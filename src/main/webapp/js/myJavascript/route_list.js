$(function () {
    var cid = getParameter("cid")
    var rname = getParameter("rname")
    if (rname) {
        rname = window.decodeURIComponent(rname)
    }

    // 当页面加载完成后
    pageLoad(cid, null, rname)
})


function pageLoad(cid, currentPage, rname) {
    //发送ajax请求
    // 请求route/pageQuery 传递cid
    $.get("route/pageQuery", {cid: cid, currentPage: currentPage, rname: rname}, function (pageBean) {
        // 1 分页工具条
        // 1.1 展示总页码和总记录数
        $("#totalCount").html(pageBean.totalCount)
        $("#totalPage").html(pageBean.totalPage)
        // 1.2 展示分页
        /*
        *    <ul id="pageNum">
                            <li><a href="">首页</a></li>
                            <li class="threeword"><a href="#">上一页</a></li>
                            <li><a href="#">1</a></li>
                            <li><a href="#">2</a></li>

                            <li class="threeword"><a href="javascript:;">下一页</a></li>
                            <li class="threeword"><a href="javascript:;">末页</a></li>
                        </ul>
        *
        * */

        // 定义开始位置和结束位置
        var begin
        var end
        // 显示页码
        if (pageBean.totalPage < 10) {
            // 总页码不够10页
            begin = 1;
            end = pageBean.totalPage;
        } else {
            begin = pageBean.currentPage - 5
            end = pageBean.currentPage + 4

            // 前面不够
            if (begin < 1) {
                begin = 1
                end = begin + 9;
            }

            // 后面不够

            if (end > pageBean.totalPage) {
                end = pageBean.totalPage
                begin = end - 9
            }

        }


        var page_lis = ""
        var firstPage = '<li onclick="pageLoad(' + cid + ',' + 1 + ',\'' + rname + '\')"><a href="javascript:void(0)">首页</a></li>'
        if (currentPage == 1) {
            var beforePage = '<li class="threeword" onclick="pageLoad(' + cid + ',' + 1 + ',\'' + rname + '\')"><a href="javascript:void(0)">上一页</a></li>'
        } else {
            var beforePage = '<li class="threeword" onclick="pageLoad(' + cid + ',' + (currentPage - 1) + ',\'' + rname + '\')"><a href="javascript:void(0)">上一页</a></li>'
        }

        page_lis += firstPage + beforePage
        for (var i = begin; i <= end; i++) {
            // 创建页码的li
            var li
            if (i == pageBean.currentPage) {
                li = '<li class="curPage" onclick="pageLoad(' + cid + ',' + i + ',\'' + rname + '\')"><a href="javascript:void(0)">' + i + '</a></li>'
            } else {
                li = '<li onclick="pageLoad(' + cid + ',' + i + ',\'' + rname + '\')"><a href="javascript:void(0)">' + i + '</a></li>'
            }

            page_lis += li
        }

        if (pageBean.currentPage == pageBean.totalPage) {
            var nextPage = '<li class="threeword" onclick="pageLoad(' + cid + ',' + currentPage + ' ,\'' + rname + '\')"><a href="javascript:void(0);">下一页</a></li>'
        } else {
            var nextPage = '<li class="threeword" onclick="pageLoad(' + cid + ',' + (currentPage + 1) + ' ,\'' + rname + '\')"><a href="javascript:void(0);">下一页</a></li>'
        }

        var lastPage = '<li onclick="pageLoad(' + cid + ',' + pageBean.totalPage + ',\'' + rname + '\')" class="threeword"><a href="javascript:void(0);">末页</a></li>'

        page_lis += nextPage + lastPage

        $("#pageNum").html(page_lis)

        // 2 展示数据

        /**
         * <li>
         <div class="img"><img src="images/04-search_03.jpg" alt=""></div>
         <div class="text1">
         <p>泰国芭提雅三合一日游芭提雅蒂芬妮人妖秀成人门票bigeye含接送</p>
         <br/>
         <p>1-2月出发，网付立享￥1099/2人起！爆款位置有限，抢完即止！</p>
         </div>
         <div class="price">
         <p class="price_num">
         <span>&yen;</span>
         <span>1589</span>
         <span>起</span>
         </p>
         <p><a href="route_detail.html">查看详情</a></p>
         </div>
         </li>
         */

        var route_lis = ""
        for (var i = 0; i < pageBean.list.length; i++) {
            var route = pageBean.list[i]
            route_lis += '<li>\n' +
                '         <div class="img"><img src="' + route.rimage + '" alt="" style="width: 299px"></div>\n' +
                '         <div class="text1">\n' +
                '         <p>' + route.rname + '</p>\n' +
                '         <br/>\n' +
                '         <p>' + route.routeIntroduce + '</p>\n' +
                '         </div>\n' +
                '         <div class="price">\n' +
                '         <p class="price_num">\n' +
                '         <span>&yen;</span>\n' +
                '         <span>' + route.price + '</span>\n' +
                '         <span>起</span>\n' +
                '         </p>\n' +
                '         <p><a href="route_detail.html?rid='+ route.rid +'">查看详情</a></p>\n' +
                '         </div>\n' +
                '         </li>'
        }

        $("#route_list").html(route_lis)
        window.scrollTo(0, 0)
    })

}


