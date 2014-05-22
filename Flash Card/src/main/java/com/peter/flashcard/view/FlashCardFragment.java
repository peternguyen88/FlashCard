package com.peter.flashcard.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
                updateFlashCard();
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
        Log.v("Check", "STOP THREAD!");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("Check", "START THREAD!");
        periodicalUpdate();
    }

    @UiThread
    public void updateFlashCard() {
        Word word = ContentProvider.nextWord();
        top_card_view.wordView.setText(word.getWord());
        bottom_card_view.wordView.setText(word.getDefinition());
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
        WordPopupFragment.instance().show(getFragmentManager(), WORD_POPUP_TAG);
    }

}
