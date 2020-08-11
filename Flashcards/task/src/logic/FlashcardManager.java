package logic;

import domain.Flashcard;

import java.io.IOException;
import java.util.*;

public class FlashcardManager {

    Map<String, Flashcard> flashcards;
    Scanner scanner;
    LogWriter logWriter;
    FileManager fileManager;
    Statistics statistics;

    public FlashcardManager(Scanner scanner, LogWriter logWriter) {
        flashcards = new TreeMap<>();
        this.scanner = scanner;
        this.logWriter = logWriter;
        fileManager = new FileManager(logWriter, flashcards);
        statistics = new Statistics(flashcards);

    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void add(String front, String verse) {

        if (front == null || verse == null) {
            return;
        }

        if (flashcards.containsKey(front)) {

            String line = "The card \""+ front + "\" already exists.";
            logWriter.write(line);

            System.out.println(line);

        } else {
            flashcards.put(front, new Flashcard(front, verse, 0));
        }
    }

    public void remove() {

        String cardLine = "The card:";
        logWriter.write(cardLine);

        System.out.println(cardLine);

        String front = scanner.nextLine();
        logWriter.write(front);

        if (!(flashcards.containsKey(front))) {

            String failMessage = "Can't remove \"" + front + "\": there is no such card.";
            logWriter.write(failMessage);

            System.out.println(failMessage);

        } else {

            String successMessage = "The card has been removed";
            logWriter.write(successMessage);

            System.out.println(successMessage);

            flashcards.remove(front);

            System.out.println();
            logWriter.write("\n");
        }
    }

    public String checkFront(String front) {

        if (flashcards.containsKey(front)) {

            String line = "The card \"" + front + "\" already exists.";
            logWriter.write(line);

            System.out.println(line);

            return null;
        }

        return front;
    }

    public String checkVerse(String verse) {

        for (Flashcard flashcard : flashcards.values()) {

            if (flashcard.getDefinition().equals(verse)) {

                String alreadyExists = "The definition \"" + verse + "\" already exists.";
                logWriter.write(alreadyExists);

                System.out.println(alreadyExists);

                return null;
            }
        }

        return verse;
    }

    public void howManyTimesToAsk() {

        String howMany = "How many times to ask?";
        logWriter.write(howMany);

        System.out.println(howMany);

        int timesToAsk = Integer.parseInt(scanner.nextLine());
        System.out.println();

        addToLog(timesToAsk +"\n");

        for (int i = 0; i < timesToAsk; i++) {
            ask();
        }
    }

    private void ask() {

        Random random = new Random();

        List<String> keysAsArray = new ArrayList<>(flashcards.keySet());

        int index = random.nextInt(keysAsArray.size());

        String question = keysAsArray.get(index);

        String askingToPrint = "Print the definition of \"" + question + "\"";
        logWriter.write(askingToPrint);

        System.out.println(askingToPrint);

        String answer = scanner.nextLine();
        logWriter.write(answer);

        checkAnswer(question, answer);
    }

    private void checkAnswer(String front, String answer) {

        String trueDefinition = flashcards.get(front).getDefinition();
        int currentErrors = flashcards.get(front).getErrors();

        boolean isThereADefinition = false;

        for (Flashcard f : flashcards.values()) {
            if (f.getDefinition().equals(answer)) {
                isThereADefinition = true;
                break;
            }
        }

        if (answer.equals(trueDefinition)) {

            System.out.println("Correct!");
            logWriter.write("Correct!");

        } else if (isThereADefinition) {

            for (Flashcard flashcard : flashcards.values()) {

                if (flashcard.getDefinition().equals(answer)) {

                    String frontForTheAnswer = flashcard.getTerm();

                    String otherDefinition = "\"Wrong. The right answer is \"" + flashcards.get(front).getDefinition() + "\", " +
                            "but your " + "definition is correct for \"" + frontForTheAnswer + "\".";

                    logWriter.write(otherDefinition);

                    flashcards.get(front).setErrors(currentErrors + 1);

                    System.out.println(otherDefinition);

                    break;
                }
            }
        } else {

            String wrongMessage = "Wrong. The right answer is \"" + flashcards.get(front).getDefinition() + "\".\n";
            logWriter.write(wrongMessage);

            flashcards.get(front).setErrors(currentErrors + 1);

            System.out.print(wrongMessage);
        }
    }

    public void importCards() throws IOException { fileManager.importCards(""); }

    public void exportCards() throws IOException { fileManager.exportCards(""); }

    public void addToLog(String line) { logWriter.write(line); }

    public void exportLog(String fileName) throws IOException { logWriter.save(fileName); }

    public void hardestCard() {

        String hardest = statistics.toString();
        logWriter.write(hardest);

        System.out.println(hardest);;
    }

    public void resetStats() {

        statistics.reset();

        String success = "Card statistics have been reset.";
        logWriter.write(success);

        System.out.println(success);
    }
}
