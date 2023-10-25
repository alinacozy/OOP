package functions.meta;

import functions.Function;

public class Shift implements Function {
    Function function;
    double xShift;
    double yShift;
    public Shift(Function func, double x, double y){
        function=func;
        xShift =x;
        yShift =y;
    }

    public double getLeftDomainBorder(){
        return function.getLeftDomainBorder()+ xShift;
    }

    public double getRightDomainBorder(){
        return function.getRightDomainBorder()+ xShift;
    }

    public double getFunctionValue(double x){
        return function.getFunctionValue(x)+ yShift;
    }
}
