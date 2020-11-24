package com.bjpowernode.p2p.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import com.bjpowernode.p2p.service.user.RechargeRecordService;
import com.bjpowernode.p2p.utils.HttpClientUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/loan")
public class RechargeRecordController {


    @Reference(interfaceClass = RechargeRecordService.class,version = "1.0.0",check = false,timeout = 2000)
    private  RechargeRecordService rechargeRecordService;

    @Reference(interfaceClass = FinanceAccountService.class,version = "1.0.0",check = false,timeout = 2000)
    private FinanceAccountService financeAccountService;

    @RequestMapping("/returnCall")
//    public String returnCall(@RequestParam Map<String,Object> param){
    public String returnCall(String out_trade_no , Double total_amount,Model model,HttpSession session) throws Exception {
        System.out.println("returnCall execute ...");
        User user = (User) session.getAttribute("user");
        //支付宝支付成功后,回调该方法
        //接收到该参数后,查询具体的支付结果
        //远程后台调用pay工程的接口
        //参数1,发送的url地址
        //参数2.发送的2请求参数的Map集合
        Map<String,Object> param = new HashMap<>();
        param.put("out_trade_no",out_trade_no);
        String result = HttpClientUtils.doPost("http://localhost:9090/pay/alipayQuery", param);
        System.out.println("web工程的接收结果 result "+result);

        //解析支付宝返回的结果
        Map<String,Object> resultMap = (Map<String, Object>) JSONObject.parse(result);
        if (ObjectUtils.isNotEmpty(resultMap)){
            Map<String,String> alipay_trade_query_response = (Map<String,String>) resultMap.get("alipay_trade_query_response");
            if (ObjectUtils.isNotEmpty(alipay_trade_query_response)){
                if(StringUtils.equals(alipay_trade_query_response.get("code"),"10000")){
                    //code为10000,代表查询订单成功
                    //业务处理成功
                    //获取交易状态
                    String trade_status = alipay_trade_query_response.get("trade_status");
                    RechargeRecord rechargeRecord = rechargeRecordService.findRechargeRecordByRechargeNo(out_trade_no);
                    if(StringUtils.equals(trade_status,"WAIT_BUYER_PAY")){
                        //判断支付状态交易状态：
                        // WAIT_BUYER_PAY（交易创建，等待买家付款）、

                    }else if (StringUtils.equals(trade_status,"TRADE_CLOSED")){
                        // TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
                        //更改充值状态为2,代表交易失败或关闭
                        //获取充值记录
                        if (ObjectUtils.isNotEmpty(rechargeRecord)){
                            //修改充值状态
                            RechargeRecord updateRechargeRecord = new RechargeRecord();
                            updateRechargeRecord.setId(rechargeRecord.getId());
                            updateRechargeRecord.setRechargeStatus("2");//充值失败
                            rechargeRecordService.updateRechargeStatusById(updateRechargeRecord);
                            model.addAttribute("trade_msg","交易失败或关闭");
                            return "toRechargeBack";
                        }else {
                            model.addAttribute("trade_msg","订单不存在");
                            return "toRechargeBack";
                        }

                    }else if (StringUtils.equals(trade_status,"TRADE_FINISHED")){
                        // TRADE_FINISHED（交易结束，不可退款）

                    }else if (StringUtils.equals(trade_status,"TRADE_SUCCESS")){
                        // TRADE_SUCCESS（交易支付成功）、
                        if (ObjectUtils.isNotEmpty(rechargeRecord)){
                            //修改充值状态
                            //更改充值状态为1,代表交易成功
                            RechargeRecord updateRechargeRecord = new RechargeRecord();
                            updateRechargeRecord.setId(rechargeRecord.getId());
                            updateRechargeRecord.setRechargeStatus("1");//充值成功
                            rechargeRecordService.updateRechargeStatusById(updateRechargeRecord);
                            //更新账户余额
                            financeAccountService.updateAvailableMoneyByUid(user.getId(),total_amount);
                            return "redirect:/loan/page/toMyCenter";

                        }else{
                            model.addAttribute("trade_msg","订单不存在");
                            return "toRechargeBack";
                        }
                    }

                }else {
                    //业务处理失败,重新查询一次
                    //String result2 = HttpClientUtils.doPost("http://localhost:9090/pay/alipayQuery", param);
                    model.addAttribute("trade_msg","网络异常,请稍后再试");
                    return "toRechargeBack";
                }
            }

        }

        return "";
    }
    /**
     *  支付宝回调的参数
     * http://localhost:8080/p2p/loan/returnCall?charset=UTF-8
     *                  &out_trade_no=967067M20201124051748REUKYEB1G
     *                  &method=alipay.trade.wap.pay.return
     *                  &total_amount=75477.00
     *                  &sign=Ut0VsHYAKvp%2B7xyrBh7kRDD4CE%2F8jgE11Wogogg9vns14uU7DqixqMmjV4q8HqRqVdRKQKMxsyl9epZnobHcVphPDzBtFz6JiNcj5wQH58tkpkRr3911vzqr3pE01LsBEMjjzHdokcHLQq3ESPlvvpjn2aYZEhqcMNlL5y0HjtHrQevICNqPc7iJflwitkrZjatpDuftRhbJkoP%2BnnY9Q9N5dIXWU4eWJARmKAto2KbFnVGIPDeCAooC6dw%2BzTLVwGSpv6EdF0GNPMzSx5yuZ9NNGNekJb2UfFEMmeL1vN6RlYI8bZs7jrLQc3oWhTGCLhtwgkO1WPez5Rs7oVjPxA%3D%3D
     *                  &trade_no=2020112422001442480501608081
     *                  &auth_app_id=2016110200787550&version=1.0
     *                  &app_id=2016110200787550
     *                  &sign_type=RSA2
     *                  &seller_id=2088102181666609
     *                  &timestamp=2020-11-24+17%3A18%3A03
     * @return
     */
    @RequestMapping("/notifyCall")
    public String notifyCall(){
        System.out.println("notifyCall execute ...");
        return "";
    }


