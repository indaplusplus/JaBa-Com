package com.paul.core;

import com.paul.structure.Line;
import com.paul.structure.Token;
import com.paul.structure.expression.Expression;
import com.paul.structure.expression.ExpressionArithmetic;
import com.paul.structure.expression.ExpressionInteger;
import com.paul.structure.expression.ExpressionString;
import com.paul.structure.expression.ExpressionVariable;
import com.paul.structure.statement.Statement;
import com.paul.structure.statement.StatementEnd;
import com.paul.structure.statement.StatementGosub;
import com.paul.structure.statement.StatementGoto;
import com.paul.structure.statement.StatementIf;
import com.paul.structure.statement.StatementInput;
import com.paul.structure.statement.StatementLet;
import com.paul.structure.statement.StatementPrint;
import com.paul.structure.statement.StatementReturn;
import java.util.ArrayList;

public class Parser {

  private Compiler compiler;
  private Line line;
  private ArrayList<Token> tokens;
  private Token current;

  public Parser(Compiler compiler, Line line) {
    this.compiler = compiler;
    this.line = line;
    this.tokens = new Tokenizer().getTokens(line);
    this.current = tokens.get(0);
  }

  public Statement parse() {
    switch (current.getValue()) {
      case "end":
        return new StatementEnd(compiler);

      case "print":
        consume();
        StatementPrint statement = new StatementPrint(compiler);

        while (current != null &&
            (current.getType() == Token.Type.SPLITTER
            || current.getType() == Token.Type.VARIABLE
            || current.getType() == Token.Type.NUMBER
            || current.getType() == Token.Type.OPERATOR
            || current.getType() == Token.Type.STRING)) {
          if (current.getType() == Token.Type.STRING) {
            statement.expressions.add(new ExpressionString(compiler, current.getValue()));
            consume();
          } else if (current.getType() != Token.Type.SPLITTER) {
            statement.expressions.add(parseExpression());
          } else {
            consume();
          }
        }

        return statement;
      case "goto":
        consume();
        return new StatementGoto(compiler, Integer.valueOf(current.getValue()));
      case "gosub":
        consume();
        return new StatementGosub(compiler, line, Integer.valueOf(current.getValue()));
      case "let":
        consume();

        Expression variable = new ExpressionVariable(compiler, current.getValue());
        consume();

        if (current.getValue().equals("=")) {
          consume();

          return new StatementLet(compiler, variable, parseExpression());
        }
      case "return":
        return new StatementReturn(compiler);
      case "if":
        consume();

        Expression left = parseExpression();

        String operator = current.getValue();
        consume();

        Expression right = parseExpression();

        consume();

        Statement then = parse();
        return new StatementIf(compiler, operator, left, right, (StatementGoto) then);
      case "input":
        consume();

        StatementInput input = new StatementInput(compiler);

        while (current != null &&
            (current.getType() == Token.Type.SPLITTER || current.getType() == Token.Type.VARIABLE)) {
          if (current.getType() == Token.Type.VARIABLE) {
            input.variables.add(new ExpressionVariable(compiler, current.getValue()));
          }
          consume();
        }

        return input;
    }

    return null;
  }

  public Expression parseExpression() {
    if (current.getType() == Token.Type.OPERATOR &&
        (current.getValue().equals("+") || current.getValue().equals("-"))) {
      consume();
    }

    Expression expr = parseTerm();

    while (current != null
        && current.getType() == Token.Type.OPERATOR
        && (current.getValue().equals("+") || current.getValue().equals("-"))) {
      String operator = current.getValue();
      consume();
      expr = new ExpressionArithmetic(compiler, operator, expr, parseTerm());
    }

    return expr;
  }

  public Expression parseTerm() {
    Expression expr = parseFactor();

    while (current != null
        && current.getType() == Token.Type.OPERATOR
        && (current.getValue().equals("*") || current.getValue().equals("/"))) {
      String operator = current.getValue();
      consume();
      expr = new ExpressionArithmetic(compiler, operator, expr, parseFactor());
    }

    return expr;
  }

  public Expression parseFactor() {
    switch(current.getType()) {
      case NUMBER:
        Expression expr = new ExpressionInteger(compiler, Integer.valueOf(current.getValue()));
        consume();
        return expr;
      case VARIABLE:
        expr = new ExpressionVariable(compiler, current.getValue());
        consume();
        return expr;
      case LEFT_PARENTHESIS:
        consume();
        expr = parseExpression();
        consume();
        return expr;
    }

    return null;
  }

  public void consume() {
    if (!tokens.isEmpty()) {
      tokens.remove(0);

      if (tokens.isEmpty()) {
        current = null;
      } else {
        current = tokens.get(0);
      }
    }
  }
}
