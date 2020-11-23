//点击注册按钮，发送ajax请求
$.ajax({
    url: basepath + "/loan/",
    data:{

    },
    type:"post",
    dataType:"json",
    success:function (data) {
        if (data.success){

        }else {

        }
    },
    error:function () {
        //网络异常
    }
})