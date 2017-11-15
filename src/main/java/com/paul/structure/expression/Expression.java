package com.paul.structure.expression;

import com.paul.core.Compiler;

public abstract class Expression {

  protected Compiler compiler;

  public Expression(Compiler compiler) {
    this.compiler = compiler;
  }

  public abstract void load();
}
