<%--
  Created by IntelliJ IDEA.
  User: hanpeng
  Date: 2019/4/15
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <form method="post" action="login">
        用户名:<input name="username" type="text"/><br/>
        密码:<input name="password" type="password"/> <br>
        <input type="submit">
        ${msg}
    </form>
</body>
</html>
