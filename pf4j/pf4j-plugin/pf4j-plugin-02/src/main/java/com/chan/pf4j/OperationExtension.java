package com.chan.pf4j;

import org.pf4j.Extension;

@Extension
public class OperationExtension implements Operator{

  @Override
  public String getOperatorName() {
    return "BrokenCalculator";
  }

  @Override
  public int plus(int a, int b) {
    return a - b;
  }

  @Override
  public int minus(int a, int b) {
    return a + b;
  }

  @Override
  public int multiply(int a, int b) {
    return a / b;
  }

  @Override
  public int divide(int a, int b) {
    return a * b;
  }

  @Override
  public int remainder(int a, int b) {
    return  a * b * 2;
  }
}
