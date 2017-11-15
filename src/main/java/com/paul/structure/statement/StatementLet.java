package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.expression.Expression;
import com.paul.structure.expression.ExpressionString;
import com.paul.structure.expression.ExpressionVariable;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.Type;

public class StatementLet extends Statement {

  private Expression variable, value;

  public StatementLet(Compiler compiler, Expression variable, Expression value) {
    super(compiler);
    this.variable = variable;
    this.value = value;
  }

  @Override
  public void execute() {
    value.load();

    LocalVariableGen localVariableGen
        = compiler.methodGen.addLocalVariable(((ExpressionVariable) variable).getVariableName(),
        Type.INT, null, null);
    localVariableGen.setStart(compiler.instructionList.append(new ISTORE(localVariableGen.getIndex())));

    compiler.variables.put(((ExpressionVariable) variable).getVariableName(), localVariableGen.getIndex());
  }
}
