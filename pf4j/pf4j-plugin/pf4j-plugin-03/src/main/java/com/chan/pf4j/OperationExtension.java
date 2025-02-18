package com.chan.pf4j;

import org.pf4j.Extension;

@Extension
public class OperationExtension implements Operator{

  @Override
  public String getOperatorName() {
    return "CustomCalculator";
  }

  @Override
  public int plus(int a, int b) {
    return a + b + 1;
  }

  @Override
  public int minus(int a, int b) {
    return a - b + 1;
  }

  @Override
  public int multiply(int a, int b) {
    return a * b + 1;
  }

  @Override
  public int divide(int a, int b) {
    return a / b + 1;
  }

  @Override
  public int remainder(int a, int b) {
    return  (a % b) + 1;
  }
}
