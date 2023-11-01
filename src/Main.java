import functions.*;

public class Main {
    public static void main(String[] args) {
        //инициализация функций
        double[] valuesLinear={1,2,3,4};
        double[] valuesQuadratic={1,4,9,16};

        ArrayTabulatedFunction arrayLinear=new ArrayTabulatedFunction(1,4,valuesLinear);
        ArrayTabulatedFunction arrayLinear2=new ArrayTabulatedFunction(1,4,valuesLinear);
        LinkedListTabulatedFunction listLinear=new LinkedListTabulatedFunction(1, 4, valuesLinear);
        ArrayTabulatedFunction arrayQuadratic=new ArrayTabulatedFunction(1,4,valuesQuadratic);
        LinkedListTabulatedFunction listQuadratic=new LinkedListTabulatedFunction(1,4,valuesQuadratic);


        System.out.println("Проверка метода toString():");
        System.out.println("Строковое представление ArrayTabulatedFunction:");
        System.out.println(arrayLinear.toString());
        System.out.println("Строковое представление LinkedListTabulatedFunction:");
        System.out.println(listLinear.toString());
        System.out.print('\n');


        System.out.println("Проверка метода equals():");
        System.out.print("Сравнение одинаковых функций одинакового типа: ");
        System.out.println(arrayLinear.equals(arrayLinear2));
        System.out.print("Сравнение одинаковых функций разного типа: ");
        System.out.println(arrayLinear.equals(listLinear));
        System.out.print("Сравнение разных функций одинакового типа: ");
        System.out.println(arrayLinear.equals(arrayQuadratic));
        System.out.print("Сравнение разных функций разного типа: ");
        System.out.println(listLinear.equals(arrayQuadratic));
        System.out.print('\n');


        System.out.println("Проверка метода hashCode():");
        System.out.print("Значение хэш-кода для линейной функции, заданной массивом: ");
        System.out.println(arrayLinear.hashCode());
        System.out.print("Значение хэш-кода для копии линейной функции, заданной массивом: ");
        System.out.println(arrayLinear2.hashCode());
        System.out.print("Значение хэш-кода для линейной функции, заданной списком: ");
        System.out.println(listLinear.hashCode());
        System.out.print("Значение хэш-кода для квадратичной функции, заданной массивом: ");
        System.out.println(arrayQuadratic.hashCode());
        System.out.print("Значение хэш-кода для квадратичной функции, заданной списком: ");
        System.out.println(listQuadratic.hashCode());

        //попробуем незначительно изменить одну из точек квадратичной функции, заданной списком
        listQuadratic.setPointY(1, 4.003);
        System.out.print("Значение хэш-кода для измененной квадратичной функции, заданной списком: ");
        System.out.println(listQuadratic.hashCode());
        System.out.print('\n');


        System.out.println("Проверка клонирования для класса ArrayTabulatedFunction:");
        ArrayTabulatedFunction clonedArrayLinear=(ArrayTabulatedFunction) arrayLinear.clone();
        System.out.println("Исходная функция:");
        System.out.println(arrayLinear.toString());
        System.out.println("Клонированная функция:");
        System.out.println(clonedArrayLinear.toString());
        //изменим значение одной из точек исходной функции
        arrayLinear.setPointY(1,100);
        System.out.println("Исходная функция после изменения:");
        System.out.println(arrayLinear.toString());
        System.out.println("Клонированная функция после изменения:");
        System.out.println(clonedArrayLinear.toString());
        System.out.print('\n');


        System.out.println("Проверка клонирования для класса LinkedListTabulatedFunction:");
        LinkedListTabulatedFunction clonedListLinear=(LinkedListTabulatedFunction) listLinear.clone();
        System.out.println("Исходная функция:");
        System.out.println(listLinear.toString());
        System.out.println("Клонированная функция:");
        System.out.println(clonedListLinear.toString());
        //изменим значение одной из точек исходной функции
        listLinear.setPointY(2,-80);
        System.out.println("Исходная функция после изменения:");
        System.out.println(listLinear.toString());
        System.out.println("Клонированная функция после изменения:");
        System.out.println(clonedListLinear.toString());
        System.out.print('\n');
    }
}