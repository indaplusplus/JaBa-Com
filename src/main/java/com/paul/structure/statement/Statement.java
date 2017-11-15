package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.expression.Expression;
import java.util.ArrayList;

public abstract class Statement {

  public ArrayList<Expression> expressions = new ArrayList<>();
  protected Compiler compiler;

  public Statement(Compiler compiler) {
    this.compiler = compiler;
  }

  public abstract void execute();
}
