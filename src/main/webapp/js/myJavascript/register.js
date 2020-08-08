/**
 * 表单校验
 *        1.    用户名，单词字符，长度8-20位
 *        2.    密码，单词字符，长度8到20
 *        3.    email
 *        4.    姓名非空
 *        5.    手机号
 *        6.    出生日期,非空
 *        7.    验证码,非空
 */

// 校验用户名
function checkUsername() {
    // 1 获取用户名
    var username = $("#username").val()
    // 2 定义正则
    var reg_username = /^\w{8,20}$/
    // 3 判断,给出提示信息
    var flag = reg_username.test(username)
    if (flag) {
        $("#username").css("border", "")
    } else {
        //用户名非法
        $("#username").css("border", "1px solid red")
    }
    return flag
}

// 校验密码
function checkPassword() {
    // 1 获取密码
    var password = $("#password").val()
    // 2 定义正则
    var reg_password = /^\w{8,20}$/
    // 3 判断,给出提示信息
    var flag = reg_password.test(password)
    if (flag) {
        $("#password").css("border", "")
    } else {
        //密码非法
        $("#password").css("border", "1px solid red")
    }
    return flag
}

// 校验email
function checkEmail() {
    // 1 获取email
    var email = $("#email").val()
    // 2 定义正则
    var reg_email = /^\w+@\w+\.\w+$/
    // 3 判断,给出提示信息
    var flag = reg_email.test(email)
    if (flag) {
        $("#email").css("border", "")
    } else {
        //email非法
        $("#email").css("border", "1px solid red")
    }
    return flag
}

// 校验姓名
function checkName() {
    // 1 获取name
    var name = $("#name").val()
    // 2 定义正则
    var reg_name = /^[\s\S]*.*[^\s][\s\S]*$/
    // 3 判断,给出提示信息
    var flag = reg_name.test(name)
    if (flag) {
        $("#name").css("border", "")
    } else {
        //name非法
        $("#name").css("border", "1px solid red")
    }
    return flag
}

// 校验手机号
function checkTelephone() {

    // 1 获取telephone
    var telephone = $("#telephone").val()
    // 2 定义正则
    var reg_telephone = /^(1)+\d{10}$/
    // 3 判断,给出提示信息
    var flag = reg_telephone.test(telephone)
    if (flag) {
        $("#telephone").css("border", "")
    } else {
        //telephone非法
        $("#telephone").css("border", "1px solid red")
    }
    return flag
}

// 校验出生日期
function checkBirthday() {
    // 1 获取数据
    var birthday = $("#birthday").val()
    // alert(birthday)
    if (birthday != null && birthday != "") {
        $("#birthday").css("border", "")
        return true
    } else {
        $("#birthday").css("border", "1px solid red")
        return false
    }

}

// 校验验证码
function checkCheckCode() {
    var checkCode = $("#check").val()

    return checkCode != null && checkCode !=""
}

//图片点击事件
function changeCheckCode(img) {
    img.src = "CheckCodeServlet?" + new Date().getTime();
}


// 当表单提交时校验所有方法
// 每一个组件失去焦点的时候使用对应方法
$(function () {
    $("#registerForm").submit(function () {
        var flag = false
        // 1 发送数据到服务器
        if (checkUsername() && checkPassword() && checkEmail() && checkTelephone() && checkName() && checkCheckCode() && checkBirthday()) {
            // 校验通过,发送ajax请求,提交表单数据,
            $.post("user/register", $(this).serialize(), function (data) {
                    // alert(data)
                    if (data.flag) {
                        //注册成功
                        location.href = "register_ok.html"

                    } else {
                        $("#error_msg").html(data.errorMsg)
                    }
                }
            )
        }

        return flag
    })

    $("#username").blur(checkUsername)
    $("#password").blur(checkPassword)
    $("#email").blur(checkEmail)
    $("#telephone").blur(checkTelephone)
    $("#birthday").blur(checkBirthday)
    $("#check").blur(checkCheckCode)
    $("#name").blur(checkName)
})
