package readingDataFromFiles.factory;

import readingDataFromFiles.DataFileStack;
import readingDataFromFiles.IntegerFileBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;

public class IntegerFileBufferFactory implements FileBufferFactory<Integer> {

    @Override
    public DataFileStack<Integer> create(String fileName, BufferedReader reader, Comparator<Integer> comparator) throws IOException {
        return new IntegerFileBuffer(reader, comparator, fileName);
    }
}
