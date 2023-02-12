package merge;

import configuration.SortOptionsFromConsole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import readingDataFromFiles.factory.FileBufferFactory;
import readingDataFromFiles.factory.IntegerFileBufferFactory;
import readingDataFromFiles.factory.StringFileBufferFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

class MergeSorterTest {
    @Test
    <T extends Comparable<T>> void getResultOfMerge_reverse() throws IOException {
        final String[] args = new String[]{"-i", "-d", "result.txt", "int1", "int2"};
        Charset cs = Charset.defaultCharset();
        SortOptionsFromConsole options = SortOptionsFromConsole.parseArgsAndGetSortOptions(args);

        List<File> files = options.getFiles().stream()
                .map(File::new)
                .filter(File::exists)
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            System.out.println("Input files do not exist");
        }

        File out = new File(options.getOutputFile());

        if (out.length() != 0) {
            out.delete();
            out = new File(options.getOutputFile());
        }

        Comparator<T> comparator = Comparable::compareTo;
        if (options.isReverse()) {
            comparator = Comparator.reverseOrder();
        }

        FileBufferFactory<T> fileBufferFactory = options.isString()
                ? (FileBufferFactory<T>) new StringFileBufferFactory()
                : (FileBufferFactory<T>) new IntegerFileBufferFactory();

        MergeSorter<T> mergeSort = new MergeSorter<>(
                comparator,
                fileBufferFactory);

        mergeSort.getResultOfMerge(files, out, cs);
        InputStream in = new FileInputStream(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, cs));
        Comparator<Integer> cmp = Integer::compareTo;
        cmp = cmp.reversed();
        List<Integer> check = new ArrayList<>();
        for (int i = 0; i <1000; i++)
        {
            check.add(Integer.parseInt(br.readLine()));
        }
        Integer min = check.get(0);
        for (Integer current : check) {
            boolean result = (cmp.compare(current, min)) >= 0;
            if (result)
                min = current;
            Assertions.assertTrue(result);
        }
    }


    @Test
    <T extends Comparable<T>>  void getResultOfMerge_Ascending() throws IOException {
        Charset cs = Charset.defaultCharset();
        final String[] args = new String[]{"-s", "res.txt", "str1.txt","str2.txt","str3.txt"};
        SortOptionsFromConsole options = SortOptionsFromConsole.parseArgsAndGetSortOptions(args);
        List<File> files = options.getFiles().stream()
                .map(File::new)
                .filter(File::exists)
                .collect(Collectors.toList());

        File out = new File(options.getOutputFile());
        if (out.length() != 0) {
            out.delete();
            out = new File(options.getOutputFile());
        }

        Comparator<T> comparator = Comparable::compareTo;
        if (options.isReverse()) {
            comparator = Comparator.reverseOrder();
        }

        FileBufferFactory<T> fileBufferFactory = options.isString()
                ? (FileBufferFactory<T>) new StringFileBufferFactory()
                : (FileBufferFactory<T>) new IntegerFileBufferFactory();

        MergeSorter<T> mergeSort = new MergeSorter<>(
                comparator,
                fileBufferFactory);

        mergeSort.getResultOfMerge(files, out, cs);
        InputStream in = new FileInputStream(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, cs));
        Comparator<String> cmp = Comparator.comparing(String::toString);
        List<String> check = br.lines().toList();
        String min = check.get(0);
        for (String current : check) {
            boolean result = (cmp.compare(current, min)) >= 0;
            if (result)
                min = current;
            Assertions.assertTrue(result);
        }
    }
}