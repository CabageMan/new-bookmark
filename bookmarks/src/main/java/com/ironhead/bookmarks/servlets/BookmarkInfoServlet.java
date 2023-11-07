package com.ironhead.bookmarks.servlets;

import com.ironhead.bookmarks.datasource.DataSource;
import com.ironhead.bookmarks.models.Bookmark;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "bookmarkInfo", value = "/bookmark-info")
public class BookmarkInfoServlet extends HttpServlet {
  private static final String BOOKMARK_ID_PARAMETER = "bookmarkId";
  private static final String BUTTON_PARAMETER = "button";
  private static final String DELETE_BOOKMARK_BUTTON = "deleteBookmark";
  private static final String EDIT_BOOKMARK_BUTTON = "editBookmark";

  private DataSource dataSource;
  private Bookmark bookmark;

  public void init() {
    this.dataSource = DataSource.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    if (request.getParameter(BOOKMARK_ID_PARAMETER) != null) {
      try {
        String bookmarkIdString = request.getParameter(BOOKMARK_ID_PARAMETER);
        long bookmarkId = Long.parseLong(bookmarkIdString);
        bookmark = dataSource.queryBookmarkByID(bookmarkId);
      } catch (NumberFormatException e) {
        System.out.println("Couldn't get bookmark ID");
        throw new ServletException("Couldn't get bookmark ID");
      }
    }

    request.setAttribute("bookmark", bookmark);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/bookmarkInfo.jsp");
    requestDispatcher.include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String buttonValue = request.getParameter(BUTTON_PARAMETER);

    if (buttonValue.equals(DELETE_BOOKMARK_BUTTON)) {
      System.out.println("Delete Bookmark by ID: " + bookmark.getId());
      if (dataSource.deleteBookmark(bookmark)) {
        request.getRequestDispatcher("/bookmarksList.jsp").forward(request, response);
      } else {
        request.getRequestDispatcher("/bookmarkInfo.jsp").include(request, response);
      }
    } else if (buttonValue.equals(EDIT_BOOKMARK_BUTTON)) {
      request.setAttribute("bookmark", bookmark);
      request.getRequestDispatcher("/bookmarkEdit.jsp").forward(request, response);
    } else {
      System.out.println("BookmarkInfo: Something went wrong");
      request.getRequestDispatcher("/bookmarkInfo.jsp").include(request, response);
    }
  }

  public void destroy() {
    dataSource.removeInstance();
    System.out.println("Bookmark Info Servlet Destroy is called");
  }
}
