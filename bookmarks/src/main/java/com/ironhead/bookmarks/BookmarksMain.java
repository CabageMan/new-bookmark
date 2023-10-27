package com.ironhead.bookmarks;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "bookmarksList", value = "/bookmarks-list")
public class BookmarksMain extends HttpServlet {
  private static final String BUTTON_PARAMETER = "button";
  private static final String ADD_BOOKMARK_BUTTON = "addBookmark";
  private static final String SEARCH_BOOKMARK_BUTTON = "searchBookmarks";
  private static final String BOOKMARK_TITLE_FIELD = "bookmarkTitle";
  private static final String BOOKMARK_DESCRIPTION_FIELD = "bookmarkDescription";
  private static final String SEARCH_BOOKMARK_FIELD = "searchByTitle";

  private String message;

  public void init() {
    message = "Bookmark info:";
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
//
//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");
    System.out.println("Do Get");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    String buttonValue = request.getParameter(BUTTON_PARAMETER);

    if (buttonValue.equals(ADD_BOOKMARK_BUTTON)) {
      String newBookmarkTitle = request.getParameter(BOOKMARK_TITLE_FIELD);
      String newBookmarkDescription = request.getParameter(BOOKMARK_DESCRIPTION_FIELD);
      System.out.println("Add Bookmark " + "\"" + newBookmarkTitle + "\"" + ":\n" + newBookmarkDescription) ;
    } else if (buttonValue.equals(SEARCH_BOOKMARK_BUTTON)) {
      String bookmarkTitle = request.getParameter(SEARCH_BOOKMARK_FIELD);
      System.out.println("Need to find " + "\"" + bookmarkTitle + "\"" + " bookmark.");
    } else {
      System.out.println("Something went wrong");
    }

//        request.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);

//        if (request.getParameter("saveBookmark") != null ||
//                request.getParameter("title") != null ||
//                request.getParameter("content") != null) {
//
//            try {
//                //Send to database new bookmark and return changed list of bookmarks
//                BookMark bookmark = new BookMark(0, request.getParameter("title"), request.getParameter("content"));
//                BookMarksModel.addBookmark(bookmark);
//                request.setAttribute("list", getBookmarks(""));
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

//            request.getRequestDispatcher("/").forward(request, response);
//
//        } else if (request.getParameter("filterBookmarks") != null ||
//                    request.getParameter("filter") != null) {
//            // Get filtered list of bookmarks by substring
//            request.setAttribute("list", getBookmarks(request.getParameter("filter")));
//
//            request.getRequestDispatcher("/").forward(request, response);
//
//        } else if (request.getParameter("deleteBookmark") != null) {
//
//            if (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty()) {
//                try {
//                    //Call delete method with ID of deleting bookmark
//                    BookMarksModel.deleteBookmark(Integer.parseInt(request.getParameter("delete")));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }

    //Return changed bookmarks list
//            request.setAttribute("list", getBookmarks(""));

    request.getRequestDispatcher("/").forward(request, response);
  }

  public void destroy() {
    System.out.println("Destroy is called");
  }
}