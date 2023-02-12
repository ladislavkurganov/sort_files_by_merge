package readingDataFromFiles;

import exceptions.FileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;

public final class IntegerFileBuffer implements DataFileStack<Integer> {
    private final String fileName;
    private final BufferedReader bufferedReader;
    private final Comparator<Integer> comparator;
    private Integer cache;

    public IntegerFileBuffer(BufferedReader r, Comparator<Integer> cmp, String fileName) {
        this.fileName = fileName;
        this.bufferedReader = r;
        this.comparator = cmp;
        setCache();
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

    public Integer peek() {
        return this.cache;
    }

    public Integer pop() {
        Integer answer = peek();
        reload();
        return answer;
    }

    private void reload() {
        Integer tmp = peek();
        setCache();

        if (cache != null && comparator.compare(tmp, this.cache) > 0) {
            System.out.println(this.fileName + FileException.MESSAGE_DATA_WAS_NOT_SORTED);
            this.cache = null;
            close();
        }
    }

    private void setCache() {
        try {
            String temp = bufferedReader.readLine();
            if (temp != null) {
                this.cache = Integer.parseInt(temp);
            } else {
                this.cache = null;
                close();
            }
        } catch (NumberFormatException exception) {
            System.err.println(FileException.MESSAGE_ERROR_INVALID_INTEGER + fileName + ":" + exception.getMessage());
            this.cache = null;
            close();
        } catch (IOException e) {
            System.err.println(FileException.MESSAGE_ERROR_FILE + fileName + ":" + e.getMessage());
        }
    }
}