package com.peter.flashcard;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.peter.flashcard.adapter.DrawerAdapter;
import com.peter.flashcard.view.FlashCardFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_flash_card)
@OptionsMenu(R.menu.flash_card)
public class FlashCard extends Activity {

    @FragmentById
    FlashCardFragment flashCardFragment;

    @Bean
    DrawerAdapter drawerAdapter;

    @ViewById(R.id.left_drawer)
    ListView drawerList;

    @Extra
    int sleepingTime;

    @AfterViews
    public void afterView(){
        drawerList.setAdapter(drawerAdapter);
    }

    @ItemClick(R.id.left_drawer)
    void listItemClicked(Integer list) {
        Toast.makeText(this, list, Toast.LENGTH_LONG).show();
    }
}
