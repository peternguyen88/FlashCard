package com.peter.flashcard;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;

import com.cengalabs.flatui.FlatUI;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.j256.ormlite.dao.Dao;
import com.peter.flashcard.content.ContentProvider;
import com.peter.flashcard.model.Definition;
import com.peter.flashcard.model.Word;
import com.peter.flashcard.persistence.DatabaseHelper;
import com.peter.flashcard.pref.SharedPrefs_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@EActivity(R.layout.activity_setting)
@OptionsMenu(R.menu.setting)
public class SettingActivity extends ActionBarActivity {

    @Pref
    SharedPrefs_ prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (prefs.firstRun().get()) {
            new DatabaseHelper(this).getWritableDatabase().close();
            prefs.firstRun().put(false);
        }

        loadWords();
        FlatUI.setDefaultTheme(FlatUI.SEA);
    }

    @ViewById(R.id.seekBar)
    public SeekBar seekBar;

    @Click(R.id.startButton)
    public void startButtonClick() {
        FlashCard_.intent(this).sleepingTime(convertSeekBarToSleepingTime()).start();
    }

    private int convertSeekBarToSleepingTime() {
        int progress = seekBar.getProgress();
        if (progress == 100) progress = 99;
        return (int) ((100 - progress) / 100.0 * (10000));
    }

    @OrmLiteDao(helper = DatabaseHelper.class, model = Word.class)
    Dao<Word, String> wordDao;

    @OrmLiteDao(helper = DatabaseHelper.class, model = Definition.class)
    Dao<Definition, String> definitionDao;

    private void loadWords() {
        try {
            List<Word> wordList = wordDao.queryBuilder().orderBy("LIST", true).orderBy("WORD", true).query();
            List<Definition> definitionList = definitionDao.queryForAll();

            ListMultimap<String, Definition> wordDefinitionMap = ArrayListMultimap.create();
            ListMultimap<Integer, Word> awlMap = ArrayListMultimap.create();

            for (Definition definition : definitionList) {
                wordDefinitionMap.put(definition.getWord(), definition);
            }

            for (Word word : wordList) {
                word.setDefinitions(wordDefinitionMap.get(word.getWord()));
                awlMap.put(word.getList(), word);
            }

            ContentProvider.setAwlMap(awlMap);

            ContentProvider.setWordList(wordList);
            loadImages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImages() throws IOException {
        AssetManager assetManager = getAssets();
        List<String> imagesList = Arrays.asList(assetManager.list("images"));

        for (Word word : ContentProvider.getWordList()) {
            word.resetList();
            String fileName = word.getWord() + ".jpg";
            if (imagesList.contains(fileName)) {
                word.getFilePaths().add("images/" + fileName);
            }
        }
    }
}
