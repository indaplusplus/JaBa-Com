package com.paul.structure.statement;

import com.paul.core.Compiler;
import org.apache.bcel.generic.RETURN;

public class StatementEnd extends Statement {

  public StatementEnd(Compiler compiler) {
    super(compiler);
  }

  @Override
  public void execute() {
    compiler.addInstruction(new RETURN());
  }
}
