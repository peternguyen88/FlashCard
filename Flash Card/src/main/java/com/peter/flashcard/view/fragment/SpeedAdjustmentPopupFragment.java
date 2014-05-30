package com.peter.flashcard.view.fragment;

import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.peter.flashcard.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/29/2014.
 */
@EFragment(R.layout.fragment_speed_adjustment)
public class SpeedAdjustmentPopupFragment extends DialogFragment{

    @ViewById(R.id.speedTextView)
    TextView speedTextView;

    public static SpeedAdjustmentPopupFragment instance(){
        SpeedAdjustmentPopupFragment popup =  new SpeedAdjustmentPopupFragment_();
        popup.setRetainInstance(true);
        popup.setCancelable(false);
        return popup;
    }

    @AfterViews
    protected void setTitle(){
        this.getDialog().setTitle("Speed Adjustment");
    }

    @Click(R.id.okButton)
    public void okButtonClicked(){

    }

    @Click(R.id.dismissButton)
    public void dismissButtonClicked(){
        this.dismiss();
    }

    @ViewById(R.id.rangebar)
    RangeBar rangeBar;

    @AfterViews
    public void initRangeBar(){
        rangeBar.setDisableLeftThumb(true);
    }
}
