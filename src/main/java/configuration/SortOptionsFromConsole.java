package configuration;

import exceptions.ParameterWasNotSpecifiedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortOptionsFromConsole {

    private final boolean reverse;
    private final boolean string;
    private final List<String> files;
    private final String outputFile;

    public SortOptionsFromConsole(boolean reverse, boolean string, List<String> files, String outputFile) {
        this.reverse = reverse;
        this.string = string;
        this.files = files;
        this.outputFile = outputFile;
    }

    public static SortOptionsFromConsole parseArgsAndGetSortOptions(String[] args) {
        ArrayList<String> arg = new ArrayList<>(Arrays.asList(args));
        return parseArgs(arg);
    }

    private static SortOptionsFromConsole parseArgs(ArrayList<String> args) {

        boolean isReverse = false;
        boolean isString;
        List<String> files = new ArrayList<>();
        String outputFile = null;

        if (args.contains("-d")) {
            args.remove("-d");
            isReverse = true;
        }
        if (args.contains("-a")) {
            args.remove("-a");
        }
        if (args.contains("-s")) {
            args.remove("-s");
            isString = true;
        } else if (args.contains("-i")) {
            args.remove("-i");
            isString = false;
        } else {
            throw new ParameterWasNotSpecifiedException(ParameterWasNotSpecifiedException.MESSAGE_FLAG_WAS_NOT_SPECIFIED);
        }

        if (args.isEmpty() || args.size() < 2) {
            throw new ParameterWasNotSpecifiedException(ParameterWasNotSpecifiedException.MESSAGE_FILE_WAS_NOT_SPECIFIED);
        }

        for (String arg : args) {
            if (outputFile == null) {
                outputFile = arg;
            } else {
                files.add(arg);
            }
        }

        return new SortOptionsFromConsole(isReverse, isString, files, outputFile);
    }

    public boolean isReverse() {
        return reverse;
    }

    public boolean isString() {
        return string;
    }

    public List<String> getFiles() {
        return files;
    }

    public String getOutputFile() {
        return outputFile;
    }
}