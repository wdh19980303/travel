$(function () {
    $("#btn_submit").click(function () {
        $.post("user/login",$("#loginForm").serialize(),function (data) {
            if(data.flag) {
                //登录成功
                location.href = "index.html"
            } else {
                // 登录失败
                $("#errorMsg").html(data.errorMsg)
            }
        })
    })
})

