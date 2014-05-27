package com.peter.flashcard.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.peter.flashcard.R;
import com.peter.flashcard.adapter.DrawerAdapter;

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
        Toast.makeText(getActivity(),position + " clicked " , Toast.LENGTH_SHORT).show();
    }
}
