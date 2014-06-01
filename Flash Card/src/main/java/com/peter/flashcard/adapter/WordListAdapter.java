package com.peter.flashcard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.peter.flashcard.AWLApplication;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.event.SlidingMenuItemSelectEvent;
import com.peter.flashcard.model.Word;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 5/31/2014.
 */

@EBean
public class WordListAdapter extends BaseAdapter implements Filterable{

    private static final String TAG = WordListAdapter.class.getSimpleName();

    @RootContext
    Context context;

    List<Word> wordList = new ArrayList<Word>();

    @AfterInject
    public void init(){
        Log.i(TAG,"Init Word List");
        wordList = ContentProvider.getWordList();
        AWLApplication.eventBus.register(this);
    }

    @Subscribe
    public void handleWordListChanged(SlidingMenuItemSelectEvent event){
        if(event.isLeftMenu())
            updateWordList();
    }

    public void updateWordList(){
        this.wordList = ContentProvider.getWordList();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Word getItem(int position) {
        return wordList.get(position);
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
            textView.setPadding(20,0,0,0);
            textView.setTextSize(20);
            textView.setTextColor(Color.WHITE);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(this.wordList.get(position).getWord());
        return textView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Word> filteredWords = new ArrayList<Word>();

                for(Word word : ContentProvider.getWordList()){
                    if(StringUtils.startsWith(word.getWord(),constraint)){
                        filteredWords.add(word);
                    }
                }

                results.count = filteredWords.size();
                results.values = filteredWords;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                wordList = (List<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
