package com.peter.flashcard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;


@EActivity(R.layout.activity_flash_card_quiz)
public class FlashCardQuiz extends Activity {

    @Extra @InstanceState
    protected int numberOfQuestion;

    @Extra @InstanceState
    protected int timePerQuestion;

}
