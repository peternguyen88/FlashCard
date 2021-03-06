package com.peter.flashcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.flashcard.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/13/2014.
 */
@EViewGroup(R.layout.view_top_card)
public class TopCardView extends LinearLayout{

    @ViewById(R.id.wordDefinition)
    public TextView wordView;

    @ViewById
    LinearLayout layout;

    public TopCardView(Context context) {
        super(context);
    }

    public TopCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
