package com.demo.mall.service.impl;

import com.demo.mall.dao.ProductMapper;
import com.demo.mall.entity.Cart;
import com.demo.mall.entity.Product;
import com.demo.mall.enums.ProductEnum;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.form.CartAddForm;
import com.demo.mall.form.CartUpdateForm;
import com.demo.mall.service.ICartService;
import com.demo.mall.vo.CartProductVo;
import com.demo.mall.vo.CartVo;
import com.demo.mall.vo.ResponseVo;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author wucong
 * @date 2020/11/8 15:58
 * @description com.demo.mall.service.impl
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    /**
     * redis 购物车 key
     */
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        // 1.检查商品是否存在
        if (Objects.isNull(product)) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        // 2.是否在出售状态
        if (!ProductEnum.ON_SALE.getCode().equals(product.getStatus())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 3.库存是否足够
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }
        // 4.写入redis
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Integer quantity = 1;    Cart cart;
        String cartValue = opsForHash.get(redisKey, String.valueOf(product.getId()));
        if (StringUtils.isEmpty(cartValue)) {
            // 无该商品的购物车，新建
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        } else {
            // 已经存在，数量加一
            cart = gson.fromJson(cartValue, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entities = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        Set<Integer> productIdSet = new HashSet<>();
        for (Map.Entry<String, String> entry : entities.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            productIdSet.add(productId);
        }

        // 获取购物车中的所有商品
        List<Product> productList = new ArrayList<>();
        if (!productIdSet.isEmpty()) {
            productList = productMapper.selectByProductIdSet(productIdSet);
        }
        for (Product product : productList) {
            Cart cart = gson.fromJson(entities.get(String.valueOf(product.getId())), Cart.class);

            if (!Objects.isNull(product)) {
                CartProductVo cartProductVo = new CartProductVo(
                        product.getId(),
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoList.add(cartProductVo);
                // 存在一个商品未选，则未全选
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }
                // 选中进行总价累加
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();
        }
        // 返回数据模型
        CartVo cartVo = new CartVo();
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartProductVos(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        Cart cart = gson.fromJson(value, Cart.class);
        cart.setQuantity(cartUpdateForm.getQuantity());
        cart.setProductSelected(cartUpdateForm.getSelected());

        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        setAllSelectOrUnSelect(uid, true);
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        setAllSelectOrUnSelect(uid, false);
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    @Override
    public List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entities = opsForHash.entries(redisKey);

        List<Cart> carts = new ArrayList<>();
        for (Map.Entry<String, String> entry : entities.entrySet()) {
            carts.add(gson.fromJson(entry.getValue(), Cart.class));
        }
        return carts;
    }

    /**
     * 全选/不全选
     * @param uid
     * @param select
     */
    private void setAllSelectOrUnSelect(Integer uid, Boolean select) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        List<Cart> carts = listForCart(uid);
        for (Cart cart : carts) {
            cart.setProductSelected(select);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
    }

}
