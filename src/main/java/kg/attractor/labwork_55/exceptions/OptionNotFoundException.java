package kg.attractor.labwork_55.exceptions;

import java.util.NoSuchElementException;

public class OptionNotFoundException extends NoSuchElementException {
    public OptionNotFoundException(String message) {
        super(message);
    }
}
