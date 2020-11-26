package com.bjpowernode.p2p.pay;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.bjpowernode.p2p.config.AlipayConfig;
import com.bjpowernode.p2p.utils.HttpClientUtils;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {


    @RequestMapping("/getWxpayResult")
    @ResponseBody
    public String getWxpayResult(@RequestParam(required = true,name = "out_trade_no")String out_trade_no) throws Exception {

        Map<String,String> param = new HashMap<>();
        //公众号id
        param.put("appid","wx8a3fcf509313fd74");
        //商户号
        param.put("mch_id","1361137902");
        //订单号,商户订单号和微信订单号,二选一
        param.put("out_trade_no",out_trade_no);
        //随机字符串
        param.put("nonce_str",WXPayUtil.generateNonceStr());
        //签名,签名一定要在最后面
        param.put("sign",WXPayUtil.generateSignature(param,"367151c5fd0d50f1e34a68a802d6bbca"));
        //将Map集合转换为xml数据
        String xml = WXPayUtil.mapToXml(param);

        //调用微信支付的api,完成订单查询操作
         String stringXmlResult = HttpClientUtils.doPostByXml("https://api.mch.weixin.qq.com/pay/orderquery", xml);

        Map<String, String> result = WXPayUtil.xmlToMap(stringXmlResult);

        System.out.println(result);

        /**
         *
         * 未支付的查询订单结果:
              {"nonce_str":"FEXUpFW12T1tiKm8",
              "device_info":"",
              "out_trade_no":"560368M20201125113440RY236AVPW",
              "trade_state":"NOTPAY",
              "appid":"wx8a3fcf509313fd74",
              "total_fee":"1",
              "sign":"E8625201850E711ABEC2B77030C97DEA",
              "trade_state_desc":"è®¢å\u008D\u0095æ\u009Cªæ\u0094¯ä»\u0098",
              "return_msg":"OK",
              "result_code":"SUCCESS",
              "mch_id":"1361137902",
              "return_code":"SUCCESS"
                }

            已支付的查询订单结果:
         {
            "transaction_id":"4200000737202011254406386931",
             "nonce_str":"IoBbujHt9HRUWeAa",
             "trade_state":"SUCCESS",
             "bank_type":"OTHERS",
              "openid":"o_Tnit0yTYqh5HrClB7V1P5aYLk0",
                "sign":"3F35FD564D1BFD1AA4A3F347FD508D68",
                "return_msg":"OK",
                "fee_type":"CNY",
                "mch_id":"1361137902",
                "cash_fee":"1",
                "out_trade_no":"560368M20201125113440RY236AVPW",
                "cash_fee_type":"CNY",
                "appid":"wx8a3fcf509313fd74",
                "total_fee":"1",
                "trade_state_desc":"æ\u0094¯ä»\u0098æ\u0088\u0090å\u008A\u009F",
                "trade_type":"NATIVE",
                "result_code":"SUCCESS",
                "attach":"",
                "time_end":"20201125113740",
                "is_subscribe":"Y",
                "return_code":"SUCCESS"
            }

        **/

        //将集合转换为jsonString并返回
        String jsonString = JSON.toJSONString(result);
        return jsonString;

    }




    @RequestMapping("/getWxpayCodeUrl")
    @ResponseBody
    public String getWxpayCodeUrl(String body,String out_trade_no,String total_fee) throws Exception {
        //调用微信的官方统一下单api,获取code_url
        Map<String,String> param = new HashMap<>();
        // 公众号账号ID  appid=wx8a3fcf509313fd74
        // 商户号 mch_id=1361137902
        //trade_type=NATIVE
        //key=367151c5fd0d50f1e34a68a802d6bbca
        //wxpay_url=https://api.mch.weixin.qq.com/pay/unifiedorder
        param.put("appid","wx8a3fcf509313fd74");
        param.put("mch_id","1361137902");
        //随机字符串
        param.put("nonce_str",WXPayUtil.generateNonceStr());
        param.put("body",body);
        param.put("out_trade_no",out_trade_no);
        param.put("total_fee","1");
        param.put("spbill_create_ip","127.0.0.1");
        param.put("notify_url","127.0.0.1");//通知地址

        param.put("trade_type","NATIVE");
        param.put("product_id",out_trade_no);

        //签名,将所有的参数准备完毕后,一起进行签名
        param.put("sign",WXPayUtil.generateSignature(param,"367151c5fd0d50f1e34a68a802d6bbca"));
        //微信支付交互的参数都是xml
        //需要将参数转换为xml格式
        String xml = WXPayUtil.mapToXml(param);

        //发送post请求,参数是xml数据
        String xmlResult = HttpClientUtils.doPostByXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);

        //将它转换为json数据
        Map<String, String> stringStringMap = WXPayUtil.xmlToMap(xmlResult);
        System.out.println("stringStringMap:"+stringStringMap);
        String jsonString = JSON.toJSONString(stringStringMap);
        return jsonString;
    }



    @RequestMapping("/alipayQuery")
    @ResponseBody
    public String alipayQuery(String out_trade_no) throws AlipayApiException {
        //查询支付宝订单号的代码在query.jsp中
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        //String trade_no = new String(request.getParameter("WIDtrade_no").getBytes("ISO-8859-1"),"UTF-8");
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
        model.setOutTradeNo(out_trade_no);
        //model.setTradeNo(trade_no);
        alipay_request.setBizModel(model);

        AlipayTradeQueryResponse alipay_response =client.execute(alipay_request);
        System.out.println(alipay_response.getBody());
        return alipay_response.getBody();
    }


    /**
     * 支付工程,调用第三方支付宝的支付
     * 返回支付页面
     * @param body
     * @param subject
     * @param out_trade_no
     * @param total_amount
     * @return
     */
    @RequestMapping("/alipay")
    @ResponseBody
    public String alipay(@RequestParam(required = true,name = "body")String body,
                         @RequestParam(required = true,name = "subject")String subject,
                         @RequestParam(required = true,name = "out_trade_no")String out_trade_no,
                         @RequestParam(required = true,name = "total_amount")String total_amount){
        System.out.println(body);
        System.out.println(subject);
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println("支付工程接收到了请求");

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        //TODO 官方SDK提供的demo
//        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        // 订单名称，必填
//        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
//        System.out.println(subject);
        // 付款金额，必填
//        String total_amount=new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
        // 商品描述，可空
//        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        // 超时时间 可空
        String timeout_express="2m";
        // 销售产品码 必填
        String product_code="QUICK_WAP_WAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            //form参数返回的就是支付宝支付页面
            //在该支付页面,登录,输入支付密码
            System.out.println("form"+form);
            return  form;

     /*       response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();*/
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return null;
    }




}
