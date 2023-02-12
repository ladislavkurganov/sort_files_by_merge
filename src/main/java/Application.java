
import configuration.SortOptionsFromConsole;
import merge.MergeSorter;
import readingDataFromFiles.factory.FileBufferFactory;
import readingDataFromFiles.factory.IntegerFileBufferFactory;
import readingDataFromFiles.factory.StringFileBufferFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static <T extends Comparable<T>> void main(String[] args) {
        Charset cs = Charset.defaultCharset();
        SortOptionsFromConsole options = SortOptionsFromConsole.parseArgsAndGetSortOptions(args);

        List<File> files = options.getFiles().stream()
                .map(File::new)
                .filter(File::exists)
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            System.out.println("Input files do not exist");
            return;
        }

        File output = new File(options.getOutputFile());

        if (output.length() != 0) {
            output.delete();
            output = new File(options.getOutputFile());
        }

        Comparator<T> comparator = Comparable::compareTo;
        if (options.isReverse()) {
            comparator = Comparator.reverseOrder();
        }

        FileBufferFactory<T> fileBufferFactory = options.isString()
                ? (FileBufferFactory<T>) new StringFileBufferFactory()
                : (FileBufferFactory<T>) new IntegerFileBufferFactory();

        MergeSorter<T> mergeSortedFiles = new MergeSorter<>(
                comparator,
                fileBufferFactory);
        try {
            if (mergeSortedFiles.getResultOfMerge(files, output, cs))
                System.out.println("Sorting completed.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}