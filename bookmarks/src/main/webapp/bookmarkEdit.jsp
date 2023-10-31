<%--
  Created by IntelliJ IDEA.
  User: victorb
  Date: 10/31/23
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Bookmark</title>
</head>
<body>

<h1><%= "Edit Bookmark" %></h1>
<br/>

<form action="${pageContext.request.contextPath}/bookmark-edit" method="post">
    <label for="bookmarkTitle">Add new bookmark:</label>
    <br/>
    <input type="text" name="bookmarkTitle" id="bookmarkTitle" placeholder="Enter new bookmark title">
    <br/>
    <label for="bookmarkDescription">Bookmark:</label>
    <br/>
    <textarea id="bookmarkDescription" name="bookmarkDescription" placeholder="Enter your bookmark..." rows="5"></textarea>
    <br/>
    <button type="submit" name="button" value="updateBookmark">Add Bookmark</button>
</form>

<br/>

<a href="bookmarks-list">View all bookmarks</a>

</body>
</html>
