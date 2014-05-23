package com.peter.flashcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.flashcard.R;
import com.peter.flashcard.model.Definition;
import com.peter.flashcard.model.Word;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/14/2014.
 */
@EViewGroup(R.layout.bottom_card_view)
public class BottomCardView extends LinearLayout {

    @ViewById(R.id.wordDefinition)
    public TextView wordDefinition;

    @ViewById(R.id.wordType)
    public TextView wordType;

    @ViewById(R.id.definitionOrder)
    public TextView definitionOrder;

    private Word word;

    public BottomCardView(Context context) {
        super(context);
    }

    public BottomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
        setDefinition(word.getRandomDefinition());
        updateDefinitionOrder();
    }

    @Click(R.id.wordDefinition)
    public void clickOnDefinition() {
        if (word != null) {
            setDefinition(word.nextDefinition());
            updateDefinitionOrder();
        }
    }

    private void setDefinition(Definition definition){
        this.wordDefinition.setText(definition.getDefinition());
        this.wordType.setText(definition.getType());
        this.wordType.setBackgroundResource(definition.getColor());
    }

    private void updateDefinitionOrder(){
        this.definitionOrder.setText(word.getDefinitionOrder());
    }
}
