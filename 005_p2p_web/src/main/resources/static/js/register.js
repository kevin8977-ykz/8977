//错误提示
function showError(id,msg) {
	$("#"+id+"Ok").hide();
	$("#"+id+"Err").html("<i></i><p>"+msg+"</p>");
	$("#"+id+"Err").show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id+"Ok").show();
	$("#"+id).removeClass("input-red");
}


//打开注册协议弹层
function alertBox(maskid,bosid){
	$("#"+maskid).show();
	$("#"+bosid).show();
}
//关闭注册协议弹层
function closeBox(maskid,bosid){
	$("#"+maskid).hide();
	$("#"+bosid).hide();
}

//注册协议确认
$(function() {
	$("#agree").click(function(){
		var ischeck = document.getElementById("agree").checked;
		if (ischeck) {
			$("#btnRegist").attr("disabled", false);
			$("#btnRegist").removeClass("fail");
		} else {
			$("#btnRegist").attr("disabled","disabled");
			$("#btnRegist").addClass("fail");
		}
	});

});

$(function () {
    $("#phone").blur(function () {
        var phone = $("#phone").val();
        if(phone === "" ){
            showError("phone","手机号码不能为空!");
        }else if(phone.length < 11){
            showError("phone","请输入11位数字的手机号码！");
        }else if(!/^1[3|4|5|7|8][0-9]{9}$/.test(phone)){
            showError("phone","请输入正确的手机号码!");
        }else {
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
                    if(data.success){
                        showSuccess("phone");
                    }else {
                        showError("phone",data.msg);
                    }
                }
            })
        }
    });

    //对密码进行校验操作
    //参数1，绑定的事件
    //参数2，执行的回调方法
    $("#loginPassword").on("blur",function () {
        var loginPassword = $("#loginPassword").val();

        if(loginPassword === ""){
            showError("loginPassword","密码不能为空！");
        }else   if(loginPassword.length < 6){
            showError("loginPassword","密码不能小于6位！");
        }else  if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(loginPassword)){
            showError("loginPassword","请输入6-20位英文和数字混合密码");
        }else {
            showSuccess("loginPassword");
        }
    });

    //验证验证码
    $("#messageCode").blur(function () {

       //获取短信验证码
        var messageCode = $.trim($("#messageCode").val());
        //校验
        if (messageCode === ""){
            showError("messageCode","验证码不能为空！");
        } else  if(messageCode.length < 6){
            showError("messageCode","请输入6位数验证码")
        }else  if(!/^[1-9]\d*$/.test(messageCode)){
            showError("messageCode","请输入6位有效的数字验证码")
        }else {
            showSuccess("messageCode")
        }
    });

    //点击注册按钮，发送ajax请求
    $("#btnRegist").click(function () {
        //当手机号码，密码，校验不通过，不发送请求。
        //触发手机号码和密码的onblur事件
        $("#phone").blur();
        $("#loginPassword").blur();
        $("#messageCode").blur();

        var html = $("div[id$='Err']").text();
        if(html === "" ){
            var phone =$.trim($("#phone").val());
            var pwd = $.trim($("#loginPassword").val());
            var messageCode = $.trim($("#messageCode").val());


            //抓包工具，抓取指定的url的请求  md5加密
            var md5Pwd = $.md5(pwd);
            //覆盖原密码
            $("#loginPassword").val(pwd);


                $.ajax({
                    url: basepath +"/loan/register",
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
                            location = basepath + "/loan/page/realName";
                        }else if(data.msg === 1){
                            showError("messageCode","验证码输入错误，请重新输入!");
                        }else {
                            //注册失败
                            //网络异常，请稍后再试
                            showError("phone","网络异常，请稍后再试！")
                        }
                    },
                    error:function () {
                        //网络异常
                    }
                });

            }

    });
});

//TODO 注册阿里云账号
//实现短信验证码操作：
//第三方提供的短信服务：
//阿里云的短信服务
//需要自己注册阿里云账号
//需要身份证证件上传，正反面
//申请ak和sk，accessKey，accessSecretKey，
//右上角图标处，下拉框，accessKey管理，创建子用户accessKey
//为该用户申请ak和sk
//将ak和sk保存到本地文件中，一会儿要使用到的参数，保存，别泄露。
$(function () {
    $("#messageCodeBtn").click(function () {
        //发送ajax请求，发送短信验证码
        $("#phone").blur();
        //获取验证结果
        var text = $("div[id=phoneErr]").text();

        if(text === ""){
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
                        showError("messageCode",data.msg)
                    }else{
                        showError("messageCode","网络异常，请稍后再试！")
                    }
                },
                error:function () {
                    //网络异常
                    showError("messageCode","网络异常，请稍后再试！")
                }
            })
        }
    });

});

