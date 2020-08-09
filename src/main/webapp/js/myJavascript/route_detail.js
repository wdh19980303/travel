$(function () {
    var rid = getParameter("rid")
    $.post("route/routeQuery", {rid: rid}, function (route) {
        $("#rname").html(route.rname)
        $("#address").html(route.seller.address)
        $("#price").html(route.seller.price)
        $("#consphone").html(route.seller.consphone)
        $("#sname").html(route.seller.sname)
        $("#count_favorite").html("已收藏" + route.count + "次")

        // 图片
        var ddstr = '<a class="up_img up_img_disable"></a>'
        // 遍历
        for (var i = 0; i < route.routeImgList.length; i++) {
            var astr
            if (i >= 4) {
                astr = '<a title="" class="little_img"\n' +
                    '     data-bigpic="' + route.routeImgList[i].bigPic + '" style="display: none">\n' +
                    '     <img src="' + route.routeImgList[i].smallPic + '" >'
            } else {
                astr = '<a title="" class="little_img"\n' +
                    '     data-bigpic="' + route.routeImgList[i].bigPic + '">\n' +
                    '     <img src="' + route.routeImgList[i].smallPic + '">'
            }

            ddstr += astr
        }
        // 加入
        ddstr += ' <a class="down_img down_img_disable" style="margin-bottom: 0;"></a>'
        $("#dd").html(ddstr)


        $(document).ready(function () {
            goImg()
            auto_play()
        })

    })

})

function goImg() {
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function () {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click', function () {
        var num = $('.little_img').length;
        if ((nextindex + 1) <= num) {
            $('.little_img:eq(' + picindex + ')').hide();
            $('.little_img:eq(' + nextindex + ')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click', function () {
        var num = $('.little_img').length;
        if (picindex > 0) {
            $('.little_img:eq(' + (nextindex - 1) + ')').hide();
            $('.little_img:eq(' + (picindex - 1) + ')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
    //自动播放
    // var timer = setInterval("auto_play()", 5000);
}

function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}


$(function () {
    // 发送请求,查看用户是否收藏
    var rid = getParameter("rid")
    $.post("route/isFavorite", {rid: rid}, function (flag) {
        if (flag) {
            // 用户收藏过
            $("#btn_favorite").addClass("already")
            $("#btn_favorite").attr("disabled",true).css("pointer-events","none")
            $("#btn_favorite").text("已收藏")
        } else {

            // 用户没有收藏过
        }
    })

})

$("#btn_favorite").click(function () {
    var rid = getParameter("rid")

    // 判断用户是否登录
    $.post("user/findUsername", {}, function (data) {
        if (data == null) {
            alert("请登录后再收藏吧")
            return
        } else {
            $.post("route/addFavorite", {rid: rid}, function (route) {
                $("#btn_favorite").addClass("already")
                $("#btn_favorite").attr("disabled","disabled").css("pointer-events","none");
                $("#btn_favorite").text("已收藏")
                $("#count_favorite").html("已收藏" + route.count + "次")

            })
        }
    })



})