package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable {

    Task task;

    public SimpleIntegrator(Task t) {
        task = t;
    }

    public SimpleIntegrator() {
    }

    @Override
    public void run() {
        for (int i = 0; i < task.countOfTasks; ++i) {
            synchronized (task) {
                double integral = Functions.integral(task.f, task.leftX, task.rightX, task.step);
                System.out.println("Result " + task.leftX + ' ' + task.rightX + ' ' + task.step + ' ' + integral);
            }
        }
    }
}
