package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.expression.Expression;
import com.paul.structure.expression.ExpressionArithmetic;
import com.paul.structure.expression.ExpressionInteger;
import com.paul.structure.expression.ExpressionVariable;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.LDC;

public class StatementPrint extends Statement {

  public StatementPrint(Compiler compiler) {
    super(compiler);
  }

  @Override
  public void execute() {
    for (Expression expression : expressions) {
      compiler.addInstruction(new GETSTATIC(
          compiler.constantPoolGen.addFieldref(
              "java.lang.System", "out", "Ljava/io/PrintStream;")));

      expression.load();

      if (expression instanceof ExpressionVariable
          || expression instanceof ExpressionInteger
          || expression instanceof ExpressionArithmetic) {
        compiler.addInstruction(new INVOKEVIRTUAL(
            compiler.constantPoolGen.addMethodref(
                "java.io.PrintStream", "print", "(I)V")));
      } else {
        compiler.addInstruction(new INVOKEVIRTUAL(
            compiler.constantPoolGen.addMethodref(
                "java.io.PrintStream", "print", "(Ljava/lang/String;)V")));
      }
    }

    compiler.addInstruction(new GETSTATIC(
        compiler.constantPoolGen.addFieldref(
            "java.lang.System", "out", "Ljava/io/PrintStream;")));

    compiler.addInstruction(new LDC(
        compiler.constantPoolGen.addString("")));

    compiler.addInstruction(new INVOKEVIRTUAL(
        compiler.constantPoolGen.addMethodref(
            "java.io.PrintStream", "println", "(Ljava/lang/String;)V")));
  }
}
