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

@WebServlet(name = "bookmarkEdit", value = "/bookmark-edit")
public class BookmarkEditServlet extends HttpServlet {
  private static final String BUTTON_PARAMETER = "button";
  private static final String UPDATE_BOOKMARK_BUTTON = "updateBookmark";
  private static final String BOOKMARK_TITLE_FIELD = "bookmarkTitle";
  private static final String BOOKMARK_DESCRIPTION_FIELD = "bookmarkDescription";

  private DataSource dataSource;

  public void init() {
    this.dataSource = DataSource.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/bookmarkEdit.jsp");
    requestDispatcher.include(request, response);
    System.out.println("Bookmark Info Do Get");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String buttonValue = request.getParameter(BUTTON_PARAMETER);
    String bookmarkIdParam = request.getParameter("bookmarkId");

    if (buttonValue.equals(UPDATE_BOOKMARK_BUTTON) || bookmarkIdParam != null) {
      long bookmarkId = Long.parseLong(bookmarkIdParam);
      String bookmarkTitle = request.getParameter(BOOKMARK_TITLE_FIELD);
      String bookmarkDescription = request.getParameter(BOOKMARK_DESCRIPTION_FIELD);
      Bookmark updatedBookmark = new Bookmark(bookmarkId, bookmarkTitle, bookmarkDescription);
      if (dataSource.updateBookmark(updatedBookmark)) {
        request.getRequestDispatcher("/bookmarksList.jsp").forward(request, response);
      } else {
        request.getRequestDispatcher("/bookmarkEdit.jsp").include(request, response);
      }
    } else {
      System.out.println("BookmarkEdit: Something went wrong");
      request.getRequestDispatcher("/bookmarkEdit.jsp").include(request, response);
    }
  }

  public void destroy() {
    dataSource.removeInstance();
    System.out.println("Bookmark Edit Servlet Destroy is called");
  }
}
