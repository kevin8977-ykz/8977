package com.bjpowernode.p2p.controller.loan;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.service.loan.BidInfoService;
import com.bjpowernode.p2p.service.loan.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loan")
public class LoanInfoController {

    @Reference(interfaceClass = LoanInfoService.class,version = "1.0.0",check = false)
    private  LoanInfoService loanInfoService;

    @Reference(interfaceClass = BidInfoService.class,version = "1.0.0",check = false)
    private BidInfoService bidInfoService;

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



        return "loan";
    }

    @RequestMapping("/loanInfo")
    public String toLoanInfo(Integer id,Model model){

        //根据id查询对应的产品数据
        LoanInfo loanInfo = loanInfoService.findById(id);

        //将产品数据存入到model中
        model.addAttribute("loanInfo",loanInfo);

        //获取当前产品的投资记录
        //前台页面需要：用户的电话号码，投资金额，投资时间
        List<Map<String,Object>> bidInfoList = bidInfoService.findBidInfoListByLoanId(id);
        model.addAttribute("bidInfoList",bidInfoList);



        return "loanInfo";
    }



}
