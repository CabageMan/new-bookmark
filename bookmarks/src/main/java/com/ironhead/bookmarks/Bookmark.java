package com.ironhead.bookmarks;

public class Bookmark {
  private String title;
  private String info;

  public Bookmark(String title, String info) {
    this.title = title;
    this.info = info;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
