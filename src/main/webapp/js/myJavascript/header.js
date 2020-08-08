$(function () {
    // 查询用户信息
    $.post("user/findUsername", {}, function (data) {
        if (data != null) {
            var msg = "欢迎" + data
            $("#span_username").html(msg)
        }
    })

    // 分类信息
    $.post("category/findAll", {}, function (data) {
        var lis = ' <li class="nav-active"><a href="index.html">首页</a></li>'
        // 遍历数组,拼接标签
        for (var i = 0; i < data.length; i++) {
            lis += '<li><a href="route_list.html?cid=' + data[i].cid + '">' + data[i].cname + "</a></li>"
        }
        lis += "<li><a href=\"favoriterank.html\">收藏排行榜</a></li>"
        // 拼接收藏排行榜
        $("#category_ul").html(lis)

    })

    //给搜索按钮绑定单击事件
    $("#search-button").click(function () {
        var rname = $("#search_input").val()
        // 跳转路径

        var cid = getParameter("cid")
        location.href = "http://localhost:80/travel/route_list.html?cid=" + cid + "&rname=" + rname
    })
})