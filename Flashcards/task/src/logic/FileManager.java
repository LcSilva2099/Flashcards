package logic;

import domain.Flashcard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileManager {

    private LogWriter logWriter;
    private Map<String, Flashcard> flashcards;

    public FileManager(LogWriter logWriter, Map<String, Flashcard> flashcards) {
        this.logWriter = logWriter;
        this.flashcards = flashcards;
    }

    public void importCards(String file) throws IOException {

        if (file.isEmpty()) {

            Scanner scanner = new Scanner(System.in);

            String askFileName = "File name:";
            logWriter.write(askFileName);

            System.out.println(askFileName);

            file = scanner.nextLine();
            logWriter.write(file);
        }

        List<String> list = new ArrayList<>();

        try (Scanner fileReader = new Scanner(Paths.get(file))) {

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine();
                logWriter.write(line);

                list.add(line);
            }

        } catch (Exception e) {

            System.out.println("File not found");
            logWriter.write("File not found");

            return;
        }

        if (!(list.isEmpty())) {
            for (String s : list) {
                String[] parts = s.split(";");
                flashcards.put(parts[0], new Flashcard(parts[0], parts[1], Integer.parseInt(parts[2])));
            }

            String nOfCardsLoaded = list.size() + " cards have been loaded.";
            logWriter.write(nOfCardsLoaded);

            System.out.println(nOfCardsLoaded);
        }
    }

    public void exportCards(String fileName) throws IOException {

        File file;

        if (fileName.isEmpty()) {
            Scanner scanner = new Scanner(System.in);

            String askFileName = "File name:";
            logWriter.write(askFileName);

            System.out.println(askFileName);

            file = new File(scanner.nextLine());
            logWriter.write(file.toString());
        } else {
            file = new File(fileName);
        }

        if (flashcards.isEmpty()) {

            String noCardsMessage = "0 cards have been saved.";
            logWriter.write(noCardsMessage);

            System.out.println(noCardsMessage);

            return;
        }

        FileWriter fileWriter = new FileWriter(file);

        flashcards.forEach((key, value) -> {
            try {
                fileWriter.write(key + ";" + value.getDefinition() + ";" + value.getErrors() + "\n");
            } catch (IOException ignored) {
            }
        });

        fileWriter.flush();
        fileWriter.close();

        String savedMessage = flashcards.size() + " cards have been saved.";
        logWriter.write(savedMessage);

        System.out.println(savedMessage);
    }
}
