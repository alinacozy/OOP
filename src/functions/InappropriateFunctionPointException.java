package functions;

public class InappropriateFunctionPointException extends Exception{
    public InappropriateFunctionPointException() {
    }

    public InappropriateFunctionPointException(String message) {
        super(message);
    }

    public InappropriateFunctionPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public InappropriateFunctionPointException(Throwable cause) {
        super(cause);
    }

    public InappropriateFunctionPointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
