package functions;

import java.io.*;

public class TabulatedFunctions {
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
        return new ArrayTabulatedFunction(values);
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
            return new ArrayTabulatedFunction(values);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayTabulatedFunction(0, 0, 0);
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
            return new ArrayTabulatedFunction(values);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayTabulatedFunction(0, 0, 0);
    }
}
