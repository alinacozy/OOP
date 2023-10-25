package functions;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ArrayTabulatedFunction implements TabulatedFunction, java.io.Externalizable{

    private FunctionPoint[] points;
    private int pCount; //количество точек функции

    public ArrayTabulatedFunction(){}

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if (leftX>=rightX){
            throw new IllegalArgumentException("The left border of the area must be lower than the right");
        }
        if (pointsCount<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        pCount=pointsCount;
        points=new FunctionPoint[pointsCount];
        double interval=(rightX-leftX)/(pointsCount-1);
        double currentX=leftX;
        for (int i=0; currentX<=rightX; currentX+=interval, i+=1) {
            points[i] = new FunctionPoint(currentX, 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        pCount=values.length;
        if (leftX>=rightX){
            throw new IllegalArgumentException("The left border of the area must be lower than the right");
        }
        if (pCount<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        points=new FunctionPoint[pCount];
        double interval=(rightX-leftX)/(pCount-1);
        double currentX=leftX;
        for (int i=0; currentX<=rightX; currentX+=interval, i+=1) {
            points[i] = new FunctionPoint(currentX, values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] values) throws IllegalArgumentException {
        if (values.length<2){
            throw new IllegalArgumentException("The number of points must be more than two");
        }
        pCount=values.length;
        points=new FunctionPoint[pCount];
        for (int i=0; i<pCount; i++) { // пробегаемся по массиву и добавляем элементы по одному в список
            if (i!=0 && values[i-1].getX()>=values[i].getX()){
                throw new IllegalArgumentException("The points must be ordered by abscissa");
            }
            points[i]=values[i];
        }
    }

    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    public double getRightDomainBorder(){
        return points[pCount-1].getX();
    }

    public double getFunctionValue(double x){
        if (getLeftDomainBorder()<=x && x<=getRightDomainBorder()){
            //если точка лежит в области определения
            for(int i=1; i<pCount; i+=1){ //начиная со второй точки перебираем массив
                if (points[i].getX()>=x){ //если нашли точку, которая по x больше или равна нашего икса
                    if (points[i].getX()==x) return points[i].getY(); //если мы нашли точку с такой же абсциссой, то возвращаем ее значение
                    return ( (points[i].getY()-points[i-1].getY())*(x-points[i-1].getX())/(points[i].getX()-points[i-1].getX()) + points[i-1].getY() );
                }
            }
        }
        return Double.NaN;
    }

    public int getPointsCount(){
        return pCount;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return points[index];
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && points[index-1].getX()>=point.getX()) || (index!=pCount-1 && point.getX()>=points[index+1].getX())){
           throw new InappropriateFunctionPointException("The abscissa of the point lies outside the interval");
        }
        points[index]=point;
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return points[index].getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if ((index!=0 && points[index-1].getX()>=x) || (index!=pCount-1 && x>=points[index+1].getX())){
            throw new InappropriateFunctionPointException("The abscissa of the point lies outside the interval");
        }
        points[index].setX(x);
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return points[index].getY();
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        points[index].setY(y);
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index<0 || index>=pCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (pCount<3){
            throw new IllegalStateException("Unable to delete a point because there are too few of them");
        }
        System.arraycopy(points,index+1, points, index, pCount-index-1);
        points[pCount-1]=null;
        pCount-=1;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int index=0;
        for(; index<pCount && point.getX()>getPointX(index); index+=1); //по итогу этого цикла получаем индекс, куда мы должны вставить элемент
        if (getPointX(index)==point.getX()){
            throw new InappropriateFunctionPointException("A point with such an abscissa already exists");
        }
        if (pCount+1> points.length){ //если нам не хватает размера, сохдаем новый массив
            FunctionPoint[] newArray=new FunctionPoint[pCount+1];
            System.arraycopy(points, 0,newArray, 0, index);
            newArray[index]=point;
            System.arraycopy(points, index, newArray, index+1, pCount-index);
            points=newArray;
        }
        else{
            System.arraycopy(points,index, points, index+1, pCount-index);
            points[index]=point;
        }
        pCount+=1;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(points);
        out.writeObject(pCount);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        points=(FunctionPoint[]) in.readObject();
        pCount=(int) in.readObject();
    }
}
