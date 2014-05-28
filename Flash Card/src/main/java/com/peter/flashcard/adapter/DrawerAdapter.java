package com.peter.flashcard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.view.SlidingMenuItem;
import com.peter.flashcard.view.SlidingMenuItem_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 5/25/2014.
 */
@EBean
public class DrawerAdapter extends BaseAdapter{

    @RootContext
    Context context;

    List<String> wordLists;

    @AfterInject
    public void initAdapter(){
        wordLists = new ArrayList<String>();
        wordLists.add("All Words");
        for(Integer i : ContentProvider.getAwlMap().keySet()){
            wordLists.add("List " + i);
        }
    }

    @Override
    public int getCount() {
        return wordLists.size();
    }

    @Override
    public String getItem(int position) {
        return wordLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SlidingMenuItem slidingMenuItem;

        if (convertView == null) {
            slidingMenuItem = SlidingMenuItem_.build(context);
            bindTextView(slidingMenuItem,position);
        } else {
            slidingMenuItem = (SlidingMenuItem) convertView;
        }

        return slidingMenuItem;
    }

    private void bindTextView(SlidingMenuItem sliding, int position){
        sliding.setText(wordLists.get(position));
    }
}
