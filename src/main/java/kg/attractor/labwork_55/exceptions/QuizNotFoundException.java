package kg.attractor.labwork_55.exceptions;

import java.util.NoSuchElementException;

public class QuizNotFoundException extends NoSuchElementException {
    public QuizNotFoundException() {
        super("Quiz not found!");
    }

    public QuizNotFoundException(String message) {
        super(message);
    }
}
