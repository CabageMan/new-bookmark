package com.ironhead.bookmarks.datasource;

import com.ironhead.bookmarks.models.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

  private static class SingletonHelper {
    private static final DataSource INSTANCE = new DataSource();
  }

  private static final String DB_NAME = "bookmarks.db";
  // May be it's possible to use localhost int the path to database
  private static final String CONNECTION_STRING = "jdbc:sqlite:/opt/tomcat/webapps/dataBases/" + DB_NAME;
  private static final String TABLE_BOOKMARKS = "bookmarks";
  private static final String COLUMN_ID = "rowid";
  private static final String COLUMN_TITLE = "title";
  private static final String COLUMN_DESCRIPTION = "description";
  public static final String QUERY_BOOKMARKS_BY_TITLE = "SELECT " +
          COLUMN_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + " FROM " +
          TABLE_BOOKMARKS + " WHERE " + COLUMN_TITLE + " = ?" + " ORDER BY " +
          TABLE_BOOKMARKS + "." + COLUMN_TITLE + " ASC";
  public static final String INSERT_BOOKMARK = "INSERT INTO " + TABLE_BOOKMARKS +
          " (" + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + ") VALUES(?, ?)";

  private Connection connection;
  private PreparedStatement queryBookmarksByTitle;
  private PreparedStatement insertIntoBookmarks;

  // Life Cycle
  private DataSource() {
  }

  public static DataSource getInstance() {
    // Check this place for open calling
    DataSource dataSourceInstance = SingletonHelper.INSTANCE;
    if (dataSourceInstance.connection == null) {
      dataSourceInstance.open();
    }
    return dataSourceInstance;
  }

  private boolean open() {
    try {
      this.connection = DriverManager.getConnection(CONNECTION_STRING);
      Statement statement = connection.createStatement();
//      statement.execute("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
      statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_BOOKMARKS +
              " (" + COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT " + ")");
      insertIntoBookmarks = connection.prepareStatement(INSERT_BOOKMARK);
      queryBookmarksByTitle = connection.prepareStatement(QUERY_BOOKMARKS_BY_TITLE);
      return true;
    } catch (SQLException e) {
      System.out.println("Couldn't connect to database: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  private void close() {
    try {
      if (insertIntoBookmarks != null) {
        insertIntoBookmarks.close();
      }
      if (queryBookmarksByTitle != null) {
        queryBookmarksByTitle.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      System.out.println("Couldn't close connection: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // METHODS
  public ArrayList<Bookmark> queryBookmarks() {
    try (Statement statement = connection.createStatement();
         ResultSet results = statement.executeQuery("SELECT " +
                 COLUMN_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION +
                 " FROM " + TABLE_BOOKMARKS)
    ) {
      ArrayList<Bookmark> bookmarks = new ArrayList<>();
      while (results.next()) {
        Bookmark artist = new Bookmark(
                results.getInt(1),
                results.getString(2),
                results.getString(3)
        );
        bookmarks.add(artist);
      }
      return bookmarks;
    } catch (SQLException e) {
      System.out.println("Query failed: " + e.getMessage());
      return null;
    }
  }

  public ArrayList<Bookmark> queryBookmarksByTitle(String title) {
    try {
      queryBookmarksByTitle.setString(1, title);
      ResultSet results = queryBookmarksByTitle.executeQuery();
      ArrayList<Bookmark> bookmarks = new ArrayList<>();
      while (results.next()) {
        Bookmark bookmark = new Bookmark(
                results.getInt(1),
                results.getString(2),
                results.getString(3)
        );
        bookmarks.add(bookmark);
      }
      return bookmarks;
    } catch (SQLException e) {
      System.out.println("Query by title failed: " + e.getMessage());
      return null;
    }
  }

  public boolean addBookmark(String title, String description) {
    try {
      insertBookmark(title, description);
    } catch (SQLException e) {
      System.out.println("Add Bookmark error: " + e.getMessage());
      return false;
    }
    return true;
  }

  private void insertBookmark(String title, String description) throws SQLException {
    insertIntoBookmarks.setString(1, title);
    insertIntoBookmarks.setString(2, description);
    int affectedRows = insertIntoBookmarks.executeUpdate();
    if (affectedRows != 1) { // Only one bookmark should be added
      throw new SQLException("Couldn't insert bookmark");
    }
  }
}
