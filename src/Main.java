import functions.*;
import functions.basic.Exp;
import functions.basic.Log;
import functions.meta.Sum;
import threads.*;


public class Main {
    public static void nonThread() {
        Task task = new Task();
        task.countOfTasks = 100;
        for (int i = 0; i < task.countOfTasks; ++i) {
            double base = Math.random() * 9 + 1;
            task.f = new Log(base);
            task.leftX = Math.random() * 100;
            task.rightX = Math.random() * 100 + 100;
            task.step = Math.random();
            System.out.println("Source " + task.leftX + ' ' + task.rightX + ' ' + task.step);
            double integral = Functions.integral(task.f, task.leftX, task.rightX, task.step);
            System.out.println("Result " + task.leftX + ' ' + task.rightX + ' ' + task.step + ' ' + integral);
        }
    }

    public static void simpleThreads(){
        Task task=new Task();
        task.countOfTasks=100;
        SimpleGenerator generator=new SimpleGenerator(task);
        Thread generatorThread=new Thread(generator);
        SimpleIntegrator integrator=new SimpleIntegrator(task);
        Thread integratorThread=new Thread(integrator);
        generatorThread.start();
        integratorThread.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task task=new Task();
        task.countOfTasks=1000;
        Semaphore semaphore=new Semaphore();
        Generator generator=new Generator(task, semaphore);
        Thread generatorThread=new Thread(generator);
        Integrator integrator=new Integrator(task, semaphore);
        Thread integratorThread=new Thread(integrator);
        generatorThread.setPriority(Thread.MIN_PRIORITY);
        integratorThread.setPriority(Thread.MAX_PRIORITY);
        generatorThread.start();
        integratorThread.start();

        Thread.currentThread().sleep(50);
        System.out.println("...Прошло 50 миллисекунд...");
        generatorThread.interrupt();
        integratorThread.interrupt();
    }

    public static void main(String[] args) {
        Function exp = new Exp();
        double theoretical_integral_exp = Math.exp(1) - 1;
        double step = 0.1;
        double integral_exp = Functions.integral(exp, 0, 1, step);
        while (Math.abs(theoretical_integral_exp - integral_exp) > 1e-7) {
            step /= 2;
            integral_exp = Functions.integral(exp, 0, 1, step);
        }
        System.out.println("Теоретическое значение интеграла от экспоненты: " + theoretical_integral_exp);
        System.out.println("Рассчитанное значение интеграла от экпоненты: " + integral_exp);
        System.out.println("Разница теоретического и рассчитанного значения: " + Math.abs(theoretical_integral_exp - integral_exp));
        System.out.println("Шаг дискретизации: " + step);
        System.out.println();

//        System.out.println("Проверка работы метода nonThread:");
//        nonThread();
//        System.out.println();

//        System.out.println("Проверка работы метода simpleThreads:");
//        simpleThreads();

        System.out.println("Проверка работы метода complicatedThreads");
        try {
            complicatedThreads();
        } catch (InterruptedException e) { //если прервали основной поток, выбросится это исключение
            System.err.println("Ошибка! Прерван основной поток программы!");
        }

    }
}