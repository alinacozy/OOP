package threads;

import functions.Functions;

public class Integrator extends SimpleIntegrator {
    Semaphore semaphore;

    public Integrator(Task t, Semaphore s) {
        task = t;
        semaphore = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.countOfTasks; ++i) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Integrator прервали");
                return; //если поток прервали, выходим из метода
            }
            try {
                semaphore.beginRead();
                double integral = Functions.integral(task.f, task.leftX, task.rightX, task.step);
                System.out.println("Result " + task.leftX + ' ' + task.rightX + ' ' + task.step + ' ' + integral);
                semaphore.endRead();
            } catch (InterruptedException e) {
                System.out.println("Integrator прервали");
                return;
            }
        }
    }
}
