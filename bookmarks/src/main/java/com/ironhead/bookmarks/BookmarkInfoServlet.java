package com.ironhead.bookmarks;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "bookmarkInfo", value = "/bookmark-info")
public class BookmarkInfoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/bookmarksList.jsp");
    requestDispatcher.include(request, response);
    System.out.println("Do Get");
  }
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
  }

  public void destroy() {
    System.out.println("Info Servlet Destroy is called");
  }
}
