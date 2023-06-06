package exception;

public class FunctionException extends RuntimeException {
    public FunctionException(Throwable throwable) {
        super(throwable);
    }
}
