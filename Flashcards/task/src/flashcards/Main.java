package flashcards;

import logic.ArgumentHandler;
import ui.TextUI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        TextUI textUI = new TextUI(new Scanner(System.in), argumentHandler);
        textUI.start();
    }
}
