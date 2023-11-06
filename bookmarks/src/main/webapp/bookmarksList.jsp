<%--
  Created by IntelliJ IDEA.
  User: victorb
  Date: 10/30/23
  Time: 6:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.List"%>
<%@page import="com.ironhead.bookmarks.models.Bookmark"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookmarks List</title>
</head>
<body>

<h1><%= "Your Bookmarks" %></h1>
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

<table border="1">
    <thead>
    <tr>
        <th>#</th>
        <th>Title</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <% List<Bookmark> bookmarks = (List) request.getAttribute("bookmarks"); %>

    <% for (Bookmark bookmark : bookmarks) { %>
    <tr>
        <td><%=bookmark.getId()%></td>
        <td>
            <a href="bookmark-info?bookmarkId=<%=bookmark.getId()%>>"><%=bookmark.getTitle()%></a>
        </td>
        <td><%=bookmark.getInfo()%></td>
    </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
