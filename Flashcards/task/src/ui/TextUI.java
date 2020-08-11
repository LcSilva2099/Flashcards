package ui;

import logic.ArgumentHandler;
import logic.FlashcardManager;
import logic.LogWriter;

import java.io.IOException;
import java.util.Scanner;

public class TextUI {

    private Scanner scanner;
    private FlashcardManager flashcardManager;
    private ArgumentHandler argumentHandler;

    public TextUI(Scanner scanner, ArgumentHandler argumentHandler) throws IOException {
        this.scanner = scanner;
        flashcardManager = new FlashcardManager(scanner, new LogWriter());
        this.argumentHandler = argumentHandler;
    }

    public void start() throws IOException {

        String file = argumentHandler.getImport();

        if (file != null) {
            flashcardManager.getFileManager().importCards(file);
        }

        menu();
    }

    public void menu() throws IOException {

        String inputAction = "Input the action (add, remove, import, export, ask, exit, log, " +
                "hardest card, reset stats):";
        flashcardManager.addToLog(inputAction);

        System.out.println(inputAction);

        String choice = scanner.nextLine();
        flashcardManager.addToLog(choice);

        while (!("exit".equals(choice))) {
            switch (choice) {

                case "add":
                    addCard();
                    break;
                case "remove":
                    flashcardManager.remove();
                    break;
                case "import":
                    flashcardManager.importCards();
                    break;
                case "export":
                    flashcardManager.exportCards();
                    break;
                case "ask":
                    flashcardManager.howManyTimesToAsk();
                    break;
                case "log":
                    saveLog();
                    break;
                case "hardest card":
                    flashcardManager.hardestCard();
                    break;
                case "reset stats":
                    flashcardManager.resetStats();
                    break;
            }

            String newAction = "\nInput the action (add, remove, import, export, ask, exit, log, " +
                    "hardest card, reset stats):";
            flashcardManager.addToLog(newAction);

            System.out.println(newAction);

            choice = scanner.nextLine();
            flashcardManager.addToLog(choice);
        }

        System.out.println("Bye bye!");
        flashcardManager.addToLog("Bye bye!");

        String file = argumentHandler.getExport();

        if (file != null) {
            System.out.println();
            flashcardManager.getFileManager().exportCards(file);
        }
    }

    public void addCard() {

        System.out.println("The card:");
        String front = flashcardManager.checkFront(scanner.nextLine());

        if (!(front == null)) {

            String askDefinition = "The definition of the card:";
            flashcardManager.addToLog(askDefinition);

            System.out.println(askDefinition);

            String verse = flashcardManager.checkVerse(scanner.nextLine());
            flashcardManager.addToLog(verse);

            if (!(verse == null)) {
                flashcardManager.add(front, verse);

                String addedMessage = "The pair (\"" + front + "\":\"" + verse + "\") has been added.";
                flashcardManager.addToLog(addedMessage);

                System.out.println(addedMessage);
            }
        }
    }

    public void saveLog() throws IOException {

        System.out.println("File name:");
        flashcardManager.addToLog("File name:");

        String file = scanner.nextLine();
        flashcardManager.addToLog(file);

        flashcardManager.exportLog(file);
    }
}
