
//同意实名认证协议
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
       var  phone = $.trim($("#phone").val());
       if(phone === ""){
           showError("phone","手机号码不能为空！");
       }else  if(phone.length < 11){
           showError("phone","请输入11位手机的号码!")
       }else  if (!/^1[3|4|5|7|8][0-9]{9}$/.test(phone)) {
           showError("phone","请输入正确的手机号码!");
       }else {
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
                       showSuccess("phone");
                   }else {
                       showError("phone","当前账号未注册，请先注册！");
                   }
               }
           })
       }
    });

    $("#realName").blur(function () {
        var realName = $.trim($("#realName").val());
        if(realName === ""){
            showError("realName","真实姓名不能为空！")
        }else  if(!/^[\u4e00-\u9fa5]{0,15}$/.test(realName)){
            showError("realName","请输入正确的姓名格式！")
        }else  {
            showSuccess("realName")
        }
    });

    $("#idCard").blur(function () {
       var idCard = $.trim($("#idCard").val());
       if(idCard === ""){
           showError("idCard","身份证号码不能为空！")
       }else if (idCard.length <  15){
           showError("idCard","请输入15位或18位身份证号码!")
       }else if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCard) ){
           showError("idCard","身份证号码格式输入错误，请重新输入！")
       }else {
           showSuccess("idCard")
       }
    });

    $("#messageCode").blur(function () {
       var messageCode = $.trim($("#messageCode").val());
       if(messageCode === ""){
           showError("messageCode","请输入验证码！")
       }else  if(messageCode.length < 6){
           showError("messageCode","请输入6位数验证码")
       }else  if(!/^[1-9]\d*$/.test(messageCode)){
           showError("messageCode","请输入6位有效的数字验证码")
       }else {
           showSuccess("messageCode")
       }
    });



    $("#messageCodeBtn").click(function () {
        $("#phone").blur();
        var phoneErr = $("#phoneErr").text();
        if (phoneErr === ""){
            var phone = $("#phone").val();
            $.ajax({
                url: basepath + "/loan/getMessageCode",
                data:{
                    phone:phone
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if (data.success){
                        alert("短信验证码发送成功！")
                    }else {
                        showError("messageCode","网络异常，请稍后再试！")
                    }
                },
                error:function () {
                    //网络异常
                    alert("网络异常，请稍后再试！");
                }
            })
        }
    });

    $("#btnRegist").click(function () {

        $("#phone").blur();
        $("#realName").blur();
        $("#idCard").blur();
        $("#messageCode").blur();

        var errText = $("div[id$=Err]").text();
        if (errText === ""){
            var phone = $.trim($("#phone").val());
            var realName = $.trim($("#realName").val());
            var idCard = $.trim($("#idCard").val());
            var messageCode = $.trim($("#messageCode").val());
                //点击注册按钮，发送ajax请求
            $.ajax({
                url: basepath + "/loan/realName",
                data:{
                    phone:phone,
                    realName:realName,
                    idCard:idCard,
                    messageCode:messageCode
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if (data.success){
                        alert("实名认证绑定成功");
                        location = basepath + "/";
                    }else {
                        showError("messageCode",data.msg);
                    }
                },
                error:function () {
                    //网络异常
                    showError("phone","网络异常，请稍后再试！")
                }
            })

        }

    });




});


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