$(function () {
    var rid = getParameter("rid")
    $.post("route/routeQuery", {rid: rid}, function (route) {
        $("#rname").html(route.rname)
        $("#address").html(route.seller.address)
        $("#price").html(route.seller.price)
        $("#consphone").html(route.seller.consphone)
        $("#sname").html(route.seller.sname)

        // 图片
        var ddstr = '<a class="up_img up_img_disable"></a>'
        // 遍历
        for (var i = 0; i < route.routeImgList.length; i++) {
            var  astr
            if(i >= 4) {
                 astr = '<a title="" class="little_img"\n' +
                    '     data-bigpic="'+ route.routeImgList[i].bigPic +'" style="display: none">\n' +
                    '     <img src="'+ route.routeImgList[i].smallPic +'" >'
            } else {
                 astr = '<a title="" class="little_img"\n' +
                    '     data-bigpic="'+ route.routeImgList[i].bigPic +'">\n' +
                    '     <img src="'+ route.routeImgList[i].smallPic +'">'
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



    /**
     * <dd>
     <a class="up_img up_img_disable"></a>

     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m40920d0669855e745d97f9ad1df966ebb.jpg">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m20920d0669855e745d97f9ad1df966ebb.jpg">
     </a>

     <a title="" class="little_img cur_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m49788843d72171643297ccc033d9288ee.jpg">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m29788843d72171643297ccc033d9288ee.jpg">
     </a>

     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m4531a8dbceefa2c44e6d0e35627cd2689.jpg">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m2531a8dbceefa2c44e6d0e35627cd2689.jpg">

     </a>
     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m46d8cb900e9f6c0a762aca19eae40c00c.jpg">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m26d8cb900e9f6c0a762aca19eae40c00c.jpg">
     </a>

     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m45ea00f6eba562a767b5095bbf8cffe07.jpg"
     style="display:none;">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m25ea00f6eba562a767b5095bbf8cffe07.jpg">
     </a>
     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m4265ec488cd1bc7ce749bc8c9b34b87bc.jpg"
     style="display:none;">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m2265ec488cd1bc7ce749bc8c9b34b87bc.jpg">
     </a>
     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m4e7e964909d7dd1a9f6e5494d4dc0c847.jpg"
     style="display:none;">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m2e7e964909d7dd1a9f6e5494d4dc0c847.jpg">
     </a>
     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m467db00e1b76718fab0fe8b96e10f4d35.jpg"
     style="display:none;">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m267db00e1b76718fab0fe8b96e10f4d35.jpg">
     </a>
     <a title="" class="little_img"
     data-bigpic="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size4/201703/m487bbbc6e43eba6aa6a36cc1a182f7a20.jpg"
     style="display:none;">
     <img src="http://www.jinmalvyou.com/Public/uploads/goods_img/img_size2/201703/m287bbbc6e43eba6aa6a36cc1a182f7a20.jpg">
     </a>
     <a class="down_img down_img_disable" style="margin-bottom: 0;"></a>
     </dd>
     */

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
