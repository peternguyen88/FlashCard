package com.peter.flashcard.event;

/**
 * Created by Peter on 5/30/2014.
 */
public class SpeedChangeEvent {
    int speed;

    public SpeedChangeEvent(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
