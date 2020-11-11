# 支付系统项目

> 技术栈：
>【项目使用了2个服务：pay文件为支付服务，mall文件为商城服务】
>
> springboot，mysql，mybatis，mybatis-generator-plugin，pagehelper，
> GSON
> best-pay-sdk(支付工具包)，redis（购物车），rabbitmq(支付通知)

## 一、数据库设计

- 表设计

  1. 表关系
  2. 表结构
  3. 索引
  4. 时间戳 （细节，方便排查数据库）
  <img src="https://github.com/wucong9827/mall/blob/main/image-20201028153909675.png?raw=true" alt="image-20201028153909675" style="zoom:50%;" />
  > 数据库文件为：mall_demo.sql                                                                                                                                                                                               
         
   ## 二、业务功能
   
   ### 支付场景
   
   1. 微信
      - 公众号支付
      - native支付
      - app支付
      - H5支付（微信外浏览器）
      - 小程序支付
      - 人脸支付
   2. 支付宝
      - 条码支付
      - 扫码支付
      - 电脑网站支付
   3. 同步/异步
      - 支付结果以异步为准
   4. 支付接口前提
      - 商户申请接口资格
   5. 加密
      - RSA 签名 ！= 加密
      - RSA 加密
      - AES 加密 解密
   
   ### 支付系统
   
   1. 抽离支付业务成为独立系统
   
   2. 专用的数据库/表
   
      微信支付流程：
   
   <img src="https://pay.weixin.qq.com/wiki/doc/api/img/chapter6_4_1.png" alt="原生支付接口模式一时序图" style="zoom:80%;" />
   
   - 发起支付，请求得到支付连接，生成二维码，微信扫码支付，微信系统后台异步返回订单结果，自己的系统负责接收结果，校验处理后，通知微信，停止发送结果。
   
   支付宝支付
   
   ```tiki wiki
   RSA 非对称
   发起支付 ： 商户（商户应用私钥签名） -> 支付宝（商户应用公钥签名）
   
   异步通知 : 支付宝 （支付宝私钥签名） -> 商户（支付宝公钥验签）
   ```
   
                                                                                                                                                            
                                                                                                                                                               
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              