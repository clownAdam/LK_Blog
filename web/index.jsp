<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<script src="${ctx}/js/template.js"></script>
<style>
    .content_item {
        height: 160px;
        position: relative;
    }

    .content_item img {
        position: absolute;
        right: 10px;
        top: 10px;
        width: 250px;
        height: 140px;
    }

</style>
<!-- 内容区 -->
<section class="layout main-wrap  content">
    <section class='col-main'>

        <article class="mainarea" style="display:block;">
            <div class="blog-tab">

                <div class="tab-content" id="tab_content">
                <%--分类类别显示--%>
                </div>
            </div>
        </article>
        <!--博客社区-->
        <article class="mainarea" style="display:block;">
            <div class="blog-tab">

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane fade in active" id="home">

                        <div id="lk_blog_list" class="container">
                            <div class="row">
                                <ul class="blog-list" id="content">


                                    <%--<li class="content_item">
                                        <div class="blog-list-left" style="float: left;">
                                            <div class="main-title">
                                                <a href="detail.jsp">标题标题标题标题标题标题</a>
                                            </div>
                                            <p class="sub-title">描述描述描述描述描述描述描述描述</p>
                                            <div class="meta">
                                                2020-08-31
                                            </div>
                                        </div>
                                        <img src="images/blog_img.png" alt="" class="img-rounded">
                                    </li>--%>
                                </ul>
                                <div id="page" class="page_div"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </section>

</section>
<footer id="lk_footer">
    <div class="container">
        <div class="footer-top">
          <!--分页-->
        </div>
        <div class="footer-bottom col-sm-offset-2 hidden-sm hidden-xs">
            <ul>
                <li><a href="">学科报名</a></li>
                <li><a href="">师资团队</a></li>
                <li><a href="">线上公开课</a></li>
                <li><a href="">联络我们</a></li>
                <li><a href="">支持我们</a></li>
                <li><a href="">沪ICP备 18026591号-1</a></li>
            </ul>
        </div>
    </div>
</footer>
<%--定义模板--%>
<script id="litmp" type="text/html">
    {{each list as value}}
    <li class="content_item">
        <div class="blog-list-left" style="float: left;">
            <div class="main-title">
                <a href="detail.jsp?id={{value.article_id}}">{{value.article_title}}</a>
            </div>
            <p class="sub-title">{{value.article_desc}}</p>
            <div class="meta">
                {{value.article_time | dateFormat:'yyyy-MM-dd'}}
            </div>
        </div>
        <img src="${ctx}/upload/{{value.article_pic}}" alt="" class="img-rounded">
    </li>
    {{/each}}

</script>
<script id="categorytmp" type="text/html">
    <div role="tabpanel" class="tab-pane fade in active" id="tab">
        <%--分类信息--%>
        <div id="lk_blog_two" class="container">
            <div class="row">
                {{each list as value}}
                <button class="btn-tag category_btn" data-cid="{{value.cid}}">{{value.cname}}</button>
<%--                <button class="btn-tag">面向对象</button>--%>
<%--                <button class="btn-tag">jdbc</button>--%>
<%--                <button class="btn-tag">web服务器</button>--%>
                {{/each}}
            </div>
        </div>
    </div>
</script>
<script>
    /*日期处理功能：时间戳转换*/
    template.helper('dateFormat',function (date,format) {
        date = new Date(date);
        var map ={
            "M":date.getMonth()+1,//月份
            "d":date.getDate(),//日
            "h":date.getHours(),//小时
            "m":date.getMinutes(),//分
            "s":date.getSeconds(),//秒
            "q":Math.floor(date.getMonth()+3/3),//季度
            "S":date.getMilliseconds()//毫秒
        };
        format = format.replace(/([yMdhmsqS])+/g,function (all,t) {
           var v = map[t];
           if(v!==undefined){
               if(all.length>1){
                   v = '0'+v;
                   v = v.substr(v.length-2);
               }
               return v;
           }else if(t === 'y'){
               return (date.getFullYear()+'').substr(4-all.length);
           }
           return all;
        });
        return format;
    });
    /*获取当前参数*/
    function getParams(key){
        var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null){
            return unescape(r[2]);
        }
        return null;
    };
    var parentid = getParams("parentid");
    // alert(res);
    // var name = getParams("name");
    if(parentid !=null){
        /*根据parentid加载子级*/
        $.post("${pageContext.request.contextPath}/article_getCategory.action", {"parentid": parentid}, function (data) {
            console.log("data");
            console.log(data);
            var html = template('categorytmp',{list:data});
            $('#tab_content').html(html);
        }, "json");
        /*加载数据*/
        getPageList(1,parentid);

    }else{
        /*加载分页列表数据*/
        getPageList(1,null,null);
    }
    // alert(name);

    /*$(".category_btn").click(function () {
        alert('111');
    });*/
    $("body").on("click",".category_btn",function () {
        // alert('1111');
        var cid = $(this).data("cid");
        // alert(cid);
        getPageList(1,null,cid);
    });
    //加载分类数据
    function getPageList(currPage,parentid,cid) {
        $.post("${ctx}/web_getPageList.action",{currPage:currPage,parentid:parentid,cid:cid},function (data) {
            // var fe = JSON.parse(data).list;
            // console.log(data);
            var html = template('litmp', {list:data.list});
            // document.getElementById("content").innerHTML = html;
            $("#content").html(html);
            /*分页*/
            $("#page").paging({
                pageNo:data.currentPage,
                totalPage:data.totalPage,
                totalSize:data.totalCount,
                callback:function (num) {
                    // alert(num);
                    getPageList(num,parentid,cid);
                }
            });
        });
    }
</script>
</body>
</html>