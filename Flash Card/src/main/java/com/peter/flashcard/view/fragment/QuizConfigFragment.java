package com.peter.flashcard.view.fragment;

import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.peter.flashcard.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.WindowFeature;

/**
 * Created by Peter on 6/10/2014.
 */
@EFragment(R.layout.fragment_quiz_config)
public class QuizConfigFragment extends DialogFragment{

    public static QuizConfigFragment instance(){
        QuizConfigFragment popup = new QuizConfigFragment_();
        return popup;
    }

    @AfterViews
    protected void setTitle(){
        this.getDialog().getWindow().setTitle("Quiz Settings");
    }


}
