package com.bjpowernode.p2p.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.bjpowernode.p2p.entity.BaseResult;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.user.RegisterService;
import com.bjpowernode.p2p.service.user.UserService;
import com.bjpowernode.p2p.utils.MessageCode;
import com.bjpowernode.p2p.utils.RealNameIdCardApi;
import com.bjpowernode.p2p.utils.SmsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/loan")
public class RegisterController {

    @Reference(interfaceClass = UserService.class,version = "1.0.0",timeout = 2000,check = false)
    private UserService userService;


    @Reference(interfaceClass = RegisterService.class,version = "1.0.0",timeout = 2000,check = false)
    private RegisterService registerService;



    @RequestMapping("/realName")
    @ResponseBody
    public BaseResult realName(@RequestParam Map<String,String> param,HttpSession session){
    //public Map<String,Object> realName(String phone,String realName,String idCard,String messageCode){

        //从map集合中提取参数
        String phone = param.get("phone");
        String realName = param.get("realName");
        String idCard = param.get("idCard");
        String messageCode = param.get("messageCode");

        //匹配验证码匹配
        String messageCodeInRedis = registerService.getMessageCodeInRedis(phone);

        //匹配
        if (!StringUtils.equals(messageCode,messageCodeInRedis)){
            /*return new HashMap<String, Object>(){{
                put("success",false);
                put("msg","验证码输入错误，请重新输入");
            }};*/
           return BaseResult.error("验证码输入错误，请重新输入");
        }
        //TODO 身份证二要素认证
        //调用封装好的工具类
        boolean flag = RealNameIdCardApi.check( idCard, realName);

        if(flag){
            //认证通过
            //获取登录的用户id 用来存入数据
            User user = (User) session.getAttribute("user");
            //将身份信息存入到数据库中
            User updateUser = userService.updateRegisteData(user.getId(),realName,idCard);

            //更新session中的用户信息
            session.removeAttribute("user");
            session.setAttribute("user",updateUser);

            return BaseResult.success("实名认证校验通过");
        }


        return BaseResult.error("实名认证校验失败");
    }





    @RequestMapping("/page/realName")
    public String toRealName(){
        return "realName";
    }

    /**
     *      发送短信验证码：
     *          业务逻辑：
     *              1.生成短信验证码，6位
     *              2.将短信验证码保存到，redis中
     *              3.调用阿里云服务，发送短信
     * @param phone
     * @return
     */
    @RequestMapping("/getMessageCode")
    @ResponseBody
    public Map<String,Object> getMessageCode (String phone) throws ClientException {
        //生成短信验证码
        Integer messageCode = MessageCode.generateMessageCode();
        System.out.println("messageCode:"+messageCode);
        //保存到redis中
        registerService.saveMessageCodeInRedis(phone,messageCode);

        //调用阿里云短信服务，发送短信
        Map<String,String> map = new HashMap<>();
        map.put("code",messageCode+"");
        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phone, JSON.toJSONString(map));
        System.out.println("sendSmsResponse:"+sendSmsResponse.getMessage());
        return new HashMap<String, Object>(){{
            put("success",true);
            put("msg","短信发送成功");
        }};

    }



    @RequestMapping("/checkPhoneNumRegisted")
    @ResponseBody
    public Map<String,Object> checkPhoneNumRegisted(String phone){
        boolean flag = userService.checkPhoneNumRegisted(phone);

        return new HashMap<String,Object>(){{
            put("success",flag);
            put("msg","手机号码已注册,请登录!");
        }};
    }


    /**
     *    登录操作
     *          根据手机号码和密码去数据库查询
     *              有用户
     * @param phone 手机号码
     * @param loginPassword  md5加密后的密码
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Object register(@RequestParam(required = true,name = "phone") String phone,
                           @RequestParam(required = true,name = "loginPassword") String loginPassword,
                           @RequestParam(required = true,name = "messageCode") String messageCode,
                           HttpSession session){
        //校验验证码
        //从redis中获取验证码
        String messageCodeInRedis = registerService.getMessageCodeInRedis(phone);

        if(!StringUtils.equals(messageCodeInRedis,messageCode)){
            //验证码不匹配
            //验证码输入错误
            return  new HashMap<String,Object>() {{
                put("success",false);
                put("msg",1);
            }};
        }

        //根据用户名和密码，查询用户
        User user = userService.register(phone,loginPassword);
        session.setAttribute("user",user);

        //让用户自动登录，将用户存入session中
        return new HashMap<String,Object>(){{
           put("success",true);
           put("msg","注册成功！");
        }};
    }


    @RequestMapping("/page/register")
    public  String toRegister(){

        return "register";
    }

}
