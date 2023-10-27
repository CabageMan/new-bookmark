<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookmarks</title>
</head>

<body>
<h1><%= "Welcome to Bookmarks!" %></h1>
<br/>

<form action="${pageContext.request.contextPath}/bookmarks-list" method="post">
    <label for="bookmarkTitle">Add new bookmark:</label>
    <br/>
    <input type="text" name="bookmarkTitle" id="bookmarkTitle" placeholder="Enter bookmark title">
    <br/>
    <label for="bookmarkDescription">Bookmark:</label>
    <br/>
    <textarea id="bookmarkDescription" name="bookmarkDescription" placeholder="Enter your bookmark..." rows="5"></textarea>
    <br/>
    <button type="submit" name="button" value="addBookmark">Add Bookmark</button>
</form>

<br/>

<h2><%= "Your Bookmarks" %></h2>
<br/>
<form action="${pageContext.request.contextPath}/bookmarks-list" method="post">
    <label for="searchByTitle">Filter bookmarks:</label>
    <br/>
    <input type="text" name="searchByTitle" id="searchByTitle" placeholder="Enter bookmark title">
    <br/>
    <button type="submit" name="button" value="searchBookmarks">Search</button>
</form>
<br/>

<%--    <c:set var="bookmarksList" value="${list}"/>--%>

    <%-- <c:forEach var="num" items="${bookmark}"> --%>
        <%-- <c:out value="${num}"/> --%>
    <%-- </c:forEach> --%>

<%--    <ul>--%>
<%--        ${bookmarksList}--%>
<%--    </ul>--%>
<ul>
    <li><a href="bookmark-info">User bookmark info</a></li>
</ul>

</body>
</html>