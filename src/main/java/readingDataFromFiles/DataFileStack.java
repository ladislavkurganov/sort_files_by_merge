package readingDataFromFiles;


import java.io.IOException;

public interface DataFileStack<T> {
    void close();

    boolean empty();

    T peek();

    T pop() throws IOException;
}