<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 10.08.2016
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="by.epam.webpoject.ezmusic.util.RandomTokenGenerator" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.page_content"/>
<html>
<head>
    <title><fmt:message key="title.edit_song"/></title>
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"
          media="screen,projection"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bin/materialize.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script>
        $(document).ready(function () {
            $('#id-authors').on("change", function (event) {
                var selectedAuthors = $('#id-authors').val();
                $.ajax({
                    url: 'jsoncontroller',
                    type: 'get',
                    dataType: 'json',
                    data: {selected_authors: selectedAuthors, command: "find_author_albums_json"},
                    success: function (json) {
                        var $select = $('#id-albums');
                        $select.find('option').remove();
                        $select.material_select('update');
                        $select.closest('.input-field').children('span.caret').remove();
                        $.each(json, function (index, album) {
                            if (json.length != 0) {
                                $select.append($("<option></option>").val(album.albumId).html(album.name));
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
<c:import url="${pageContext.request.contextPath}/jsp/header.jsp"/>
<c:import url="${pageContext.request.contextPath}/jsp/admin/admin_navbar.jsp"/>
<main>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col s8 offset-s2">
                    <div class="card z-depth-4">
                        <div class="card-content">
                            <span class="card-title text-darken-2"><fmt:message key="title.song"/></span>
                            <form action="${pageContext.request.contextPath}/controller" method="POST"
                                  enctype="multipart/form-data">
                                <c:if test="${not empty requestScope.song}">
                                    <input type="hidden" name="command" value="update_song">
                                    <input type="hidden" name="song_id" value="${requestScope.song.songId}">
                                </c:if>
                                <c:if test="${empty requestScope.song}">
                                    <input type="hidden" name="command" value="create_song">
                                </c:if>
                                <input type="hidden" name="token" value="${RandomTokenGenerator.nextToken()}">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input id="id-song-name" type="text" name="song_name"
                                               value="${requestScope.song.name}" required>
                                        <label for="id-song-name"><fmt:message key="label.name"/></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <input id="id-song-year" type="text" name="song_year"
                                               value="${requestScope.song.year}" required pattern="^\d{4}$">
                                        <label for="id-song-year"><fmt:message key="label.year"/></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <select multiple class="icons" id="id-authors" name="selected_authors">
                                            <option value="" disabled selected>Select authors</option>
                                            <c:forEach items="${requestScope.all_authors}" var="song">
                                                <c:choose>
                                                    <c:when test="${fn:contains(requestScope.song_authors, song)}">
                                                        <option value="${song.authorId}" selected
                                                                data-icon="${pageContext.request.contextPath}/img/album.jpeg"
                                                                class="circle">${song.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${song.authorId}"
                                                                data-icon="${pageContext.request.contextPath}/img/album.jpeg"
                                                                class="circle">${song.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="id-authors"><fmt:message key="label.authors"/></label>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="input-field col s12">
                                        <select multiple class="icons" id="id-albums" name="selected_albums">
                                            <option value="" disabled selected>Select album</option>
                                            <c:forEach items="${requestScope.all_albums}" var="song">
                                                <c:choose>
                                                    <c:when test="${fn:contains(requestScope.song_albums, song)}">
                                                        <option value="${song.albumId}" selected>${song.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${song.albumId}">${song.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="id-albums"><fmt:message key="label.albums"/></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <select multiple class="icons" id="id-tags" name="selected_tags">
                                            <option value="" disabled selected>Select tags</option>
                                            <c:forEach items="${requestScope.all_tags}" var="tag">
                                                <c:choose>
                                                    <c:when test="${fn:contains(requestScope.song_tags, tag)}">
                                                        <option value="${tag.tagId}" selected>${tag.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${tag.tagId}">${tag.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="id-tags">Song tags</label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <select multiple class="icons" id="id-rewards" name="selected_rewards">
                                            <option value="" disabled selected>Select rewards</option>
                                            <c:forEach items="${requestScope.all_rewards}" var="reward">
                                                <c:choose>
                                                    <c:when test="${fn:contains(requestScope.song_rewards, reward)}">
                                                        <option value="${reward.rewardId}" selected>${reward.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${reward.rewardId}">${reward.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="id-rewards">Song rewards</label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="input-field col s12">
                                        <select  class="icons" id="id-genres" name="genre_id">
                                            <option value="" disabled selected>Select genres</option>
                                            <c:forEach items="${requestScope.all_genres}" var="genre">
                                                <c:choose>
                                                    <c:when test="${requestScope.song.genre.genreId == genre.genreId}">
                                                        <option value="${genre.genreId}" selected>${genre.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${genre.genreId}">${genre.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label for="id-genres">Song genres</label>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="input-field col s12">
                                        <input id="id-song-cost" type="text" name="song_cost"
                                               value="${requestScope.song.cost}" required pattern="[0-9]*\.?[0-9]*">
                                        <label for="id-song-cost"><fmt:message key="label.cost"/></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="file-field input-field">
                                        <div class="btn black">
                                            <span class="white-text"><fmt:message key="button.file"/></span>
                                            <input type="file" name="song_file_path">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input id="id-song-file-path" name="old_song_file_path" class="file-path"
                                                   type="text" value="${requestScope.song.filePath}" required>
                                        </div>
                                    </div>

                                </div>

                                <div class="card-action">
                                    <div class="row">
                                        <button class="btn col s12 black" type="submit"><fmt:message
                                                key="button.save_changes"/></button>
                                    </div>
                                </div>
                            </form>
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
