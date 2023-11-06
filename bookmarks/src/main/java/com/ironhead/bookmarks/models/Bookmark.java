package com.ironhead.bookmarks.models;

public class Bookmark {
  private long id;
  private String title;
  private String info;

  public Bookmark(long id, String title, String info) {
    this.id = id;
    this.title = title;
    this.info = info;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getInfo() {
    return info;
  }
}
