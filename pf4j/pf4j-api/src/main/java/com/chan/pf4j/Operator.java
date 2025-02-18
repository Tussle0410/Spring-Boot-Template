package com.chan.pf4j;

import org.pf4j.ExtensionPoint;

public interface Operator extends ExtensionPoint {

    String getOperatorName();
    int plus(int a, int b);
    int minus(int a, int b);
    int multiply(int a, int b);
    int divide(int a, int b);
    int remainder(int a, int b);

}