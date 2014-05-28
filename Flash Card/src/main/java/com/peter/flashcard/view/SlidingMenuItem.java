package com.peter.flashcard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.flashcard.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/28/2014.
 */
@EViewGroup(R.layout.view_sliding_menu_item)
public class SlidingMenuItem extends LinearLayout{

    public SlidingMenuItem(Context context) {
        super(context);
    }

    public SlidingMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingMenuItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @ViewById(R.id.textView)
    TextView textView;

    public void setText(String text){
        this.textView.setText(text);
    }
}
