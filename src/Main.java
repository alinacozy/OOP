import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
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

        System.out.println("Проверка работы фабрик (задание 2):");
        Function cosinus = new Cos();
        TabulatedFunction tabulatedCosinus;
        tabulatedCosinus=TabulatedFunctions.tabulate(cosinus, 0, Math.PI, 11);
        System.out.println(tabulatedCosinus.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tabulatedCosinus=TabulatedFunctions.tabulate(cosinus, 0, Math.PI, 11);
        System.out.println(tabulatedCosinus.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tabulatedCosinus=TabulatedFunctions.tabulate(cosinus,0, Math.PI, 11);
        System.out.println(tabulatedCosinus.getClass());
        System.out.println();

        System.out.println("Проверка работы итераторов классов (задание 1):");
        Function exp= new Exp();
        //создаем ArrayTabulatedFunction с помощью factory
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        TabulatedFunction f = TabulatedFunctions.tabulate(exp, 0, 10, 11);
        System.out.println("Для ArrayTabulatedFunction:");
        for (FunctionPoint p: f){
            System.out.println(p);
        }
        System.out.println();
        //создаем LinkedListTabulatedFunction с помощью factory
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        f = TabulatedFunctions.tabulate(exp, 0, 10, 11);
        System.out.println("Для LinkedListTabulatedFunction:");
        for (FunctionPoint p: f){
            System.out.println(p);
        }
        System.out.println();

        System.out.println("Проверка работы рефлексивных методов (задание 3):");
        //создаем табулированную функцию класса ArrayTabulatedFunction
        TabulatedFunction function;
        function = TabulatedFunctions.createTabulatedFunction(0, 10, 3, ArrayTabulatedFunction.class);
        System.out.println(function.getClass());
        System.out.println(function);

        //создаем табулированную функцию со списком значений типа ArrayTabulatedFunction
        function = TabulatedFunctions.createTabulatedFunction(
                0, 10, new double[] {0, 10}, ArrayTabulatedFunction.class);
        System.out.println(function.getClass());
        System.out.println(function);

        //создаем табулированную функцию массивом FunctionPoint типа LinkedListTabulatedFunction
        function = TabulatedFunctions.createTabulatedFunction(
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                },
                LinkedListTabulatedFunction.class
        );
        System.out.println(function.getClass());
        System.out.println(function);

        //создаем функцию методом tabulate типа LinkedListTabulatedFunction
        function = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, 11, LinkedListTabulatedFunction.class);
        System.out.println(function.getClass());
        System.out.println(function);

    }
}