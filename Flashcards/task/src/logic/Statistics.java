package logic;

import domain.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Statistics {

    private Map<String, Flashcard> flashcards;

    public Statistics(Map<String, Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public void reset() {

        flashcards.forEach((key, value) -> {
            value.setErrors(0);
        });
    }

    @Override
    public String toString() {

        List<String> mostErrors = new ArrayList<>();

        int qtyOfErrors = flashcards.values().stream().mapToInt(Flashcard::getErrors).max().orElse(0);

        if (qtyOfErrors == 0) {
            return "There are no cards with errors.";
        }

        flashcards.forEach((key, value) -> {
            if (qtyOfErrors == value.getErrors()) {
                mostErrors.add(key);
            }
        });

        if (mostErrors.size() == 1) {
            return "The hardest card is \"" + mostErrors.get(0) + "\". You have " + qtyOfErrors +
                    " errors answering it.";
        }

        StringBuilder sb = new StringBuilder("The hardest card are \"");

        for (int i = 0; i < mostErrors.size(); i++) {

            if (i == mostErrors.size() - 1) {
                sb.append(mostErrors.get(i)).append("\".");
            }

            sb.append(mostErrors.get(i)).append("\", ");
        }

        sb.append(" You have ").append(qtyOfErrors).append(" answering them.");

        return sb.toString();
    }
}
