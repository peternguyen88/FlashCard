package com.peter.flashcard.view.fragment;

import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.peter.flashcard.AWLApplication;
import com.peter.flashcard.R;
import com.peter.flashcard.event.AutoPlayInterruptEvent;
import com.peter.flashcard.event.SpeedChangeEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/29/2014.
 */
@EFragment(R.layout.fragment_speed_adjustment)
public class SpeedAdjustmentPopupFragment extends DialogFragment{

    int currentSpeed = 5;

    public static SpeedAdjustmentPopupFragment instance(){
        SpeedAdjustmentPopupFragment popup =  new SpeedAdjustmentPopupFragment_();
        popup.setRetainInstance(true);
        popup.setCancelable(false);
        return popup;
    }

    public SpeedAdjustmentPopupFragment setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
        return this;
    }

    @AfterViews
    protected void setTitle(){
        this.getDialog().setTitle("Speed Adjustment");
    }

    @Click(R.id.okButton)
    public void okButtonClicked(){
        AWLApplication.eventBus.post(new SpeedChangeEvent(rangeBar.getRightIndex()-rangeBar.getLeftIndex()+1));
        AWLApplication.eventBus.post(new AutoPlayInterruptEvent(AutoPlayInterruptEvent.Mode.MODE_RESUME));
        this.dismiss();
    }

    @Click(R.id.dismissButton)
    public void dismissButtonClicked(){
        // If Flash Card is Auto Playing, then we will resume the action
        AWLApplication.eventBus.post(new AutoPlayInterruptEvent(AutoPlayInterruptEvent.Mode.MODE_RESUME));
        this.dismiss();
    }

    @ViewById(R.id.rangebar)
    RangeBar rangeBar;

    @ViewById(R.id.speedTextView)
    TextView speedTextView;

    @AfterViews
    public void initRangeBar(){
        rangeBar.setThumbIndices(0,currentSpeed);
        speedTextView.setText((rangeBar.getRightIndex()-rangeBar.getLeftIndex()+1)+"");

        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                speedTextView.setText((rightThumbIndex-leftThumbIndex+1)+"");
            }
        });
    }
}
