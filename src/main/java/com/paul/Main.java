package com.paul;

import com.paul.core.Compiler;
import com.paul.structure.Code;

public class Main {

  public static void main(String[] args) {
    Code code = new Code();

    code.insertLine("01 GOSUB 02");
    code.insertLine("02 INPUT A");
    code.insertLine("08 LET A = A * A");
    code.insertLine("10 PRINT A");
    code.insertLine("15 RETURN");
    code.insertLine("20 END");

//    code.insertLine("05 LET A = 5");
//    code.insertLine("10 PRINT \"Hello, World!\"");
//    code.insertLine("15 LET A = A * 5");
//    code.insertLine("35 PRINT A");
//    code.insertLine("50 END");

    Compiler compiler = new Compiler(code);

    compiler.run();
  }
}
