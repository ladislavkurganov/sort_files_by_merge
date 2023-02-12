package readingDataFromFiles;

import exceptions.FileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;

public final class StringFileBuffer implements DataFileStack<String> {
    private final String fileName;
    private final BufferedReader bufferedReader;
    private String cache;
    private final Comparator<String> comparator;

    public StringFileBuffer(BufferedReader r, Comparator<String> cmp, String fileName) {
        this.fileName = fileName;
        this.bufferedReader = r;
        this.comparator = cmp;
        try {
            this.cache = this.bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println(FileException.MESSAGE_ERROR_FILE + fileName + ":" + e.getMessage());
        }
    }

    public void close() {
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            throw new FileException(FileException.MESSAGE_ERROR_CLOSING_FILE + e.getMessage());
        }
    }

    public boolean empty() {
        return this.cache == null;
    }

    public String peek() {
        return this.cache;
    }

    public String pop() {
        if (peek() == null) {
            close();
            return null;
        }
        String answer = peek();
        reload();
        return answer;
    }

    private void reload() {
        String tmp = peek();
        try {
            this.cache = this.bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println(FileException.MESSAGE_ERROR_FILE + fileName + ":" + e.getMessage());
        }

        if (this.cache != null) {
            if ((comparator.compare(tmp, this.cache) > 0) || (this.cache.matches("\\s"))) {
                System.out.println(this.fileName + FileException.MESSAGE_DATA_WAS_NOT_SORTED);
                this.cache = null;
                close();
            }
        }
    }
}
