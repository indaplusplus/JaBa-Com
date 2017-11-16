package com.paul.core;

import com.paul.structure.Line;
import com.paul.structure.Token;
import com.paul.structure.Token.Type;
import java.util.ArrayList;

public class Tokenizer {

  private ArrayList<String> whitespace = new ArrayList<>();
  private ArrayList<String> keywords = new ArrayList<>();
  private ArrayList<String> digits = new ArrayList<>();
  private ArrayList<String> characters = new ArrayList<>();
  private ArrayList<String> operators = new ArrayList<>();
  private ArrayList<String> relationalOperators = new ArrayList<>();

  public Tokenizer() {
    whitespace.add(" ");
    whitespace.add("	");

    keywords.add("print");
    keywords.add("if");
    keywords.add("then");
    keywords.add("goto");
    keywords.add("input");
    keywords.add("let");
    keywords.add("gosub");
    keywords.add("return");
    keywords.add("end");

    String numbers = "0123456789";
    for (char digit : numbers.toCharArray()) {
      digits.add(String.valueOf(digit));
    }

    String variables = "abcdefghijklmnopqrstuvwxyz";
    for (String variable : variables.split("")) {
      characters.add(variable);
    }

    operators.add("*");
    operators.add("+");
    operators.add("-");
    operators.add("/");

    relationalOperators.add(">");
    relationalOperators.add("<");
    relationalOperators.add("=");
  }

  public ArrayList<Token> getTokens(Line line) {
    ArrayList<Token> tokens = new ArrayList<>();

    CharacterBuffer buffer = new CharacterBuffer(line);

    while (buffer.hasNext()) {
      tokens.add(nextToken(buffer));
    }

    return tokens;
  }

  public Token nextToken(CharacterBuffer buffer) {
    String token = buffer.getNext();

    while (whitespace.contains(token) && buffer.hasNext()) {
      token = buffer.getNext();
    }

    Type type = getType(token);

    switch(type) {
      case SPLITTER:
        return new Token(Token.Type.SPLITTER, token);

      case CHARACTER:
        if (!buffer.hasNext() || (buffer.hasNext() && !characters.contains(buffer.peek(0).toLowerCase()))) {
          return new Token(Token.Type.VARIABLE, token);
        } else {
          return readKeyword(token, buffer);
        }

      case DIGIT:
        return readNumber(token, buffer);

      case OPERATOR:
        return new Token(Token.Type.OPERATOR, token);

      case RELOPERATOR:
        return readRelationalOperator(token, buffer);

      case QUOTE:
        return readString(buffer);

      case LEFT_PARENTHESIS:
        return new Token(Token.Type.LEFT_PARENTHESIS, "(");

      case RIGHT_PARENTHESIS:
        return new Token(Token.Type.RIGHT_PARENTHESIS, ")");
    }

    return null;
  }

  public Token readKeyword(String token, CharacterBuffer buffer) {
    while (buffer.hasNext() && characters.contains(buffer.peek(0).toLowerCase())) {
      token += buffer.getNext();
    }

    if (keywords.contains(token.toLowerCase())) {
      return new Token(Token.Type.KEYWORD, token.toLowerCase());
    }

    return new Token(Token.Type.VARIABLE, token);
  }

  public Token readNumber(String token, CharacterBuffer buffer) {
    while (buffer.hasNext() && digits.contains(buffer.peek(0))) {
      token += buffer.getNext();
    }

    return new Token(Token.Type.NUMBER, token);
  }

  public Token readString(CharacterBuffer buffer) {
    String token = "";

    while (buffer.hasNext() && !(buffer.peek(0).equals("\"") && !(buffer.peek(-1).equals("\\")))) {
      token += buffer.getNext();
    }

    buffer.getNext();

    return new Token(Token.Type.STRING, token);
  }

  public Token readRelationalOperator(String token, CharacterBuffer buffer) {
    if (!token.equals("=") && buffer.hasNext() && relationalOperators.contains(buffer.peek(0))) {
      return new Token(Token.Type.RELOPERATOR, token+buffer.getNext());
    } else {
      return new Token(Token.Type.RELOPERATOR, token);
    }
  }

  private enum Type {
    CHARACTER,
    DIGIT,
    OPERATOR,
    RELOPERATOR,
    QUOTE,
    SPLITTER,
    RIGHT_PARENTHESIS,
    LEFT_PARENTHESIS;
  }

  private Type getType(String token) {
    if (characters.contains(token.toLowerCase())) {
      return Type.CHARACTER;
    } else if (digits.contains(token)) {
      return Type.DIGIT;
    } else if (operators.contains(token)) {
      return Type.OPERATOR;
    } else if (relationalOperators.contains(token)) {
      return Type.RELOPERATOR;
    } else if (token.equals(",")) {
      return Type.SPLITTER;
    } else if (token.equals("\"")) {
      return Type.QUOTE;
    } else if (token.equals("(")) {
      return Type.LEFT_PARENTHESIS;
    } else if (token.equals(")")) {
      return Type.RIGHT_PARENTHESIS;
    }

    return null;
  }

  public class CharacterBuffer {

    private String line;
    private int offset;

    public CharacterBuffer(Line line) {
      this.line = line.getLineText();
    }

    public String getNext() {
      return line.substring(offset, ++offset);
    }

    public boolean hasNext() {
      return offset < line.length();
    }

    public String peek(int offset) {
      return line.substring(this.offset + offset, this.offset + offset + 1);
    }
  }
}
