package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable {

    Task task;

    public SimpleGenerator(Task t) {
        task = t;
    }

    public SimpleGenerator() {
    }

    @Override
    public void run() {
        for (int i = 0; i < task.countOfTasks; ++i) {
            double base = Math.random() * 9 + 1;
            synchronized (task) {
                task.f = new Log(base);
                task.leftX = Math.random() * 100;
                task.rightX = Math.random() * 100 + 100;
                task.step = Math.random();
                System.out.println("Source " + task.leftX + ' ' + task.rightX + ' ' + task.step);
            }
        }
    }
}
