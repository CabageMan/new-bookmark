package com.ironhead.bookmarks.datasource;

import java.sql.*;

public class DataSource {
  private static final String DB_NAME = "bookmarks.db";
  private static final String CONNECTION_STRING = "jdbc:sqlite:/opt/tomcat/webapps/dataBases/" + DB_NAME;
  private static final String BOOKMARKS_TABLE_NAME = "bookmarks";
  private static final String COLUMN_TITLE = "title";
  private static final String COLUMN_DESCRIPTION = "description";

  private Connection connection;


  // METHODS
  public boolean open() {
    try {
      connection = DriverManager.getConnection(CONNECTION_STRING);
      Statement statement = connection.createStatement();
      statement.execute("DROP TABLE IF EXISTS " + BOOKMARKS_TABLE_NAME);
      statement.execute("CREATE TABLE IF NOT EXISTS " + BOOKMARKS_TABLE_NAME +
              " (" + COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT " + ")"
      );
      return true;
    } catch (SQLException e) {
      System.out.println("Couldn't connect to database: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public void close() {
    try {
      // It's important to close prepare query before closing connection.
      // When prepared query is closed the results sets also closed
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      System.out.println("Couldn't close connection: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
