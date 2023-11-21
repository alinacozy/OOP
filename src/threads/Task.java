package threads;

import functions.Function;
import functions.basic.Log;

public class Task {
    public Function f;
    public double leftX;
    public double rightX;
    public double step;
    public int countOfTasks;

    public Task(){
        f=new Log();
        leftX=0;
        rightX=0;
        step=1;
        countOfTasks=1;
    }
}
