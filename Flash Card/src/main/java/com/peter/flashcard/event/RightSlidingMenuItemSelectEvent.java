package com.peter.flashcard.event;

import com.peter.flashcard.model.Word;

/**
 * Created by Peter on 5/31/2014.
 */
public class RightSlidingMenuItemSelectEvent extends SlidingMenuItemSelectEvent {

    Word selectedWord;

    public RightSlidingMenuItemSelectEvent(Word word){
        this.selectedWord = word;
        this.postion = MENU_POSTION.RIGHT;
    }

    public Word getSelectedWord() {
        return selectedWord;
    }
}
