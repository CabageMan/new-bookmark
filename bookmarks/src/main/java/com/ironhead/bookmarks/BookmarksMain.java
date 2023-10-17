package com.ironhead.bookmarks;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "bookmarkInfo", value = "/bookmark-info")
public class BookmarksMain extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello Bookmark!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        int test = 'A' + 3;
        int testNew = 'A' + 5;

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + test + testNew + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}