<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 19.08.2016
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit author</title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="../../css/styles.css" media="screen,projection"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="../../js/bin/materialize.min.js"></script>
    <script>
        $(document).ready(function() {
            $('select').material_select();
        });
    </script>
    <script>
        $(document).ready(function() {
            $('#id-albums').on("change", function(event) {
                var selectedAlbums = $('#id-albums').val();
                $.ajax({
                    url: 'JsonController',
                    type: 'post',
                    dataType: 'json',
                    data: {selected_albums:selectedAlbums, command:"find_album_songs_json"},
                    success: function (json) {
                        var $select = $('#id-songs');
                        $select.find('option').remove();
                        $select.material_select('update');
                        $select.closest('.input-field').children('span.caret').remove();
                        $.each(json, function(index,song ) {
                            if(json.length != 0) {
                                $select.append($("<option></option>").val(song.songId).html(song.name));
                                $select.material_select('update');
                                $select.closest('.input-field').children('span.caret').remove();
                            }
                        });
                    }
                });
            });
        });
    </script>
</head>
<body>
<c:import url="../header.jsp"/>
<c:import url="admin_navbar.jsp"/>
<main>
<div class="wrapper">
    <div class="container">
        <div class="row">
            <div class="col s8 offset-s2">
                <div class="card z-depth-4">
                    <div class="card-content">
                        <span class="card-title text-darken-2">Author</span>
                        <form action="${pageContext.request.contextPath}/controller" method="POST">
                            <c:if test="${not empty author}">
                                <input type="hidden" name="command" value="update_author">
                                <input type="hidden" name="author_id" value="${author.authorId}">
                            </c:if>
                            <c:if test="${empty author}">
                                <input type="hidden" name="command" value="create_author">
                            </c:if>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="id-author-name" type="text" name="author_name" value="${author.name}">
                                    <label for="id-author-name">Name</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="id-author-country" type="text" name="author_country" value="${author.country}">
                                    <label for="id-author-country">Country</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <select multiple class="icons" id="id-albums" name="selected_albums">
                                        <option value="" disabled selected>Select albums</option>
                                        <c:forEach items="${requestScope.all_albums}" var="item">
                                            <c:choose>
                                                <c:when test="${fn:contains(author_albums, item)}">
                                                    <option value="${item.albumId}" selected data-icon="${pageContext.request.contextPath}/img/album.jpeg" class="circle">${item.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${item.albumId}" data-icon="${pageContext.request.contextPath}/img/album.jpeg" class="circle">${item.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                    <label for="id-albums">Albums</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12">
                                    <select multiple class="icons" id="id-songs" name="selected_songs">
                                        <option value="" disabled selected>Select songs</option>
                                        <c:forEach items="${requestScope.all_songs}" var="item">
                                            <c:choose>
                                                <c:when test="${fn:contains(author_songs, item)}">
                                                    <option value="${item.songId}" selected >${item.name}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${item.songId}">${item.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                    <label for="id-songs">Songs</label>
                                </div>
                            </div>

                            <div class="row">
                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>File</span>
                                        <input type="file">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" type="text" name="author_photo_path" value="${author.photoPath}">
                                    </div>
                                </div>
                            </div>

                            <div class="card-action">
                                <div class="row">
                                    <button class="btn col s12 black" type="submit">Save changes</button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/jsp/footer.jsp"/>
</div>
</main>
<c:if test="${requestScope.message != null}">
    <script> Materialize.toast('${requestScope.message}', 4000);</script>
</c:if>

</body>
</html>
