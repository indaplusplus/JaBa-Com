package com.paul.structure;

public class Line {

  private int lineNumber;
  private String lineText;

  public Line(String lineText) {
    String[] split = lineText.split(" ", 2);

    this.lineNumber = Integer.valueOf(split[0]);
    this.lineText = split[1];
  }

  public int getLineNumber() {
    return this.lineNumber;
  }

  public String getLineText() {
    return this.lineText;
  }

  public String getCompleteLineText() {
    return this.getLineNumber() + " " + this.getLineText();
  }
}
