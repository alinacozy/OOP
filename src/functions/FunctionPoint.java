package functions;

public class FunctionPoint implements java.io.Serializable, Cloneable{
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

    public String toString(){
        return "("+x+"; "+y+")";
    }

    public boolean equals(Object o){
        FunctionPoint oPoint=(FunctionPoint) o; //создаем объект класса точки, чтобы обратиться к o.x
        return (o.getClass()==getClass()) && (x==oPoint.x && y==oPoint.y);
    }

    public int hashCode(){
        long longX=Double.doubleToLongBits(x);
        long longY=Double.doubleToLongBits(y);
        int x1=(int)(longX)&(0x0000ffff); //получим младшие биты
        int x2=(int)(longX>>32); //получим старшие биты
        int y1=(int)(longY)&(0x0000ffff);
        int y2=(int)(longY>>32);
        return x1^x2^y1^y2;
    }

    public Object clone(){
        Object result=null;
        try{
            result=super.clone();
        } catch (CloneNotSupportedException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
