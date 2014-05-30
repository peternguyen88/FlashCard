package com.peter.flashcard.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.common.eventbus.Subscribe;
import com.peter.flashcard.AWLApplication;
import com.peter.flashcard.R;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.event.AutoPlayInterruptEvent;
import com.peter.flashcard.event.SpeedChangeEvent;
import com.peter.flashcard.model.Word;
import com.peter.flashcard.view.fragment.WordPopupFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@EFragment(R.layout.fragment_flash_card)
public class FlashCardFragment extends Fragment {

    public static final String UPDATE_THREAD = "UPDATE_THREAD";
    public static final String WORD_POPUP_TAG = "WORD_POPUP";
    public static final int MILLISECOND_IN_ONE_SECOND = 1000;
    private int sleepingTime = 2000; // Default Card will stop for 2 seconds

    private boolean isAutoRun;

    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
    }

    public int getSleepingTime() {
        return sleepingTime / MILLISECOND_IN_ONE_SECOND;
    }

    @ViewById
    TopCardView top_card_view;

    @ViewById
    BottomCardView bottom_card_view;

    @ViewById
    ImageView imageView;

    Random random = new Random();
    AssetManager assetManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        AWLApplication.eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        AWLApplication.eventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        assetManager = activity.getAssets();
    }

    @Background(id = "UPDATE_THREAD")
    public void periodicalUpdate() {
        try {
            while (true) {
                Thread.sleep(sleepingTime);
                updateFlashCard(ContentProvider.nextWord());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopAutoPlaying();
    }

    private void stopAutoPlaying() {
        BackgroundExecutor.cancelAll(UPDATE_THREAD, true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isAutoRun) {
            periodicalUpdate();
        }
        updateFlashCard();
    }

    @ViewById(R.id.autoPlayButton)
    BootstrapButton autoPlayButton;

    @ViewById(R.id.previousButton)
    BootstrapButton previousButton;

    @ViewById(R.id.nextButton)
    BootstrapButton nextButton;

    @Click(R.id.autoPlayButton)
    public void autoRunClicked() {
        if (isAutoRun) {
            isAutoRun = false;
            BackgroundExecutor.cancelAll(UPDATE_THREAD, true);
        } else {
            isAutoRun = true;
            periodicalUpdate();
        }
        updatePlayButtons();
    }

    @AfterViews
    protected void updatePlayButtons() {
        if (isAutoRun) {
            autoPlayButton.setText("Stop");
            autoPlayButton.setBootstrapType("warning");
            previousButton.setBootstrapButtonEnabled(false);
            nextButton.setBootstrapButtonEnabled(false);
        } else {
            autoPlayButton.setText("Play");
            autoPlayButton.setBootstrapType("primary");
            previousButton.setBootstrapButtonEnabled(true);
            nextButton.setBootstrapButtonEnabled(true);
        }
    }

    public void updateFlashCard() {
        updateFlashCard(ContentProvider.currentWord());
    }

    @UiThread
    public void updateFlashCard(Word word) {
        top_card_view.wordView.setText(word.getWord());
        bottom_card_view.setWord(word);
        if (!word.getFilePaths().isEmpty()) {
            try {
                String filePath = word.getFilePaths().get(random.nextInt(word.getFilePaths().size()));
                InputStream is = assetManager.open(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap == null) Log.i("Empty Picture", filePath);
                imageView.setImageBitmap(bitmap);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Empty Picture", word.getWord());
            imageView.setImageBitmap(null);
        }
    }

    @Click(R.id.top_card_view)
    public void showPopup() {
        WordPopupFragment.instance(ContentProvider.currentWord()).show(getFragmentManager(), WORD_POPUP_TAG);
    }

    @Click(R.id.previousButton)
    public void previousWord() {
        updateFlashCard(ContentProvider.previousWord());
    }

    @Click(R.id.nextButton)
    public void nextWord() {
        updateFlashCard(ContentProvider.nextWord());
    }

    /*
     * Handle Speed Change Event
     */
    @Subscribe
    public void handleSpeedChangeEvent(SpeedChangeEvent event) {
        this.setSleepingTime(event.getSpeed() * MILLISECOND_IN_ONE_SECOND);
    }

    @Subscribe
    public void handleAutoPlayInterrupEvent(AutoPlayInterruptEvent event) {
        if (event.getMode() == AutoPlayInterruptEvent.Mode.MODE_INTERRUP) {
            stopAutoPlaying();
        }
        if (event.getMode() == AutoPlayInterruptEvent.Mode.MODE_RESUME) {
            if (isAutoRun) {
                periodicalUpdate();
            }
        }
    }
}
