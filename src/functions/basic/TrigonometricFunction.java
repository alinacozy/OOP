package functions.basic;

import functions.Function;

abstract class TrigonometricFunction implements Function {
    public double getLeftDomainBorder(){
        return Double.NEGATIVE_INFINITY;
    }

    public double getRightDomainBorder(){
        return Double.POSITIVE_INFINITY;
    }
    public abstract double getFunctionValue(double x);
}
