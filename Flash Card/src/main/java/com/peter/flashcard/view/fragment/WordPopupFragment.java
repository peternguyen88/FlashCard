package com.peter.flashcard.view.fragment;

import android.app.DialogFragment;

import com.peter.flashcard.R;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Peter on 5/19/2014.
 */
@EFragment(R.layout.word_definition_popup_fragment)
public class WordPopupFragment extends DialogFragment {

    public static WordPopupFragment instance(){
        return new WordPopupFragment_();
    }
}
