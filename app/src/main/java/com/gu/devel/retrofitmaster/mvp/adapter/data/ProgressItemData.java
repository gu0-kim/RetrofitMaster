package com.gu.devel.retrofitmaster.mvp.adapter.data;

/** Created by devel on 2018/4/27. */
public class ProgressItemData {
  private String filePath;
  protected String fileName;
  private boolean checked;
  protected String size; // 1.2 MB
  //
  private int progress;
  private boolean doing;
  private boolean done;
  private String pbText;

  public ProgressItemData() {
    this("", "", "", false);
  }

  public ProgressItemData(String filePath, String fileName, String size, boolean checked) {
    this.filePath = filePath;
    this.fileName = fileName;
    this.checked = checked;
    this.size = size;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public boolean isDoing() {
    return doing;
  }

  public void setDoing(boolean doing) {
    this.doing = doing;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public String getPbText() {
    return pbText;
  }

  public void setPbText(String pbText) {
    this.pbText = pbText;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }
}
