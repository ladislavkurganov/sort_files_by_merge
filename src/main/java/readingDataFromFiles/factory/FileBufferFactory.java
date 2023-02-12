package readingDataFromFiles.factory;


import readingDataFromFiles.DataFileStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;

public interface FileBufferFactory<T extends Comparable<T>> {

    DataFileStack<T> create(String fileName, BufferedReader reader, Comparator<T> comparator) throws IOException;
}
