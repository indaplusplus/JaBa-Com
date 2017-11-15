package com.paul.core;

import com.paul.structure.Code;
import com.paul.structure.Line;
import com.paul.structure.statement.Statement;
import com.paul.structure.statement.StatementGosub;
import com.paul.structure.statement.StatementGoto;
import com.paul.structure.statement.StatementIf;
import com.paul.structure.statement.StatementReturn;
import com.sun.org.apache.bcel.internal.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;

public class Compiler {

  public InstructionList instructionList;
  public InstructionFactory instructionFactory;
  public ConstantPoolGen constantPoolGen;
  public MethodGen methodGen;

  public final HashMap<String, Integer> variables = new HashMap<>(); //variable name, variable index
  public final HashMap<Line, InstructionHandle> lineStart = new HashMap<>();

  private Code code;
  private Line line;

  public Compiler(Code code) {
    this.code = code;
  }

  public void run() {
    ClassGen classGen = new ClassGen(
        "com.paul.SyntheticClass", "java.lang.Object",
        "SyntheticClass.java", Constants.ACC_PUBLIC, null);

    constantPoolGen = classGen.getConstantPool();
    instructionList = new InstructionList();
    instructionFactory = new InstructionFactory(classGen, constantPoolGen);

    methodGen = new MethodGen(Constants.ACC_PUBLIC|Constants.ACC_STATIC, Type.VOID,
        new Type[] {new ArrayType(Type.STRING, 1)},
        new String[]{"args"}, "main", "com.paul.SyntheticClass",
        instructionList, constantPoolGen);

    Tokenizer tokenizer = new Tokenizer();
    line = code.getLineFromIndex(0);

    while (line != null) {
      Parser parser = new Parser(this, line);
      Statement statement = parser.parse();
      statement.execute();

      line = code.getNextLine(line.getLineNumber());
    }

    for (StatementGoto sg : StatementGoto.gotoStatements) {
      sg.getGotoHandle().setTarget(
          lineStart.get(
              code.getLine(
                  sg.getLineNumber()
              )
          )
      );
    }

    for (StatementIf si : StatementIf.ifStatements) {
      si.getIfHandle().setTarget(
          lineStart.get(
              code.getLine(
                  si.getLineNumber()
              )
          )
      );
    }

    for (StatementGosub sg : StatementGosub.gosubStatements) {
      sg.getGosubHandle().setTarget(
          lineStart.get(
              code.getLine(
                  sg.getLineNumberTo()
              )
          )
      );
    }

    for (StatementReturn sr : StatementReturn.returnStatements) {
      sr.getReturnHandle().setTarget(
          lineStart.get(
              code.getNextLine(
                StatementGosub.gosubStatements.get(0).getLineFrom().getLineNumber()
              )
          )
      );
    }

    methodGen.setMaxLocals();
    methodGen.setMaxStack();

    classGen.addMethod(methodGen.getMethod());

    JavaClass javaClass=classGen.getJavaClass();
    try {
      javaClass.dump("com/paul/SyntheticClass.class");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addInstruction(Instruction instruction) {
    InstructionHandle instructionHandle = this.instructionList.append(instruction);

    if (!this.lineStart.containsKey(line)) {
      lineStart.put(line, instructionHandle);
    }
  }

  public Code getCode() {
    return code;
  }
}
