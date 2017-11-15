package com.paul.structure;

public class Token {

  public enum Type {
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    KEYWORD,
    VARIABLE,
    NUMBER,
    STRING,
    SPLITTER,
    OPERATOR,
    RELOPERATOR;
  }

  private Type type;
  private String value;

  public Token(Type type, String value) {
    this.type = type;
    this.value = value;
  }

  public Type getType() {
    return type;
  }

  public String getValue() {
    return value;
  }
}
