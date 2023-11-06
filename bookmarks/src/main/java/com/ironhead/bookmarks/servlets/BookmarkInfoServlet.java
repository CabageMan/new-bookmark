package com.ironhead.bookmarks.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "bookmarkInfo", value = "/bookmark-info")
public class BookmarkInfoServlet extends HttpServlet {
  public static final String BOOKMARK_ID_PARAMETER = "bookmarkId";
  private static final String BUTTON_PARAMETER = "button";
  private static final String DELETE_BOOKMARK_BUTTON = "deleteBookmark";
  private static final String EDIT_BOOKMARK_BUTTON = "editBookmark";

  public void init() {
    System.out.println("Init Bookmarks Info servlet");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/bookmarkInfo.jsp");
    String bookmarkId = request.getParameter(BOOKMARK_ID_PARAMETER);
    System.out.println("Bookmark Info Do Get By ID: " + bookmarkId);
    
    requestDispatcher.include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String buttonValue = request.getParameter(BUTTON_PARAMETER);

    if (buttonValue.equals(DELETE_BOOKMARK_BUTTON)) {
      System.out.println("Delete Bookmark");
      request.getRequestDispatcher("/bookmarksList.jsp").forward(request, response);
    } else if (buttonValue.equals(EDIT_BOOKMARK_BUTTON)) {
      System.out.println("Edit Bookmark");
      request.getRequestDispatcher("/bookmarkEdit.jsp").forward(request, response);
    } else {
      System.out.println("BookmarkInfo: Something went wrong");
      request.getRequestDispatcher("/bookmarkInfo.jsp").include(request, response);
    }
  }

  public void destroy() {
    System.out.println("Bookmark Info Servlet Destroy is called");
  }
}
