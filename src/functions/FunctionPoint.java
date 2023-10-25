package functions;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class FunctionPoint implements java.io.Externalizable{
    private double x;
    private double y;

    public FunctionPoint(double newX, double newY){
        x=newX;
        y=newY;
    }

    public FunctionPoint(FunctionPoint point){
        x=point.x;
        y=point.y;
    }

    public FunctionPoint(){
        x=y=0;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double newX){
        x=newX;
    }
    public void setY(double newY){
        y=newY;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(x);
        out.writeObject(y);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = (double) in.readObject();
        y = (double) in.readObject();
    }
}
