package functions;

import functions.meta.*;

public class Functions {

    private Functions(){} //приватный конструктор, чтобы вне этого класса нельзя было создать объект

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power){
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    public static double integral(Function f, double leftX, double rightX, double step) throws IllegalArgumentException{
        if (leftX<f.getLeftDomainBorder() || leftX>f.getRightDomainBorder() || rightX <f.getLeftDomainBorder() || rightX>f.getRightDomainBorder()){
            throw new IllegalArgumentException("The integration interval goes beyond the boundaries of the function definition domain");
        }
        double result=0;
        //значения точек в вершинах трапеции:
        double leftY=f.getFunctionValue(leftX);
        double rightY=f.getFunctionValue(leftX+step);
        while(leftX<rightX){
            result+=step*(leftY+rightY)/2;
            leftX+=step;
            leftY=rightY;
            rightY=f.getFunctionValue(leftX+step);
        }
        rightY=f.getFunctionValue(rightX);
        result+=(rightX-leftX)*(leftY+rightY)/2;
        return result;
    }
}
