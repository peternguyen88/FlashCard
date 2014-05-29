package com.peter.flashcard.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.peter.flashcard.R;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.model.Word;
import com.peter.flashcard.view.fragment.WordPopupFragment;

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
    private int sleepingTime = 2000; // Default Card will stop for 2 seconds

    private boolean isAutoRun;

    public void setSleepingTime(int sleepingTime) {
        this.sleepingTime = sleepingTime;
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        assetManager = activity.getAssets();
    }

    @Background(id="UPDATE_THREAD")
    public void periodicalUpdate() {
        try {
            while (true) {
                updateFlashCard(ContentProvider.nextWord());
                Thread.sleep(sleepingTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        BackgroundExecutor.cancelAll(UPDATE_THREAD,true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isAutoRun){
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
    public void autoRunClicked(){
        if(isAutoRun){
            isAutoRun = false;
            BackgroundExecutor.cancelAll(UPDATE_THREAD, true);
            autoPlayButton.setText("Play");
            autoPlayButton.setBootstrapType("primary");
            previousButton.setBootstrapButtonEnabled(true);
            nextButton.setBootstrapButtonEnabled(true);
        }
        else{
            isAutoRun = true;
            periodicalUpdate();
            autoPlayButton.setText("Stop");
            autoPlayButton.setBootstrapType("warning");
            previousButton.setBootstrapButtonEnabled(false);
            nextButton.setBootstrapButtonEnabled(false);
        }
    }

    public void updateFlashCard(){
        updateFlashCard(ContentProvider.currentWord());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateFlashCard(ContentProvider.currentWord());
    }

    @UiThread
    public void updateFlashCard(Word word) {
        top_card_view.wordView.setText(word.getWord());
        bottom_card_view.setWord(word);
        if(!word.getFilePaths().isEmpty()){
            try {
                String filePath = word.getFilePaths().get(random.nextInt(word.getFilePaths().size()));
                InputStream is = assetManager.open(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if(bitmap == null) Log.i("Empty Picture",filePath);
                imageView.setImageBitmap(bitmap);
                is.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            Log.i("Empty Picture",word.getWord());
            imageView.setImageBitmap(null);
        }
    }

    @Click(R.id.top_card_view)
    public void showPopup(){
        WordPopupFragment.instance(ContentProvider.currentWord()).show(getFragmentManager(), WORD_POPUP_TAG);
    }

    @Click(R.id.previousButton)
    public void previousWord(){
        updateFlashCard(ContentProvider.previousWord());
    }

    @Click(R.id.nextButton)
    public void nextWord(){
        updateFlashCard(ContentProvider.nextWord());
    }

}
