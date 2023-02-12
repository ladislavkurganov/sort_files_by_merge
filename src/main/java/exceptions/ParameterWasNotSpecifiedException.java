package exceptions;

public class ParameterWasNotSpecifiedException extends RuntimeException {
    public static final String MESSAGE_FLAG_WAS_NOT_SPECIFIED = "the required data type parameter was not specified";
    public static final String MESSAGE_FILE_WAS_NOT_SPECIFIED = "output file name and names of input files, at least one was not specified";

    public ParameterWasNotSpecifiedException(String message) {
        super(message);
    }

    public ParameterWasNotSpecifiedException(String message, Throwable e) {
        super(message, e);
    }

}
