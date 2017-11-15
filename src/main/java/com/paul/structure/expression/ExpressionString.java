package com.paul.structure.expression;

import com.paul.core.Compiler;
import org.apache.bcel.generic.LDC;

public class ExpressionString extends Expression {

  private String string;

  public ExpressionString(Compiler compiler, String string) {
    super(compiler);
    this.string = string;
  }

  @Override
  public void load() {
    compiler.addInstruction(new LDC(
        compiler.constantPoolGen.addString(string)));
  }
}
