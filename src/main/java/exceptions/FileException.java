package exceptions;

public class FileException extends RuntimeException {
    public static final String MESSAGE_ERROR_FILE = "Error while working with  : ";
    public static final String MESSAGE_ERROR_INVALID_INTEGER = "Error in file  ";
    public static final String MESSAGE_ERROR_CLOSING_FILE = "Error closing file: %s";
    public static final String MESSAGE_DATA_WAS_NOT_SORTED= " was closed because the data was not sorted or data was be invalid";

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable e) {
        super(message, e);
    }

}
