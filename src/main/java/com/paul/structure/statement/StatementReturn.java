package com.paul.structure.statement;

import com.paul.core.Compiler;
import java.util.ArrayList;
import org.apache.bcel.generic.BranchHandle;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IF_ICMPEQ;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.Type;

public class StatementReturn extends Statement {

  public static ArrayList<StatementReturn> returnStatements = new ArrayList<>();

  private BranchHandle returnHandle;

  public StatementReturn(Compiler compiler) {
    super(compiler);

    returnStatements.add(this);
  }

  @Override
  public void execute() {
    //load gosub
    compiler.addInstruction(new ILOAD(compiler.variables.get("gosub")));

    //save into gosub_temp
    LocalVariableGen localVariableGen
        = compiler.methodGen.addLocalVariable("gosub_temp",
        Type.INT, null, null);
    localVariableGen.setStart(compiler.instructionList.append(new ISTORE(localVariableGen.getIndex())));

    compiler.variables.put("gosub_temp", localVariableGen.getIndex());

    //set gosub to false
    compiler.addInstruction(new LDC(compiler.constantPoolGen.addInteger(0)));
    compiler.addInstruction(new ISTORE(compiler.variables.get("gosub")));

    //if statement on gosub_temp
    compiler.addInstruction(new LDC(compiler.constantPoolGen.addInteger(1)));
    compiler.addInstruction(new ILOAD(compiler.variables.get("gosub_temp")));
    returnHandle = compiler.instructionList.append(new IF_ICMPEQ(null));
  }

  public BranchHandle getReturnHandle() {
    return returnHandle;
  }
}
