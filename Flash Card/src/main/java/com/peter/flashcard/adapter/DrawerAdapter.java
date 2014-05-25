package com.peter.flashcard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peter.flashcard.content.ContentProvider;

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

    List<Integer> wordLists;

    @AfterInject
    public void initAdapter(){
        wordLists = new ArrayList<Integer>(ContentProvider.getAwlMap().keySet());
    }

    @Override
    public int getCount() {
        return wordLists.size();
    }

    @Override
    public Integer getItem(int position) {
        return wordLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
            bindTextView(textView,position);
        } else {
            textView = (TextView) convertView;
        }

        return textView;
    }

    private void bindTextView(TextView textView, int position){
        textView.setText("List " + wordLists.get(position));
    }
}
