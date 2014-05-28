package com.peter.flashcard.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.peter.flashcard.AWLApplication;
import com.peter.flashcard.R;
import com.peter.flashcard.adapter.DrawerAdapter;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.event.SlidingMenuItemSelectEvent;
import com.peter.flashcard.view.FlashCardFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Peter on 5/28/2014.
 */
@EFragment(R.layout.fragment_sliding_menu)
public class SlidingMenuFragment extends Fragment {

    public static final int SELECT_ALL_WORD_POSITION = 0;

    @Bean
    DrawerAdapter drawerAdapter;

    @ViewById(R.id.wordListListView)
    ListView awlListView;

    @AfterViews
    public void bindAdapter(){
        awlListView.setAdapter(drawerAdapter);
    }

    @ItemClick(R.id.wordListListView)
    void wordListItemClicked(int position) {
        if(position==SELECT_ALL_WORD_POSITION){
            ContentProvider.setWordList(ContentProvider.getFullWordList());
        }
        else {
            ContentProvider.setWordList(ContentProvider.getAwlMap().get(position));
        }

        FlashCardFragment flashCardFragment =
                (FlashCardFragment) getFragmentManager().findFragmentById(R.id.flashCardFragment);
        if(flashCardFragment!=null){
            flashCardFragment.updateFlashCard();
        }

        AWLApplication.eventBus.post(new SlidingMenuItemSelectEvent());
    }
}
