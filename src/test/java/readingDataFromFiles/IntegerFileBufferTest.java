package readingDataFromFiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import readingDataFromFiles.factory.IntegerFileBufferFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Comparator;

class IntegerFileBufferTest {

    private IntegerFileBuffer createIntegerFileBuffer() throws IOException {
        Charset cs = Charset.defaultCharset();
        String fileName = "in1.txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), cs));
        Comparator<Integer> comparator = Comparable::compareTo;
        IntegerFileBufferFactory factory = new IntegerFileBufferFactory();
        return (IntegerFileBuffer) factory.create(fileName,bufferedReader,comparator );
    }

    @Test
    void pop_test() throws IOException {
        IntegerFileBuffer integerFileBuffer = createIntegerFileBuffer();
        Assertions.assertEquals(integerFileBuffer.peek(), 2);
        Assertions.assertEquals(integerFileBuffer.pop(), 2);
        Assertions.assertEquals(integerFileBuffer.peek(), 4);
        integerFileBuffer.close();
    }
}