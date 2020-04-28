<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    pageContext.setAttribute("ctx", ctx);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${ctx }/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/amazeui.min.css"/>
    <script type="text/javascript" charset="UTF-8" src="${ctx }/js/umedit/ueditor.config.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${ctx }/js/umedit/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${ctx }/js/umedit/lang/zh-cn/zh-cn.js"></script>
    <script src="${ctx }/js/jquery.min.js"></script>
</head>
<body>


<div class="main_top">
    <div class="am-cf am-padding am-padding-bottom-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">添加文章
        </strong><small></small></div>
    </div>
    <hr>
    <form id="blog_form" action="${ctx}/article_add.action" method="post" enctype="multipart/form-data">
        <div class="edit_content">
            <div class="item1">
                <div>
                    <span>文章标题：</span>
                    <input type="text" class="am-form-field" name="article_title" style="width: 300px">&nbsp;&nbsp;
                </div>
            </div>
            <%--文章描述--%>
            <input type="text" name="article_desc" id="article_desc" style="display: none;">


            <div class="item1">
                <span>所属分类：</span>
                <select id="category_select" name="category.parentid" style="width: 150px">&nbsp;&nbsp;
                    <%--<option value="aaa">aaa</option>--%>
                </select>

                <select id="skill_select" name="category.cid" style="width: 150px">&nbsp;&nbsp;
                    <%--<option value="aaa">aa</option>--%>
                </select>

            </div>

            <div class="item1 update_pic">
                <span>摘要图片：</span>
                <img src="" id="imageview" class="item1_img" style="display: none;">
                <label for="fileupload" id="label_file">上传文件</label>
                <input type="file" name="upload" id="fileupload"/>
            </div>
            <%--集成umedit--%>
            <div id="editor" name="article_content" style="width:900px;height:400px;"></div>
            <button class="am-btn am-btn-default" type="button" id="send" style="margin-top: 10px;">
                发布
            </button>
        </div>

    </form>

</div>
<script>
    $(function () {
        /*初始化富文本umedit编辑器*/
        var ue = UE.getEditor('editor');
        //发送请求，获取分类的数据。
        $.post("${pageContext.request.contextPath}/article_getCategory.action", {"parentid": 0}, function (data) {
            console.log(data);
            $(data).each(function (i, obj) {
                console.log(obj.cname);
                $("#category_select").append("<option value=" + obj.cid + ">" + obj.cname + "</option>");
            });
            //出发change事件
            $("#category_select").trigger("change");
        }, "json");
        /*////////////////////////////////////////////*/

        /*监听分类的改变*/
        $("#category_select").on("change", function () {
            // alert("aaa");
            //获取当前选中的id
            var cid = $("#category_select").val();
            // alert(cid);
            //清空之前的标签
            $("#skill_select").empty();
            $.post("${pageContext.request.contextPath}/article_getCategory.action", {"parentid": cid}, function (data) {
                console.log(data);
                $(data).each(function (i, obj) {
                    // console.log(obj.cname);
                    $("#skill_select").append("<option value=" + obj.cid + ">" + obj.cname + "</option>");
                });
            }, "json");
        });

        /*讲file:///-->http://显示图片*/
        $("#fileupload").change(function () {
            var $file = $(this);
            var objUrl = $file[0].files[0];
            /*获得一个http格式的url路径*/
            var windowURL = window.URL || window.webkitURL;
            /*createObjectURL创建一个指向该参数对象图片的URL*/
            var dataURL;
            dataURL = windowURL.createObjectURL(objUrl);
            $("#imageview").attr("src", dataURL);
            console.log($("#imageview").attr('style')+dataURL);

            if ($('#imageview').attr('style') === 'display: none;') {
                console.log("hello");
                $('#imageview').attr('style', 'inline');
                $('#imageview').width('300px');
                $('#imageview').height('200px');
                $('.update_pic').attr('style', 'margin-bottom:80px;');
            }
            console.log($("#imageview").attr('style')+dataURL);
        });

        $("#send").click(function () {
            /*设置文本的描述*/
            //获取富文本的正文
            var txt = ue.getContentTxt();
            txt= txt.substring(0,150)+"...";
            //设置描述
            $("#article_desc").val(txt);
            /*提交表单*/
            $("#blog_form").submit();
        });
    });
</script>
</body>
</html>