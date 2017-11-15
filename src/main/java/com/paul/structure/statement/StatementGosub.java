package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.Line;
import com.paul.structure.expression.Expression;
import com.paul.structure.expression.ExpressionVariable;
import java.util.ArrayList;
import org.apache.bcel.generic.BASTORE;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.Type;

public class StatementGosub extends Statement {

  public static ArrayList<StatementGosub> gosubStatements = new ArrayList<>();

  private Line currentLine;
  private int jump;
  private BranchHandle gosubHandle;

  public StatementGosub(Compiler compiler, Line currentLine, int jump) {
    super(compiler);
    this.currentLine = currentLine;
    this.jump = jump;

    gosubStatements.add(this);
  }

  @Override
  public void execute() {
    compiler.addInstruction(new LDC(compiler.constantPoolGen.addInteger(1)));

    LocalVariableGen localVariableGen
        = compiler.methodGen.addLocalVariable("gosub",
        Type.INT, null, null);
    localVariableGen.setStart(compiler.instructionList.append(new ISTORE(localVariableGen.getIndex())));

    compiler.variables.put("gosub", localVariableGen.getIndex());

    gosubHandle = compiler.instructionList.append(new GOTO(null));
  }

  public Line getLineFrom() {
    return this.currentLine;
  }

  public int getLineNumberTo() {
    return jump;
  }

  public BranchHandle getGosubHandle() {
    return gosubHandle;
  }
}
