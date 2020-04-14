<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>cmfz Login</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${path}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${path}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${path}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${path}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${path}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${path}/login/assets/js/scripts.js"></script>
    <script src="${path}/login/assets/js/verificationCod.js"></script>
    <script src="${path}/login/assets/js/jquery.validate.min.js"></script>

    <script>
        var code;//在全局 定义验证码

        //验证规则
        $.extend($.validator.messages, {
            required: "<span style='color: #c7254e'>这是必填字段</span>",
            email: "请输入有效的电子邮件地址",
            maxlength: $.validator.format("最多可以输入 {16} 个字符"),
            minlength: $.validator.format("最少要输入 {8} 个字符"),
            rangelength: $.validator.format("请输入长度在 {8} 到 {16} 之间的字符串"),
        });

        function show() {
            //显示验证码
            $("#captchaImage").prop("src", "${path}/code/jsCode?code=" + createCode());
        }

        //懒加载函数
        $(function () {
            //展示验证码
            show();
            //点击更换验证码
            $("#captchaImage").click(function () {
                show();
            });
            //点击提交表单事件
            $("#loginButtonId").click(function(){
                var flag = $("#loginForm").valid();
                //验证规则通过ajax异步提交数据
                if (flag) {
                    var val = $("#form-code").val();
                    //验证验证码输入正确
                    if(val===code){
                       //发送ajax异步请求
                        $.ajax({
                            url: "${path}/admin/login",
                            dataType: "JSON",
                            type: "post",
                            data: $("#loginForm").serialize(),
                            success: function (data) {
                                //判断登录成功，登录失败
                                if(data.status=="200"){
                                    //登陆成功  跳转至首页
                                    location.href="${path}/main/main.jsp"
                                }else{
                                    //登陆失败 展示错误信息
                                    $("#msgDiv").html("<span style='color: red'><strong>"+data.message+"</strong></span>")
                                }
                            }
                        });
                    }else{
                       //验证验证码失败
                        $("#msgDiv").html("<span style='color: #c7254e'>验证码输入错误</span>");
                        show();
                    }
                }
            });
        })

    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showAll</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" required id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" required minlength="8" maxlength="16"
                                       id="form-password">
                            </div>
                            <div class="form-group">
                                <%--<label class="sr-only" for="form-code">Code</label>--%>
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="" title="点击更换验证码">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       required type="test" name="enCode" id="form-code">
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="copyrights">Collect from <a href="http://www.cssmoban.com/" title="网站模板">网站模板</a></div>


<!-- Javascript -->

<!--[if lt IE 10]>
<script src="assets/js/placeholder.js"></script>
<![endif]-->

</body>

</html>