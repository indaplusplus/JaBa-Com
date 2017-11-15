package com.paul.structure.statement;

import com.paul.core.Compiler;
import com.paul.structure.expression.ExpressionString;
import com.paul.structure.expression.ExpressionVariable;
import com.sun.org.apache.bcel.internal.Constants;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;

public class StatementInput extends Statement {

  public ArrayList<ExpressionVariable> variables = new ArrayList<>();

  public StatementInput(Compiler compiler) {
    super(compiler);
  }

  @Override
  public void execute() {
    compiler.addInstruction(compiler.instructionFactory.createNew("java.util.Scanner"));

    compiler.addInstruction(new DUP());

    compiler.addInstruction(
        compiler.instructionFactory.createGetStatic(
            "java.lang.System", "in", new ObjectType("java.io.InputStream")));

    compiler.addInstruction(
        compiler.instructionFactory.createInvoke(
            "java.util.Scanner", "<init>", Type.VOID,
            new Type[] { new ObjectType("java.io.InputStream") }, Constants.INVOKESPECIAL));

    compiler.addInstruction(
        compiler.instructionFactory.createInvoke(
            "java.util.Scanner", "nextInt", Type.INT, Type.NO_ARGS, Constants.INVOKEVIRTUAL));

    ExpressionVariable lastVariable = null;

    for (ExpressionVariable variable : variables) {
      if (lastVariable != null) {
        lastVariable.load();
      }

      LocalVariableGen localVariableGen
          = compiler.methodGen.addLocalVariable(variable.getVariableName(),
          Type.INT, null, null);
      localVariableGen.setStart(compiler.instructionList.append(new ISTORE(localVariableGen.getIndex())));

      compiler.variables.put(variable.getVariableName(), localVariableGen.getIndex());

      lastVariable = variable;
    }
  }
}
