package com.bjpowernode.p2p.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.user.RechargeRecordService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/loan")
public class RechargeRecordController {


    @Reference(interfaceClass = RechargeRecordService.class,version = "1.0.0",check = false)
    private  RechargeRecordService rechargeRecordService;


    @RequestMapping("/returnCall")
//    public String returnCall(@RequestParam Map<String,Object> param){
    public String returnCall(String out_trade_no , Double total_amount){
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println("returnCall execute ...");
        return "";
    }

    /**
     * http://localhost:8080/p2p/loan/returnCall?charset=UTF-8&out_trade_no=967067M20201124051748REUKYEB1G&method=alipay.trade.wap.pay.return&total_amount=75477.00&sign=Ut0VsHYAKvp%2B7xyrBh7kRDD4CE%2F8jgE11Wogogg9vns14uU7DqixqMmjV4q8HqRqVdRKQKMxsyl9epZnobHcVphPDzBtFz6JiNcj5wQH58tkpkRr3911vzqr3pE01LsBEMjjzHdokcHLQq3ESPlvvpjn2aYZEhqcMNlL5y0HjtHrQevICNqPc7iJflwitkrZjatpDuftRhbJkoP%2BnnY9Q9N5dIXWU4eWJARmKAto2KbFnVGIPDeCAooC6dw%2BzTLVwGSpv6EdF0GNPMzSx5yuZ9NNGNekJb2UfFEMmeL1vN6RlYI8bZs7jrLQc3oWhTGCLhtwgkO1WPez5Rs7oVjPxA%3D%3D&trade_no=2020112422001442480501608081&auth_app_id=2016110200787550&version=1.0&app_id=2016110200787550&sign_type=RSA2&seller_id=2088102181666609&timestamp=2020-11-24+17%3A18%3A03
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

    private String getRechargeNo() {

        //生成全局唯一的订单号
        //方式一:UUID
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        String s = uuid.toString();

        String replace = s.replace("-", "");

        return replace;
    }

    public static void main(String[] args) {
        //方式一:UUID
        UUID uuid = UUID.randomUUID();

        String s = uuid.toString();

        String replace = s.replace("-", "");

        System.out.println(replace);

        //方式二:时间戳,在面对并发生成的时候,毫秒值不太好用,拼接一个唯一值
        //long l = System.currentTimeMillis();
       // System.out.println(l);
       // String rechargeNo = rechargeRecordService.getRechargeNo();
       // System.out.println(rechargeNo);
    }


}
