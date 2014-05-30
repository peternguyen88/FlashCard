package com.peter.flashcard;

import android.support.v7.app.ActionBarActivity;

import com.google.common.eventbus.Subscribe;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.peter.flashcard.event.AutoPlayInterruptEvent;
import com.peter.flashcard.event.SlidingMenuItemSelectEvent;
import com.peter.flashcard.view.FlashCardFragment;
import com.peter.flashcard.view.fragment.SpeedAdjustmentPopupFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_flash_card)
@OptionsMenu(R.menu.flash_card)
public class FlashCard extends ActionBarActivity {

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

    @AfterInject
    public void registerEventBus(){
        AWLApplication.eventBus.register(this);
    }

    @Override
    protected void onDestroy() {
        AWLApplication.eventBus.unregister(this);
        super.onDestroy();
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

    @Subscribe
    public void slidingMenuItemSelect(SlidingMenuItemSelectEvent slidingMenuItemSelectEvent){
        if(slidingMenu.isMenuShowing()) slidingMenu.toggle();
    }

    @OptionsItem(R.id.speedAdjustment)
    protected void speedAdjustmentMenuItemSelected(){
        // Raising Interrupt Mode to stop Auto Play
        AWLApplication.eventBus.post(new AutoPlayInterruptEvent(AutoPlayInterruptEvent.Mode.MODE_INTERRUP));
        SpeedAdjustmentPopupFragment.instance().setCurrentSpeed(flashCardFragment.getSleepingTime())
                .show(getSupportFragmentManager(), "Speed Adjustment Dialog");
    }
}
