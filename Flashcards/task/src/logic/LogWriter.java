package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogWriter {

    FileWriter fileWriter;
    List<String> log;

    public LogWriter() throws IOException {
        fileWriter = new FileWriter(new File("log.txt"));
        log = new ArrayList<>();
    }

    public void write(String line) {
        log.add(line);
    }

    public void save(String fileName) throws IOException {
        fileWriter = new FileWriter(new File(fileName));

        log.forEach(value -> {
            try {
                fileWriter.write(value + "\n");
            } catch (IOException ignored) {
            }
        });

        fileWriter.flush();
        fileWriter.close();

        System.out.println("The log has been saved");
        log.add("The log has been saved");
    }
}
