<%--
  Created by IntelliJ IDEA.
  User: victorb
  Date: 10/30/23
  Time: 7:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.ironhead.bookmarks.models.Bookmark" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookmark Info</title>
</head>
<body>

<h1>Bookmark Info</h1>
<br/>

<h3>Title</h3>
<% Bookmark bookmark = (Bookmark) request.getAttribute("bookmark"); %>
<p><%= bookmark.getTitle() %></p>
<h3>Description</h3>
<p><%= bookmark.getInfo() %></p>
<br/>

<form action="${pageContext.request.contextPath}/bookmark-info" method="post">
    <button type="submit" name="button" value="deleteBookmark">Delete</button>
    <button type="submit" name="button" value="editBookmark">Edit</button>
</form>

<a href="bookmarks-list">View all bookmarks</a>
</body>
</html>
