package com.peter.flashcard;

import android.app.Activity;
import android.util.Log;
import android.view.Window;

import com.peter.flashcard.view.FlashCardFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.WindowFeature;

@EActivity(R.layout.activity_flash_card)
@OptionsMenu(R.menu.flash_card)
public class FlashCard extends Activity {

    @FragmentById
    FlashCardFragment flashCardFragment;

    @Extra
    int sleepingTime;

    @AfterViews
    public void afterView(){
        flashCardFragment.setSleepingTime(sleepingTime);
        Log.v("TAG",sleepingTime+"");
    }
}
