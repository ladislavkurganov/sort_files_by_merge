package merge;


import readingDataFromFiles.DataFileStack;
import readingDataFromFiles.factory.FileBufferFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSorter<T extends Comparable<T>> {

    private final Comparator<T> comparator;
    private final FileBufferFactory<T> fileBufferFactory;

    public MergeSorter(Comparator<T> comparator, FileBufferFactory<T> fileBufferFactory) {
        this.comparator = comparator;
        this.fileBufferFactory = fileBufferFactory;
    }

    public boolean getResultOfMerge(List<File> files, File outputFile, Charset cs) throws IOException {
        List<DataFileStack<T>> bufferStorage = getBufferStorage(files,cs);
        BufferedWriter bufferedWriterInOutputFile = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputFile, true), cs));
        return mergeFilesIntoOutputFile(bufferedWriterInOutputFile, bufferStorage);
    }

    private boolean
    mergeFilesIntoOutputFile(BufferedWriter bufferedWriter, List<DataFileStack<T>> buffers) throws IOException {
        try (bufferedWriter) {
            while (true) {
                T tempLine = null;
                int index = -1;
                for (var buffer : buffers) {
                    if (priorityCheck(buffer.peek(), tempLine)) {
                        tempLine = buffer.peek();
                        index = buffers.indexOf(buffer);
                    }
                }
                if (index > -1) {
                    bufferedWriter.write(buffers.get(index).pop().toString());
                    bufferedWriter.newLine();
                } else {
                    break;
                }
            }
            return true;
        } finally {
            for (DataFileStack<T> buffer : buffers) {
                buffer.close();
            }
        }
    }

    private boolean priorityCheck(T peek, T tempLine) {
        if (peek == null)
            return false;
        else if (tempLine == null)
            return true;
        else
            return comparator.compare(peek, tempLine) <= 0;
    }

    private List<DataFileStack<T>>  getBufferStorage(List<File> files, Charset cs) throws IOException {
        List<DataFileStack<T>> storage = new ArrayList<>();
        for (File f : files) {
            if (f.length() == 0) {
                continue;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), cs));
            DataFileStack<T> buffer = fileBufferFactory.create(f.getName(), bufferedReader, comparator);
            storage.add(buffer);
        }

        return storage;
    }

}

