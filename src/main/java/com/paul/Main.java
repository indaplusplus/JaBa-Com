package com.paul;

import com.paul.core.Compiler;
import com.paul.structure.Code;

public class Main {

  public static void main(String[] args) {
    Code code = new Code();

    code.insertLine("05 LET LOOP = 0");
    code.insertLine("10 LET A = 0");
    code.insertLine("20 LET B = 1");
    code.insertLine("30 LET A = A + B");
    code.insertLine("40 LET B = B + 1");
    code.insertLine("50 IF B < 1001 THEN GOTO 30");
    code.insertLine("60 PRINT LOOP, \" \", A");
    code.insertLine("65 LET LOOP = LOOP + 1");
    code.insertLine("67 IF LOOP < 1000 THEN GOTO 10");
    code.insertLine("70 END");

    Compiler compiler = new Compiler(code);

    compiler.run();
  }
}
