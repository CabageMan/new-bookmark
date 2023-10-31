<%--
  Created by IntelliJ IDEA.
  User: victorb
  Date: 10/30/23
  Time: 7:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1><%= "Bookmark Info" %></h1>
<br/>

<h3>Title</h3>
<p><%= "Bookmark Title" %></p>
<h3>Description</h3>
<p><%= "Bookmark Description" %></p>

<br/>

<form action="${pageContext.request.contextPath}/bookmark-info" method="post">
    <button type="submit" name="button" value="deleteBookmark">Delete</button>
    <button type="submit" name="button" value="editBookmark">Edit</button>
</form>

<a href="bookmarks-list">View all bookmarks</a>
</body>
</html>
