package com.bjpowernode.p2p.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.entity.BaseResult;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.user.IncomeRecordService;
import com.bjpowernode.p2p.service.user.RechargeRecordService;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import com.bjpowernode.p2p.service.user.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("loan")
public class UserController {

    @Reference(interfaceClass = UserService.class,version = "1.0.0",check = false)
    private UserService userService;

    @Reference(interfaceClass = FinanceAccountService.class,version = "1.0.0",check = false)
    private FinanceAccountService financeAccountService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = RechargeRecordService.class,version = "1.0.0",check = false)
    private RechargeRecordService rechargeRecordService;

    @Reference(interfaceClass = IncomeRecordService.class,version = "1.0.0",check = false)
    private IncomeRecordService incomeRecordService;


    @RequestMapping("/page/myInvest")
    public String toMyInvest( HttpSession session,
                              @RequestParam(required = false,name = "pageNo",defaultValue = "1")Integer pageNo,
                              @RequestParam(required = false,name = "pageSize",defaultValue = "7")Integer pageSize,
                              Model model){
        //查看全部投资记录并分页
        //需求：每页显示6条
        //实现首页、尾页、上一页、下一页
        //实现总记录数个总页数
        //倒序收益时间进行倒序排序
        //根据页数查询数据
        int pageNoIndex = (pageNo - 1) * pageSize;

        User user = (User) session.getAttribute("user");
        //查询总记录数
        Long totalCount = bidInfoService.findBidInfoCount(user.getId());
        System.out.println("totalCount:"+totalCount);
        model.addAttribute("totalCount",totalCount);

        //查询列表数据
        List<Map<String,Object>> bidInfoList = bidInfoService.findBidInfoListByUid(user.getId(),pageNoIndex,pageSize);
        System.out.println("bidInfoList:" + bidInfoList);
        model.addAttribute("bidInfoList",bidInfoList);

        int total = totalCount.intValue();
        //计算总页数
        int totalPages = total % pageSize == 0 ? total/pageSize : (total/pageSize) + 1;
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNo",pageNo);


        return "myInvest";
    }






    @RequestMapping("/page/myRecharge")
    public String myRecharge( HttpSession session,
                              @RequestParam(required = false,name = "pageNo",defaultValue = "1")Integer pageNo,
                              @RequestParam(required = false,name = "pageSize",defaultValue = "7")Integer pageSize,
                              Model model){
        //查看全部充值记录并分页
        //需求：每页显示6条
        //实现首页、尾页、上一页、下一页
        //实现总记录数个总页数
        //倒序投资时间进行倒序排序
        //根据页数查询数据
        int pageNoIndex = (pageNo - 1) * pageSize;

        User user = (User) session.getAttribute("user");
        //查询总记录数
        Long totalCount = rechargeRecordService.findRechargeRecordCount(user.getId());
        System.out.println("totalCount:"+totalCount);
        model.addAttribute("totalCount",totalCount);

        //根据用户id 进行分页查询
        List<RechargeRecord> rechargeRecordList = rechargeRecordService.findRechargeRecordListByUid(user.getId(),pageNoIndex,pageSize);
        System.out.println("rechargeRecordList" + rechargeRecordList);
        model.addAttribute("rechargeRecordList",rechargeRecordList);

        int total = totalCount.intValue();
        //计算总页数
        int  totalPages = total % pageSize == 0 ? total/pageSize : (total/pageSize) + 1;
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNo",pageNo);

        return "myRecharge";
    }








    @RequestMapping("/page/myIncome")
    public String myIncome( HttpSession session,
                            @RequestParam(required = false,name = "pageNo",defaultValue = "1")Integer pageNo,
                            @RequestParam(required = false,name = "pageSize",defaultValue = "7")Integer pageSize,
                            Model model){
        //查看全部收益记录并分页
        //需求：每页显示6条
        //实现首页、尾页、上一页、下一页
        //实现总记录数个总页数
        //倒序充值时间进行倒序排序
        //根据页数查询数据
        int pageNoIndex = (pageNo - 1) * pageSize;
        User user = (User) session.getAttribute("user");
        //查询总记录数
        Long totalCount = incomeRecordService.findIncomeRecordCount(user.getId());
        System.out.println("totalCount:"+totalCount);
        model.addAttribute("totalCount",totalCount);

        List<Map<String,Object>>  incomeRecordList = incomeRecordService.findIncomeRecordListByUid(user.getId(),pageNoIndex,pageSize);
        model.addAttribute("incomeRecordList",incomeRecordList);
        System.out.println("incomeRecordList:" + incomeRecordList);

        int total = totalCount.intValue();
        //计算总页数
        int  totalPages = total % pageSize == 0 ? total/pageSize : (total/pageSize) + 1;
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNo",pageNo);

        return "myIncome";
    }








    @RequestMapping("/page/toMyCenter")
    public String toMyCenter(HttpSession session,Model model){
        //从session中获取用户
        User user = (User) session.getAttribute("user");


        //未登录，未注册，未实名认证
        if (ObjectUtils.isEmpty(user)){
            //跳转到注册页面
            return "redirect:/loan/page/register";
        }
        Integer uid = user.getId();

        //查询当前用户的可用余额
        FinanceAccount financeAccount = financeAccountService.findByUid(uid);
        System.out.println(financeAccount);
        model.addAttribute("financeAccount",financeAccount);

        //查询最近5笔充值记录
        List<RechargeRecord>  rechargeRecordRecent= rechargeRecordService.findRechargeRecordListRecent(uid);
        model.addAttribute("rechargeRecordRecent",rechargeRecordRecent);

        //查询最近5笔投资记录
        //页面只需要产品名称、投资金额、投资时间，需要这三个属性
        List<Map<String,Object>> bidInfoListRecent =  bidInfoService.findBidInfoListRecent(uid);
        model.addAttribute("bidInfoListRecent",bidInfoListRecent);

        //查询最近5笔收益记录
        List<Map<String,Object>>  incomeRecordListRecent = incomeRecordService.findIncomeRecordListRecent(uid);
        model.addAttribute("incomeRecordListRecent",incomeRecordListRecent);

        return "myCenter";

    }

    @RequestMapping("/getAvailableMoney")
    @ResponseBody
    public BaseResult getAvailableMoney(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();

        FinanceAccount financeAccount = financeAccountService.findByUid(id);
        System.out.println(financeAccount);
        model.addAttribute("financeAccount",financeAccount);
        return BaseResult.success(financeAccount);
    }


    @RequestMapping("/findById")
    @ResponseBody
    public String findById(){
        return userService.findById();
    }

}
