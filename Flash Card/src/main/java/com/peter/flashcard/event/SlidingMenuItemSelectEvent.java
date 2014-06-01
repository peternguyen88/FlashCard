package com.peter.flashcard.event;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.peter.flashcard.view.SlidingMenuItem;

/**
 * Created by Peter on 5/28/2014.
 */
public class SlidingMenuItemSelectEvent {
    public enum MENU_POSTION {LEFT,RIGHT};

    MENU_POSTION postion;

    public SlidingMenuItemSelectEvent(){
        this.postion = MENU_POSTION.LEFT;
    }

    public SlidingMenuItemSelectEvent(MENU_POSTION postion){
        this.postion = postion;
    }

    public boolean isLeftMenu(){
        return this.postion == MENU_POSTION.LEFT;
    }

    public boolean isRightMenu(){
        return this.postion == MENU_POSTION.RIGHT;
    }
}
