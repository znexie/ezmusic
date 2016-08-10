<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 08.08.2016
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.page_content"/>
<html>
<head>
    <title>Header</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../css/styles.css" media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8">
</head>
<body>
<nav class="nav-wrapper cyan darken-4">
    <a href="#" class="brand-logo">EZMusic</a>
    <div class="row right">
        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="waves-effect waves-light btn  green accent-4"><fmt:message key="button.sign_in"/></a>
        <a href="${pageContext.request.contextPath}/jsp/register.jsp" class="waves-effect waves-light btn  green accent-4"><fmt:message key="button.sign_up"/></a>
    </div>
</nav>
</body>
</html>
