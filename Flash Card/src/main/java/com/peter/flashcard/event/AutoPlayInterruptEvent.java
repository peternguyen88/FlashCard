package com.peter.flashcard.event;

/**
 * Created by Peter on 5/30/2014.
 */
public class AutoPlayInterruptEvent {
    public static enum Mode{MODE_INTERRUP, MODE_RESUME}

    Mode mode;

    public AutoPlayInterruptEvent(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }
}
