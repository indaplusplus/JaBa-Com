package com.paul.structure;

import com.paul.util.LineClean;
import java.util.ArrayList;

public class Code {

  private ArrayList<Line> code = new ArrayList<>();
  private LineClean lineClean = new LineClean();

  public void insertLine(String lineText) {
    if (lineText.contains(" ")) {
      String[] split = lineText.split(" ", 2);

      try {
        int lineNumber = Integer.valueOf(split[0]);

        if (code.isEmpty()) {
          code.add(new Line(
                  lineClean.clean(
                      lineText)));
        } else {
          for (int i = 0; i < code.size(); i++) {
            if (code.get(i).getLineNumber() > lineNumber) {
              code.add(i,
                  new Line(
                      lineClean.clean(
                          lineText)));
              break;
            } else if (i == code.size() - 1) {
              code.add(
                  new Line(
                      lineClean.clean(
                          lineText)));
              break;
            }
          }
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
  }

  public void removeLine(int lineNumber) {
    for (int i = 0; i < code.size(); i++) {
      if (code.get(i).getLineNumber() == lineNumber) {
        code.remove(i);
        break;
      }
    }
  }

  public Line getLineFromIndex(int index) {
    return code.get(index);
  }

  public Line getLine(int lineNumber) {
    for (Line line : code) {
      if (line.getLineNumber() == lineNumber) {
        return line;
      }
    }

    return null;
  }

  public Line getNextLine(int currentLineNumber) {
    for (int i = 0; i < code.size() - 1; i++) {
      if (code.get(i).getLineNumber() == currentLineNumber) {
        return code.get(i + 1);
      }
    }

    return null;
  }

  public int getLineAmount() {
    return code.size();
  }
}
