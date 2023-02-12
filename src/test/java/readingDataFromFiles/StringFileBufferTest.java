package readingDataFromFiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import readingDataFromFiles.factory.StringFileBufferFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Comparator;


class StringFileBufferTest {
    private StringFileBuffer createStringFileBuffer() throws IOException {
        Charset cs = Charset.defaultCharset();
        String fileName = "str1.txt";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), cs));
        Comparator<String> comparator = Comparable::compareTo;
        StringFileBufferFactory factory = new StringFileBufferFactory();
        return (StringFileBuffer) factory.create(fileName,bufferedReader,comparator );
    }

    @Test
    void pop_test() throws IOException {
        StringFileBuffer stringFileBuffer = createStringFileBuffer();
        Assertions.assertEquals(stringFileBuffer.peek(), "abcd");
        Assertions.assertEquals(stringFileBuffer.pop(), "abcd");
        Assertions.assertEquals(stringFileBuffer.peek(), "ac");
        stringFileBuffer.close();
    }
}