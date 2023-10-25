package functions.meta;

import functions.Function;

public class Scale implements Function {
    Function function;
    double xCoefficient;
    double yCoefficient;
    public Scale(Function func, double x, double y){
        function=func;
        xCoefficient =x;
        yCoefficient =y;
    }

    public double getLeftDomainBorder(){
        return function.getLeftDomainBorder()* xCoefficient;
    }

    public double getRightDomainBorder(){
        return function.getRightDomainBorder()* xCoefficient;
    }

    public double getFunctionValue(double x){
        return function.getFunctionValue(x)* yCoefficient;
    }
}
