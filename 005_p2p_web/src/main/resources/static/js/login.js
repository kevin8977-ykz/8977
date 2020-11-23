var referrer = "";//登录后返回页面
referrer = document.referrer;
if (!referrer) {
	try {
		if (window.opener) {                
			// IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性              
			referrer = window.opener.location.href;
		}  
	} catch (e) {
	}
}

//按键盘Enter键即可登录
$(document).keyup(function(event){
	if(event.keyCode == 13){
		login();
	}
});

$(function () {
    $("#phone").blur(function () {
        var phone = $.trim($("#phone").val());
        if (phone === ""){
            $("#showId").html("请输入手机号码");
        }else if (phone.length < 11){
            $("#showId").html("请输入11位的手机号码");
        }else if (!/^1[3|4|5|7|8][0-9]{9}$/.test(phone)){
            $("#showId").html("请输入正确的11位手机号码!");
        }else {
            //要求用户
            var phone =$.trim($("#phone").val());
            //发送ajax请求，核实手机号码是否注册
            $.ajax({
                url: basepath + "/loan/checkPhoneNumRegisted",
                data:{
                    "phone":phone
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(!data.success){
                        //用户已注册 可以登录
                        $("#showId").html("");
                    }else {
                        $("#showId").html("当前手机号码未注册，请先去注册！");
                    }
                },
                error:function () {
                    $("#showId").html("网络异常，请稍后再试");
                }
            });

        }
    });
    
    $("#loginPassword").blur(function () {
        var pwd = $.trim($("#loginPassword").val());
        if ( pwd === ""){
            $("#showId").html("请输入登录密码");
        } else if (pwd.length < 6){
            $("#showId").html("密码不能小于6位");
        }else if (!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(pwd)){
            $("#showId").html("密码输入不合法，请重新输入");
        } else {
            $("#showId").html("");
        }
    });

    $("#messageCode").blur(function () {

            //获取短信验证码
            var messageCode = $.trim($("#messageCode").val());
            //校验
            if (messageCode === ""){
                $("#showId").html("验证码不能为空！");
            } else  if(messageCode.length < 6){
                $("#showId").html("请输入6位数验证码")
            }else  if(!/^[1-9]\d*$/.test(messageCode)){
                $("#showId").html("请输入6位有效的数字验证码")
            }else {
                $("#showId").html("")
            }


    });

    //获取短信验证码
    $("#messageCodeBtn").click(function () {
         $("#phone").blur();
         var showId = $("#showId").text();
         if (showId === ""){
             //验证通过
             //获取手机号码
             var phone = $.trim($("#phone").val());

             $.ajax({
                 url: basepath + "/loan/getMessageCode",
                 data:{
                     "phone":phone
                 },
                 type:"post",
                 dataType:"json",
                 success:function (data) {
                     if(data.success){
                         alert(data.msg);
                     }else{
                         alert(data.msg);
                     }
                 },
                 error:function () {
                     //网络异常
                     $("#showId").html("网络异常，请稍后再试");
                 }
             })
         }
    });

    $("#loginBtn").click(function () {

        //点击得到校验结果
        $("#messageCode").blur();

        //得到校验的结果
        var err = $("#showId").text();
        if(err === "" ){
            //校验通过
            var phone =$.trim($("#phone").val());
            var pwd = $.trim($("#loginPassword").val());
            var messageCode = $.trim($("#messageCode").val());


            //抓包工具，抓取指定的url的请求  md5加密
            var md5Pwd = $.md5(pwd);
            //覆盖原密码
            $("#loginPassword").val(md5Pwd);

            $.ajax({
                url: basepath +"/loan/login",
                data:{
                    "phone":phone,
                    "loginPassword":md5Pwd,
                    "messageCode":messageCode
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if (data.success) {
                        //location = basepath + "/";
                        //location = basepath + "/";
                        //从哪儿来回哪儿去
                        var rUrl = $("#returnUrl").val();
                        if(rUrl != ""){
                            location = rUrl;
                        }else {
                            location = basepath + "/";
                        }

                    }else {
                        $("#showId").html(data.msg);
                    }
                },
                error:function () {
                    //网络异常
                    alert("网络异常，请稍后再试！")
                }

            })
        }

    });

});
