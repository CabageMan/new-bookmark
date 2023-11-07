<%--
  Created by IntelliJ IDEA.
  User: victorb
  Date: 10/31/23
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.ironhead.bookmarks.models.Bookmark" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Bookmark</title>
</head>
<body>

<h1>Edit Bookmark</h1>
<br/>

<% Bookmark bookmark = (Bookmark) request.getAttribute("bookmark"); %>
<form action="${pageContext.request.contextPath}/bookmark-edit?bookmarkId=<%=bookmark.getId()%>" method="post">
    <label for="bookmarkTitle">Change bookmark title:</label>
    <br/>
    <input type="text" name="bookmarkTitle" id="bookmarkTitle" value="<%=bookmark.getTitle()%>" placeholder="Enter new bookmark title">
    <br/>
    <label for="bookmarkDescription">Change bookmark description:</label>
    <br/>
    <textarea id="bookmarkDescription" name="bookmarkDescription" placeholder="Enter your bookmark..." rows="5"><%=bookmark.getInfo()%></textarea>
    <br/>
    <button type="submit" name="button" value="updateBookmark">Add Bookmark</button>
</form>

<br/>

<a href="bookmarks-list">View all bookmarks</a>

</body>
</html>
