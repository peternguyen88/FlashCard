package com.peter.flashcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.flashcard.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/14/2014.
 */
@EViewGroup(R.layout.bottom_card_view)
public class BottomCardView extends LinearLayout{

    @ViewById
    public TextView wordView;

    public BottomCardView(Context context) {
        super(context);
    }

    public BottomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
