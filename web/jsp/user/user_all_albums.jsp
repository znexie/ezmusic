<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 29.08.2016
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.page_content"/>
<html>
<head>
    <title>Albums</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" media="screen,projection"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bin/materialize.min.js"></script>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/header.jsp"/>
<c:import url="${pageContext.request.contextPath}/jsp/user/user_navbar.jsp"/>
<main>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col s8 offset-s2">
                    <div class="card z-depth-4">
                        <div class="card-content">
                            <span class="card-title"><fmt:message key="title.all_albums"/></span>

                            <c:if test="${not empty requestScope.all_albums}">
                                <ul class="collection">
                                    <c:forEach items="${requestScope.all_albums}" var="album">
                                        <li class="collection-item avatar">
                                            <img src="${pageContext.request.contextPath}/${album.imageFilePath}" alt="album" class="circle">
                                            <span class="title"><b><fmt:message key="title.name"/></b>${album.name}</span>
                                            <p><b><fmt:message key="title.year"/></b>${album.year}</p>
                                            <div class="secondary-content">
                                                <form action="${pageContext.request.contextPath}/controller" method="POST">
                                                    <input type="hidden" name="command" value="find_album_user">
                                                    <input type="hidden" name="album_id" value="${album.albumId}">
                                                    <button class="btn-floating black" type="submit"><i class="material-icons">info_outline</i></button>
                                                </form>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="${pageContext.request.contextPath}/jsp/footer.jsp"/>
    </div>
</main>
<c:if test="${requestScope.message != null}">
    <c:set var="message"><fmt:message key="${requestScope.message}"/></c:set>
    <ctg:message message="${message}"/>
</c:if>
</body>
</html>