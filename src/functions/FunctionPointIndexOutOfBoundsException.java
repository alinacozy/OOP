package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException{
    public FunctionPointIndexOutOfBoundsException() {
    }

    public FunctionPointIndexOutOfBoundsException(String s) {
        super(s);
    }

    public FunctionPointIndexOutOfBoundsException(int index) {
        super(index);
    }

    public FunctionPointIndexOutOfBoundsException(long index) {
        super(index);
    }
}
