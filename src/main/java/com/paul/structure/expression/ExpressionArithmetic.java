package com.paul.structure.expression;

import com.paul.core.Compiler;
import org.apache.bcel.generic.IADD;
import org.apache.bcel.generic.IDIV;
import org.apache.bcel.generic.IMUL;
import org.apache.bcel.generic.ISUB;

public class ExpressionArithmetic extends Expression {

  private String operator;
  private Expression left, right;

  public ExpressionArithmetic(Compiler compiler, String operator, Expression left, Expression right) {
    super(compiler);
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  @Override
  public void load() {
    left.load();
    right.load();

    switch(operator) {
      case "+":
        compiler.addInstruction(new IADD());
        break;
      case "-":
        compiler.addInstruction(new ISUB());
        break;
      case "*":
        compiler.addInstruction(new IMUL());
        break;
      case "/":
        compiler.addInstruction(new IDIV());
        break;
    }
  }
}
