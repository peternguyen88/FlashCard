package com.peter.flashcard;

import android.app.Activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.peter.flashcard.adapter.DrawerAdapter;
import com.peter.flashcard.view.FlashCardFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_flash_card)
@OptionsMenu(R.menu.flash_card)
public class FlashCard extends Activity {

    @FragmentById
    FlashCardFragment flashCardFragment;

    @Extra
    int sleepingTime;

    private SlidingMenu slidingMenu;

    @AfterViews
    public void initSlidingMenu(){
        slidingMenu = new SlidingMenu(this);

        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_width);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slide_drawer);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(slidingMenu.isMenuShowing()){
            slidingMenu.toggle();
        }
        else {
            super.onBackPressed();
        }
    }

    @OptionsItem(android.R.id.home)
    public void toggleSlidingMenu(){
        slidingMenu.toggle();
    }
}
