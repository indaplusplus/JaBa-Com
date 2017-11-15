package com.paul.util;

public class LineClean {

  public String clean(String line) {
    while (line.endsWith(" ")) {
      line = line.substring(0, line.length() - 1);
    }

    while (line.startsWith(" ")) {
      line = line.substring(1, line.length());
    }

    return line;
  }
}
