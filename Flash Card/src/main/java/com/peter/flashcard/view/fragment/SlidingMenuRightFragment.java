package com.peter.flashcard.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.peter.flashcard.AWLApplication;
import com.peter.flashcard.R;
import com.peter.flashcard.adapter.WordListAdapter;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.event.RightSlidingMenuItemSelectEvent;
import com.peter.flashcard.model.Word;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/31/2014.
 */
@EFragment(R.layout.fragment_sliding_menu_right)
public class SlidingMenuRightFragment extends Fragment{

    @Bean
    WordListAdapter wordListAdapter;

    @ViewById(R.id.wordListListView)
    ListView wordListView;

    @ViewById(R.id.search_view)
    TextView search_view;

    @AfterViews
    protected void bindAdapter(){
        wordListView.setAdapter(wordListAdapter);
    }

    @TextChange(R.id.search_view)
    protected void filterWordList(TextView tv, CharSequence text){
        wordListAdapter.getFilter().filter(text);
    }

    @ItemClick(R.id.wordListListView)
    protected void wordListItemClicked(int position) {
        Word selectedWord = wordListAdapter.getItem(position);
        ContentProvider.setCurrentWord(selectedWord);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search_view.getWindowToken(),0);
        AWLApplication.eventBus.post(new RightSlidingMenuItemSelectEvent(selectedWord));
    }
}
