package com.bjpowernode.p2p.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class AlipayJson {


    public static String AlipayJSON(String result) {
        //解析支付宝返回的结果
        Map<String, Object> resultMap = (Map<String, Object>) JSONObject.parse(result);
        if (ObjectUtils.isNotEmpty(resultMap)) {
            Map<String, String> alipay_trade_query_response = (Map<String, String>) resultMap.get("alipay_trade_query_response");
            if (ObjectUtils.isNotEmpty(alipay_trade_query_response)) {
                if (StringUtils.equals(alipay_trade_query_response.get("code"), "10000")) {
                    //code为10000,代表查询订单成功
                    //业务处理成功
                    //获取交易状态
                    String trade_status = alipay_trade_query_response.get("trade_status");
                    //RechargeRecord rechargeRecord = rechargeRecordService.findRechargeRecordByRechargeNo(out_trade_no);
                    if (StringUtils.equals(trade_status, "WAIT_BUYER_PAY")) {
                        //判断支付状态交易状态：
                        // WAIT_BUYER_PAY（交易创建，等待买家付款）、

                    } else if (StringUtils.equals(trade_status, "TRADE_CLOSED")) {
                        // TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
                        //更改充值状态为2,代表交易失败或关闭
                        //获取充值记录
                      /*  if (ObjectUtils.isNotEmpty(rechargeRecord)) {
                            //修改充值状态
                            RechargeRecord updateRechargeRecord = new RechargeRecord();
                            updateRechargeRecord.setId(rechargeRecord.getId());
                            updateRechargeRecord.setRechargeStatus("2");//充值失败
                            rechargeRecordService.updateRechargeStatusById(updateRechargeRecord);
                            model.addAttribute("trade_msg", "交易失败或关闭");
                            return "toRechargeBack";
                        } else {
                            model.addAttribute("trade_msg", "订单不存在");
                            return "toRechargeBack";
                        }*/

                    } else if (StringUtils.equals(trade_status, "TRADE_FINISHED")) {
                        // TRADE_FINISHED（交易结束，不可退款）

                    } else if (StringUtils.equals(trade_status, "TRADE_SUCCESS")) {
                        // TRADE_SUCCESS（交易支付成功）、
                       /* if (ObjectUtils.isNotEmpty(rechargeRecord)) {
                            //修改充值状态
                            //更改充值状态为1,代表交易成功
                            RechargeRecord updateRechargeRecord = new RechargeRecord();
                            updateRechargeRecord.setId(rechargeRecord.getId());
                            updateRechargeRecord.setRechargeStatus("1");//充值成功
                            rechargeRecordService.updateRechargeStatusById(updateRechargeRecord);
                            //更新账户余额
                            financeAccountService.updateAvailableMoneyByUid(user.getId(), total_amount);

                            return "redirect:/loan/page/toMyCenter";

                        } else {
                            model.addAttribute("trade_msg", "订单不存在");
                            return "toRechargeBack";
                        }*/

                    }


                } else {
                    //业务处理失败,重新查询一次
                    // String result2 = HttpClientUtils.doPost("http://localhost:9090/pay/alipayQuery", param);
                }
            }
        }
        return "";
    }


}
