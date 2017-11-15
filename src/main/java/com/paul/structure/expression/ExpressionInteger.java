package com.paul.structure.expression;

import com.paul.core.Compiler;
import org.apache.bcel.generic.LDC;

public class ExpressionInteger extends Expression {

  private int value;

  public ExpressionInteger(Compiler compiler, int value) {
    super(compiler);
    this.value = value;
  }

  @Override
  public void load() {
    compiler.addInstruction(new LDC(compiler.constantPoolGen.addInteger(value)));
  }
}
