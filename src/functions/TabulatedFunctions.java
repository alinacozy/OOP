package functions;

import functions.ArrayTabulatedFunction.ArrayTabulatedFunctionFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {
    private static TabulatedFunctionFactory currentFactory = new ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        currentFactory = factory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return currentFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return currentFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] values) {
        return currentFactory.createTabulatedFunction(values);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> functionClass)  throws IllegalArgumentException{
        try {
            Constructor<? extends TabulatedFunction> constructor = functionClass.getConstructor(double.class, double.class, int.class);
            return constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values, Class<? extends TabulatedFunction> functionClass) {
        try {
            Constructor<? extends TabulatedFunction> constructor = functionClass.getConstructor(double.class, double.class, double[].class);
            return constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] values, Class<? extends TabulatedFunction> functionClass) {
        try {
            Constructor<? extends TabulatedFunction> constructor = functionClass.getConstructor(FunctionPoint[].class);
            return constructor.newInstance(new Object[]{values});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private TabulatedFunctions() {
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || function.getRightDomainBorder() < rightX) {
            throw new IllegalArgumentException("The boundaries of the segment must lie inside the domain of the function");
        }
        double interval = (rightX - leftX) / (pointsCount - 1); //вычисление длины интервала
        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double currentX = leftX;
        for (int i = 0; i < pointsCount; ++i, currentX += interval) {
            values[i] = new FunctionPoint(currentX, function.getFunctionValue(currentX));
        }
        return currentFactory.createTabulatedFunction(values);
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> functionClass) {
        if (leftX < function.getLeftDomainBorder() || function.getRightDomainBorder() < rightX) {
            throw new IllegalArgumentException("The boundaries of the segment must lie inside the domain of the function");
        }
        double interval = (rightX - leftX) / (pointsCount - 1); //вычисление длины интервала
        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double currentX = leftX;
        for (int i = 0; i < pointsCount; ++i, currentX += interval) {
            values[i] = new FunctionPoint(currentX, function.getFunctionValue(currentX));
        }
        return TabulatedFunctions.createTabulatedFunction(values, functionClass);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeInt(function.getPointsCount());
            for (int i = 0; i < function.getPointsCount(); ++i) {
                dos.writeDouble(function.getPointX(i));
                dos.writeDouble(function.getPointY(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) {
        try {
            DataInputStream dis = new DataInputStream(in);
            int pCount = dis.readInt();
            FunctionPoint[] values = new FunctionPoint[pCount];
            double x, y;
            for (int i = 0; i < pCount; ++i) {
                x = dis.readDouble();
                y = dis.readDouble();
                values[i] = new FunctionPoint(x, y);
            }
            return currentFactory.createTabulatedFunction(values);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return currentFactory.createTabulatedFunction(0, 0, 0);
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in, Class<? extends TabulatedFunction> functionClass) {
        try {
            DataInputStream dis = new DataInputStream(in);
            int pCount = dis.readInt();
            FunctionPoint[] values = new FunctionPoint[pCount];
            double x, y;
            for (int i = 0; i < pCount; ++i) {
                x = dis.readDouble();
                y = dis.readDouble();
                values[i] = new FunctionPoint(x, y);
            }
            return TabulatedFunctions.createTabulatedFunction(values, functionClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return TabulatedFunctions.createTabulatedFunction(0, 0, 0, functionClass);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        pw.print(function.getPointsCount() + " ");
        for (int i = 0; i < function.getPointsCount(); ++i) {
            pw.print(function.getPointX(i) + " ");
            pw.print(function.getPointY(i) + " ");
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.parseNumbers();
        try {
            tokenizer.nextToken();
            int numOfPoints = (int) tokenizer.nval; //считали количество точек
            FunctionPoint[] values = new FunctionPoint[numOfPoints];
            double x;
            double y;
            for (int i = 0; i < numOfPoints; ++i) {
                tokenizer.nextToken(); //берем из потока число
                x = tokenizer.nval; //записываем координату x
                tokenizer.nextToken(); //берем из потока следующее число
                y = tokenizer.nval; //записываем координату y
                values[i] = new FunctionPoint(x, y); //добавляем точку в массив
            }
            return currentFactory.createTabulatedFunction(values);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return currentFactory.createTabulatedFunction(0, 0, 0);
    }

    public static TabulatedFunction readTabulatedFunction(Reader in, Class<? extends TabulatedFunction> functionClass) {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.parseNumbers();
        try {
            tokenizer.nextToken();
            int numOfPoints = (int) tokenizer.nval; //считали количество точек
            FunctionPoint[] values = new FunctionPoint[numOfPoints];
            double x;
            double y;
            for (int i = 0; i < numOfPoints; ++i) {
                tokenizer.nextToken(); //берем из потока число
                x = tokenizer.nval; //записываем координату x
                tokenizer.nextToken(); //берем из потока следующее число
                y = tokenizer.nval; //записываем координату y
                values[i] = new FunctionPoint(x, y); //добавляем точку в массив
            }
            return TabulatedFunctions.createTabulatedFunction(values, functionClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return TabulatedFunctions.createTabulatedFunction(0, 0, 0, functionClass);
    }
}
