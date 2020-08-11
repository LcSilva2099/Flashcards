package logic;

import java.util.HashMap;
import java.util.Map;

public class ArgumentHandler {

    Map<String, String> arguments;

    public ArgumentHandler(String[] args) {

        arguments = new HashMap<>();
        separateArguments(args);
    }

    private void separateArguments(String[] args) {

        for (int i = 0; i < args.length; i += 2) {
            arguments.put(args[i], args[i + 1]);
        }
    }

    public String getImport() {
        if (arguments.containsKey("-import")) {
            return arguments.get("-import");
        }

        return null;
    }

    public String getExport() {
        if (arguments.containsKey("-export")) {
            return arguments.get("-export");
        }

        return null;
    }

}
