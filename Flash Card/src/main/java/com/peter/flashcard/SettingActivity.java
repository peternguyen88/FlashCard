package com.peter.flashcard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;

import com.cengalabs.flatui.FlatUI;
import com.j256.ormlite.dao.Dao;
import com.peter.flashcard.content.ResourceLoader;
import com.peter.flashcard.model.Definition;
import com.peter.flashcard.model.Word;
import com.peter.flashcard.persistence.DatabaseHelper;
import com.peter.flashcard.pref.SharedPrefs_;
import com.peter.flashcard.view.fragment.QuizConfigFragment;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;


@EActivity(R.layout.activity_setting)
@OptionsMenu(R.menu.setting)
public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.setDefaultTheme(FlatUI.SEA);
    }

    @ViewById(R.id.seekBar)
    public SeekBar seekBar;

    @Click(R.id.startButton)
    public void startButtonClick() {
        FlashCard_.intent(this).sleepingTime(convertSeekBarToSleepingTime()).start();
    }

    @Click(R.id.popupButton)
    protected void popupButtonClick(){
        QuizConfigFragment.instance().show(getSupportFragmentManager(),"POPUP NOW");
    }

    private int convertSeekBarToSleepingTime() {
        int progress = seekBar.getProgress();
        if (progress == 100) progress = 99;
        return (int) ((100 - progress) / 100.0 * (10000));
    }
}
