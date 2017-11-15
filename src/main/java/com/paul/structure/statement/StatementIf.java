package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.expression.Expression;
import java.util.ArrayList;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.IF_ICMPGT;
import org.apache.bcel.generic.IF_ICMPLE;
import org.apache.bcel.generic.IF_ICMPLT;
import org.apache.bcel.generic.IF_ICMPNE;

public class StatementIf extends Statement {

  public static ArrayList<StatementIf> ifStatements = new ArrayList<>();

  private String operator;
  private Expression left, right;
  private StatementGoto statement;

  private int jump;
  private BranchHandle ifHandle;

  public StatementIf(Compiler compiler,
      String operator,
      Expression left,
      Expression right,
      StatementGoto statement) {
    super(compiler);
    this.operator = operator;
    this.left = left;
    this.right = right;
    this.statement = statement;

    ifStatements.add(this);
  }

  @Override
  public void execute() {
    left.load();
    right.load();

    switch (operator) {
      case "=":
        ifHandle = compiler.instructionList.append(new IF_ICMPEQ(null));
        break;
      case "><":
        ifHandle = compiler.instructionList.append(new IF_ICMPNE(null));
        break;
      case "<":
        ifHandle = compiler.instructionList.append(new IF_ICMPLT(null));
        break;
      case ">":
        ifHandle = compiler.instructionList.append(new IF_ICMPGT(null));
        break;
      case ">=":
        ifHandle = compiler.instructionList.append(new IF_ICMPLE(null));
        break;
      case "<=":
        ifHandle = compiler.instructionList.append(new IF_ICMPGE(null));
        break;
    }

    jump = statement.getLineNumber();
    StatementGoto.gotoStatements.remove(statement);
  }

  public int getLineNumber() {
    return jump;
  }

  public BranchHandle getIfHandle() {
    return ifHandle;
  }
}
