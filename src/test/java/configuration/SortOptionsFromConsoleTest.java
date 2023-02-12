package configuration;

import exceptions.ParameterWasNotSpecifiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortOptionsFromConsoleTest {
    @Test
    void parseArgsAndGetSortOptions_validValuesSortingNumbersAscendingOrder() {
        final String[] args = new String[]{"-i", "result.txt", "input", "input2"};
        final SortOptionsFromConsole params = SortOptionsFromConsole.parseArgsAndGetSortOptions(args);

        Assertions.assertNotNull(params);
        assertFalse(params.isReverse());
        assertFalse(params.isString());
        Assertions.assertEquals(params.getOutputFile(), "result.txt");
        Assertions.assertEquals(params.getFiles().get(0), "input");
        Assertions.assertEquals(params.getFiles().get(1), "input2");
    }

    @Test
    void parseArgsAndGetSortOptions_validValuesSortingNumbersDescendingOrder() {
        final String[] args = new String[]{"-d", "-i", "result.txt", "input", "input2"};
        final SortOptionsFromConsole params = SortOptionsFromConsole.parseArgsAndGetSortOptions(args);

        Assertions.assertNotNull(params);
        assertTrue(params.isReverse());
        assertFalse(params.isString());
        Assertions.assertEquals(params.getOutputFile(), "result.txt");
        Assertions.assertEquals(params.getFiles().get(0), "input");
        Assertions.assertEquals(params.getFiles().get(1), "input2");
    }

    @Test
    void parseArgsAndGetSortOptions_notValidValuesInputFilesNotSpecified() {
        final String[] args = new String[]{"-d", "-i", "result.txt"};
        Assertions.assertThrows(ParameterWasNotSpecifiedException.class, () -> SortOptionsFromConsole.parseArgsAndGetSortOptions(args));
    }

    @Test
    void parseArgsAndGetSortOptions_notValidValues() {
        final String[] args = new String[]{"i am not valid!!!!"};
        Assertions.assertThrows(ParameterWasNotSpecifiedException.class, () -> SortOptionsFromConsole.parseArgsAndGetSortOptions(args));

    }
}