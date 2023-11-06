package com.ironhead.bookmarks.servlets;

import java.io.*;
import java.util.ArrayList;

import com.ironhead.bookmarks.datasource.DataSource;
import com.ironhead.bookmarks.models.Bookmark;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "bookmarksList", value = "/bookmarks-list")
public class BookmarksListServlet extends HttpServlet {
  private static final String BUTTON_PARAMETER = "button";
  private static final String ADD_BOOKMARK_BUTTON = "addBookmark";
  private static final String SEARCH_BOOKMARK_BUTTON = "searchBookmarks";
  private static final String BOOKMARK_TITLE_FIELD = "bookmarkTitle";
  private static final String BOOKMARK_DESCRIPTION_FIELD = "bookmarkDescription";
  private static final String SEARCH_BOOKMARK_FIELD = "searchByTitle";

  private DataSource dataSource;

  public void init() {
    System.out.println("Init BookmarksList servlet");

    this.dataSource = DataSource.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ArrayList<Bookmark> bookmarks = dataSource.queryBookmarks();

    response.setContentType("text/html");

    request.setAttribute("bookmarks", bookmarks);

    RequestDispatcher requestDispatcher = request.getRequestDispatcher("bookmarksList.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    String buttonValue = request.getParameter(BUTTON_PARAMETER);
    ArrayList<Bookmark> bookmarks = new ArrayList<>();

    if (buttonValue.equals(ADD_BOOKMARK_BUTTON)) {
      dataSource.addBookmark(
              request.getParameter(BOOKMARK_TITLE_FIELD),
              request.getParameter(BOOKMARK_DESCRIPTION_FIELD)
      );
      // Improve this!
      bookmarks = dataSource.queryBookmarks();
    } else if (buttonValue.equals(SEARCH_BOOKMARK_BUTTON)) {
      String bookmarkTitle = request.getParameter(SEARCH_BOOKMARK_FIELD);
      bookmarks = (bookmarkTitle == null || bookmarkTitle.isEmpty()) ? dataSource.queryBookmarks() : dataSource.queryBookmarksByTitle(bookmarkTitle);
    } else {
      System.out.println("BookmarksList: Something went wrong");
    }

    request.setAttribute("bookmarks", bookmarks);
    request.getRequestDispatcher("/bookmarksList.jsp").forward(request, response);
  }

  public void destroy() {
    System.out.println("List Servlet Destroy is called");
  }
}