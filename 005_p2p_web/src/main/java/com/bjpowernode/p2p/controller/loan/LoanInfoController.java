package com.bjpowernode.p2p.controller.loan;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import com.bjpowernode.p2p.service.user.FinanceAccountService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loan")
public class LoanInfoController {

    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check = false)
    private  LoanInfoService loanInfoService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = FinanceAccountService.class,version = "1.0.0",check = false)
    private FinanceAccountService financeAccountService;



    @RequestMapping("/loan")
    public String toLoan(@RequestParam(required = true,name = "pType") Integer pType,
                         @RequestParam(required = false,name = "pageNo",defaultValue = "1")Integer pageNo,
                         @RequestParam(required = false,name = "pageSize",defaultValue = "9")Integer pageSize,
                         Model model){
        int pageNoIndex = (pageNo - 1) * pageSize;

        //根据产品类型查询列表数据
        List<LoanInfo> loanInfoList = loanInfoService.findLoanInfoListByProductType(pType, pageNoIndex, pageSize);

        System.out.println("loanInfoList:"+loanInfoList);
        model.addAttribute("loanInfoList",loanInfoList);

        //查询总记录数
        Long totalCount =  loanInfoService.findTotalCount(pType);
        System.out.println("totalCount:"+totalCount);
        model.addAttribute("totalCount",totalCount);

        int total = totalCount.intValue();
        //计算总页数
        int totalPages = total % pageSize == 0 ? total/pageSize : (total/pageSize) + 1;
        System.out.println("totalPages:"+totalPages);
        model.addAttribute("totalPages",totalPages);

        model.addAttribute("pageNo",pageNo);

        List<Map<String,Object>> investTop= bidInfoService.findInvestTop();
        System.out.println("investTop:"+investTop);
        model.addAttribute("investTop",investTop);


        return "loan";
    }

    @RequestMapping("/loanInfo")
    public String toLoanInfo(Integer id,
                             @RequestParam(required = true,name = "pageNo",defaultValue = "1")Integer pageNo,
                             @RequestParam(required = true,name = "pageSize",defaultValue = "7")Integer pageSize,
                             Model model, HttpSession session){

        //根据id查询对应的产品数据
        LoanInfo loanInfo = loanInfoService.findById(id);

        //将产品数据存入到model中
        model.addAttribute("loanInfo",loanInfo);
        int pageIndex = (pageNo - 1) * pageSize;


        //获取当前产品的投资记录
        //前台页面需要：用户的电话号码，投资金额，投资时间
        List<Map<String,Object>> bidInfoList = bidInfoService.findBidInfoListByLoanId(id,pageIndex,pageSize);
        System.out.println(bidInfoList);
        if (ObjectUtils.isEmpty(bidInfoList)){
            System.out.println("11111111");
            model.addAttribute("bidInfo",1);
        }
        model.addAttribute("bidInfoList",bidInfoList);




        long totalCount = bidInfoService.findBidInfoCountByLoanId(id);

        long totalPages = totalCount % pageSize == 0 ?  totalCount/pageSize : (totalCount/pageSize) + 1 ;

        //准备总记录数，总页数，分页数
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNo",pageNo);

        User user = (User) session.getAttribute("user");

        if(ObjectUtils.isNotEmpty(user)){
            //已登录
            FinanceAccount financeAccount = financeAccountService.findByUid(user.getId());
            model.addAttribute("financeAccount",financeAccount);
        }

        return "loanInfo";
    }



}
