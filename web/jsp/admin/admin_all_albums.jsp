<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 17.08.2016
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Albums</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../css/styles.css" media="screen,projection"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="../../js/bin/materialize.min.js"></script>
</head>
<body>
    <div class="wrapper">
        <c:import url="../../jsp/header.jsp"/>
        <div class="container">
            <div class="card">
                <ul class="collection">
                    <c:forEach items="${requestScope.all_albums}" var="album">
                        <li class="collection-item avatar">
                            <img src="${album.imageFilePath}" alt="" class="circle">
                            <span class="title">${album.name}</span>
                            <p>${album.year}</p>
                            <div class="secondary-content">
                                <div style="float: left">
                                    <form action="${pageContext.request.contextPath}/controller" method="POST">
                                        <input type="hidden" name="command" value="find_album_for_update">
                                        <input type="hidden" name="album_id" value="${album.albumId}">
                                        <button class="waves-effect waves-light btn  green accent-4" type="submit">Edit</button>
                                    </form>
                                </div>
                                <div style="float: right">
                                    <form action="${pageContext.request.contextPath}/controller" method="POST">
                                        <input type="hidden" name="command" value="delete_album">
                                        <input type="hidden" name="album_id" value="${album.albumId}">
                                        <button class="waves-effect waves-light btn red" type="submit">Del</button>
                                    </form>
                                </div>

                            </div>
                        </li>
                    </c:forEach>
                    <li class="collection-item right">
                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                            <input type="hidden" name="command" value="find_album_for_create">
                            <button class="waves-effect waves-light btn  green accent-4" type="submit">Create new album</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <c:import url="../../jsp/footer.jsp"/>
</body>
</html>