package threads;

import functions.basic.Log;

public class Generator extends SimpleGenerator {
    Semaphore semaphore;

    public Generator(Task t, Semaphore s) {
        task = t;
        semaphore = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.countOfTasks; ++i) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Generator прервали");
                return; //если поток прервали, выходим из метода
            }
            try {
                double base = Math.random() * 9 + 1;
                semaphore.beginWrite();
                task.f = new Log(base);
                task.leftX = Math.random() * 100;
                task.rightX = Math.random() * 100 + 100;
                task.step = Math.random();
                System.out.println("Source " + task.leftX + ' ' + task.rightX + ' ' + task.step);
                semaphore.endWrite();
            } catch (InterruptedException e) {
                System.out.println("Generator прервали");
                return;
            }
        }
    }

}
