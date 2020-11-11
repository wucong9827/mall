package com.demo.mall.service.impl;

import com.demo.mall.dao.OrderItemMapper;
import com.demo.mall.dao.OrderMapper;
import com.demo.mall.dao.ProductMapper;
import com.demo.mall.dao.ShippingMapper;
import com.demo.mall.entity.*;
import com.demo.mall.enums.OrderStatusEnum;
import com.demo.mall.enums.PaymentTypeEnum;
import com.demo.mall.enums.ProductEnum;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.service.ICartService;
import com.demo.mall.service.IOrderService;
import com.demo.mall.vo.OrderItemVo;
import com.demo.mall.vo.OrderVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wucong
 * @date 2020/11/10 15:16
 * @description com.demo.mall.service.impl
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ICartService cartService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        // 1.检查收货地址是否存在，正确
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (Objects.isNull(shipping)) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        // 2.获取购物车，校验（商品是否存在，库存是否足够）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected).collect(Collectors.toList());
            // 购物车为空
        if (cartList.isEmpty()) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
            // 获取商品id
        Set<Integer> productIdSet = cartList.stream()
                .map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderItem> orderItemList = new ArrayList<>();
            // 生成订单号 TODO：在分布式系统中，使用分布式Id算法生成
        Long orderNo = generateOrderNo();
        log.info("校验商品是否存在，库存是否足");
        for (Cart cart : cartList) {
            Product product = productMap.get(cart.getProductId());
            if (Objects.isNull(product)) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
            }
            // 商品当前销售状态
            if (!ProductEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
            }
            // 校验库存
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR,
                        "库存不正确. " + product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);
            // 扣库存（考虑高并发下的扣库存，加锁（悲观、乐观））
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }
        // 计算选中的商品的总价
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        // 订单入库，事务控制
        int row = orderMapper.insertSelective(order);
        if (row <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        // 3.更新购物车（选中的商品）
        //Redis有事务(打包命令)，不能回滚
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNoSet(orderNoSet);

        // 订单详情分组
        Map<Long, List<OrderItem>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        // 地址
        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        // 结果数据
        List<OrderVo> orderVos = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVos.add(orderVo);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVos);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        return null;
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        return null;
    }

    @Override
    public void paid(Long orderNo) {

    }

    /**
     * 订单编号
     * 企业级：分布式唯一id/主键
     * @return
     */
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    /**
     * 构建订单详细信息
     * @param uid
     * @param orderNo
     * @param quantity
     * @param product
     * @return
     */
    private OrderItem buildOrderItem(Integer uid, Long orderNo,
                                     Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo(orderNo);
        orderItem.setUserId(uid);
        orderItem.setProductName(product.getName());
        orderItem.setQuantity(quantity);
        orderItem.setProductId(product.getId());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    /**
     * 构造order订单对象
     * @param uid
     * @param orderNo
     * @param shippingId
     * @param orderItems
     * @return
     */
    private Order buildOrder(Integer uid, Long orderNo,
                             Integer shippingId, List<OrderItem> orderItems) {
        BigDecimal payment = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Order.builder()
                .orderNo(orderNo)
                .userId(uid)
                .shippingId(shippingId)
                .payment(payment)
                .paymentType(PaymentTypeEnum.PAY_ONLINE.getCode())
                .postage(0)
                .status(OrderStatusEnum.NO_PAY.getCode())
                .build();
    }

    /**
     * 构建订单返回数据
     * @param order
     * @param orderItems
     * @param shipping
     * @return
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItems, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> orderItemVoList = orderItems.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(orderItemVoList);

        if (!Objects.isNull(shipping)) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }
        return orderVo;
    }
}
