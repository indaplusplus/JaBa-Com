package com.paul.structure.expression;

import com.paul.core.Compiler;
import org.apache.bcel.generic.ILOAD;

public class ExpressionVariable extends Expression {

  private String variableName;

  public ExpressionVariable(Compiler compiler, String variableName) {
    super(compiler);

    this.variableName = variableName;
  }

  public String getVariableName() {
    return variableName;
  }

  @Override
  public void load() {
    compiler.addInstruction(new ILOAD(
        compiler.variables.get(variableName)));
  }
}
