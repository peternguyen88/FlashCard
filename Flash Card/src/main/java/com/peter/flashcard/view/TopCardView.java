package com.peter.flashcard.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.flashcard.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/13/2014.
 */
@EViewGroup(R.layout.top_card_view)
public class TopCardView extends LinearLayout{

    @ViewById
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
