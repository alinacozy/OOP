package functions.meta;

import functions.Function;

public class Power implements Function {
    Function function;
    double power;

    public Power(Function func1, double pow){
        function=func1;
        power=pow;
    }

    public double getLeftDomainBorder(){
        return function.getLeftDomainBorder();
    }

    public double getRightDomainBorder(){
        return function.getRightDomainBorder();
    }

    public double getFunctionValue(double x){
        return Math.pow(function.getFunctionValue(x), power);
    }
}
