package readingDataFromFiles.factory;

import readingDataFromFiles.DataFileStack;
import readingDataFromFiles.StringFileBuffer;

import java.io.BufferedReader;
import java.util.Comparator;

public class StringFileBufferFactory implements FileBufferFactory<String> {

    @Override
    public DataFileStack<String> create(String fileName, BufferedReader reader, Comparator<String> comparator) {
        return new StringFileBuffer(reader, comparator, fileName);
    }
}
