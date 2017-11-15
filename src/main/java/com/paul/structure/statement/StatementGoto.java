package com.paul.structure.statement;

import com.paul.core.Compiler;
import java.util.ArrayList;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.GOTO;

public class StatementGoto extends Statement {

  public static ArrayList<StatementGoto> gotoStatements = new ArrayList<>();

  private int jump;
  private BranchHandle gotoHandle;

  public StatementGoto(Compiler compiler, int jump) {
    super(compiler);
    this.jump = jump;

    gotoStatements.add(this);
  }

  @Override
  public void execute() {
    gotoHandle = compiler.instructionList.append(new GOTO(null));
  }

  public int getLineNumber() {
    return jump;
  }

  public BranchHandle getGotoHandle() {
    return gotoHandle;
  }
}
