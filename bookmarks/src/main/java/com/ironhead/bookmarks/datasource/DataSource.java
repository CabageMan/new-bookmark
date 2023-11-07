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

  public static final String QUERY_BOOKMARKS_BY_ID = "SELECT " +
          COLUMN_ID + ", * FROM " + TABLE_BOOKMARKS + " WHERE " + COLUMN_ID + " = ?";
  public static final String INSERT_BOOKMARK = "INSERT INTO " + TABLE_BOOKMARKS +
          " (" + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + ") VALUES(?, ?)";
  public static final String UPDATE_BOOKMARK = "UPDATE " + TABLE_BOOKMARKS + " SET " +
          COLUMN_TITLE + " = ?" + ", " + COLUMN_DESCRIPTION + " = ?" +
          " WHERE " + COLUMN_ID + " = ?";
  public static final String DELETE_BOOKMARK = "DELETE FROM " + TABLE_BOOKMARKS +
          " WHERE " + COLUMN_ID + " = ?";
  private Connection connection;
  private PreparedStatement queryBookmarksByTitle;
  private PreparedStatement queryBookmarkById;
  private PreparedStatement insertIntoBookmarks;
  private PreparedStatement updateBookmarkById;
  private PreparedStatement deleteBookmarkById;

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

  public void removeInstance() {
    close();
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
      queryBookmarkById = connection.prepareStatement(QUERY_BOOKMARKS_BY_ID);
      updateBookmarkById = connection.prepareStatement(UPDATE_BOOKMARK);
      deleteBookmarkById = connection.prepareStatement(DELETE_BOOKMARK);

      System.out.println("Open Connection");
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
      if (queryBookmarkById != null) {
        queryBookmarkById.close();
      }
      if (updateBookmarkById != null) {
        updateBookmarkById.close();
      }
      if (deleteBookmarkById != null) {
        deleteBookmarkById.close();
      }
      if (connection != null) {
        connection.close();
      }
      System.out.println("Close Connection");
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
                results.getLong(1),
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
                results.getLong(1),
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

  public Bookmark queryBookmarkByID(long bookmarkId) {
    try {
      queryBookmarkById.setLong(1, bookmarkId);
      ResultSet results = queryBookmarkById.executeQuery();
      if (results.next()) {
        return new Bookmark(
                results.getLong(1),
                results.getString(2),
                results.getString(3)
        );
      }
      return null;
    } catch (SQLException e) {
      System.out.println("Couldn't get Bookmark by ID: " + e);
      return null;
    }
  }

  public boolean updateBookmark(Bookmark bookmark) {
    try {
      updateBookmark(bookmark.getId(), bookmark.getTitle(), bookmark.getInfo());
      return true;
    } catch (SQLException e) {
      System.out.println("Couldn't update bookmark: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteBookmark(Bookmark bookmark) {
    try {
      deleteBookmark(bookmark.getId());
      return true;
    } catch (SQLException e) {
      System.out.println("Couldn't delete bookmark: " + e.getMessage());
      return false;
    }
  }

  private void insertBookmark(String title, String description) throws SQLException {
    insertIntoBookmarks.setString(1, title);
    insertIntoBookmarks.setString(2, description);
    int affectedRows = insertIntoBookmarks.executeUpdate();
    if (affectedRows != 1) { // Only one bookmark should be added
      throw new SQLException("Couldn't insert bookmark");
    }
  }

  private void updateBookmark(long id, String title, String info) throws SQLException {
    System.out.println("Update by ID: " + id + "\nTitle: " + title + "\nInfo: " + info);
    updateBookmarkById.setString(1, title);
    updateBookmarkById.setString(2, info);
    updateBookmarkById.setLong(3, id);
    int affectedRows = updateBookmarkById.executeUpdate();
    if (affectedRows != 1) { // Only one bookmark should be updated.
      throw new SQLException("Couldn't update bookmark");
    }
  }

  private void deleteBookmark(long bookmarkId) throws SQLException {
    deleteBookmarkById.setLong(1, bookmarkId);
    int affectedRows = deleteBookmarkById.executeUpdate();
    if (affectedRows != 1) { // Only one bookmark should be deleted.
      throw new SQLException("Couldn't delete bookmark");
    }
  }
}