    @RequestMapping("/page/toRecharge")
    public String toRecharge(){
        return "toRecharge";
    }

    /**
     *  支付宝的充值的接口:
     *          需要调用支付宝的第三方支付
     *          支付成功后
     *          将支付的金额转到用户的账户当中
     *
     *          业务逻辑:
     *               创建RechargeRecord对象
     *               设置充值状态为0,代表充值中
     *               将它插入到数据库中
     *               调用第三方接口
     * @param rechargeMoney
     * @return
     */
    @RequestMapping("/alipay")
    public String alipay(Double rechargeMoney, HttpSession session, Model model){
        //获取用户对象
        User user = (User) session.getAttribute("user");
        if (ObjectUtils.isEmpty(user)){
            //未登录
            //跳转到登录页面
            return "redirect:/loan/page/login";
        }
        //已登录
        try {
            //将充值记录插入到数据库中
            RechargeRecord recharge = rechargeRecordService.recharge(rechargeMoney, user.getId());
            //跳转到支付宝支付页面
            //需要准备参数有:标题 描述 订单号 充值金额
            model.addAttribute("subject","KAY金融网");
            model.addAttribute("body","用户余额充值");
            model.addAttribute("out_trade_no",recharge.getRechargeNo());
            model.addAttribute("total_amount",rechargeMoney);
            return "toAlipay";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }




    @RequestMapping("/wxpay")
    public String wxpay(Double rechargeMoney){

        return "";
    }



    public static void main(String[] args) {
        //方式一:UUID
        /*UUID uuid = UUID.randomUUID();

        String s = uuid.toString();

        String replace = s.replace("-", "");

        System.out.println(replace);*/

        //方式二:时间戳,在面对并发生成的时候,毫秒值不太好用,拼接一个唯一值
        //long l = System.currentTimeMillis();
       // System.out.println(l);
       // String rechargeNo = rechargeRecordService.getRechargeNo();
       // System.out.println(rechargeNo);

        String result = "{\"alipay_trade_query_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"buyer_logon_id\":\"agm***@sandbox.com\",\"buyer_pay_amount\":\"0.00\",\"buyer_user_id\":\"2088622955042487\",\"buyer_user_type\":\"PRIVATE\",\"invoice_amount\":\"0.00\",\"out_trade_no\":\"804419M20201124071911RO4CAAVAN\",\"point_amount\":\"0.00\",\"receipt_amount\":\"0.00\",\"send_pay_date\":\"2020-11-24 19:19:39\",\"total_amount\":\"1234.00\",\"trade_no\":\"2020112422001442480501608602\",\"trade_status\":\"TRADE_SUCCESS\"},\"sign\":\"KJuuhan/PFc5mIticEur6EnDKFyYKqHwmL+MTNfP4FEw50g8fACuAI42XeH8skOKS+sXMrtnxATEvn3ZiU9V6D7DYgYshM/HmfMOjSyj2Rjuj8B7gMuXL293sK82l6vJNSAHBIAP6OCRos2TW6QYsTbHCigHsHRp8lRWb3+MznrtcKvVErwlx2Z7U9vAaXTqhRtqeewvAo2CoAgxN/ja91W4T2wieOXL+lecA1AJ4sfD29xKYvfmwTuGZ6c+vRr9rGw2eF2Lh7JqM7UpuoasGviaopGeAm3Ke3djifDpdLREPG6uLjBrPFXw9gIxGy1be91kaqKiCQ+lY5JUNcJPQQ==\"}";

        Map<String,Object> parse = (Map<String, Object>) JSONObject.parse(result);
        Map<String,String> alipay_trade_query_response = (Map<String, String>) parse.get("alipay_trade_query_response");
        System.out.println(alipay_trade_query_response);
        if(StringUtils.equals(alipay_trade_query_response.get("code"),"10000")){
            //code为10000,代表查询订单成功
            //判断支付状态交易状态：
            // WAIT_BUYER_PAY（交易创建，等待买家付款）、

            // TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
            //更改充值状态为2,代表交易失败或关闭

            // TRADE_SUCCESS（交易支付成功）、
            //更改充值状态为1,代表交易成功

            // TRADE_FINISHED（交易结束，不可退款）

        }else {

        }

        Object trade_status = alipay_trade_query_response.get("trade_status");
        System.out.println(trade_status);

    }


}
