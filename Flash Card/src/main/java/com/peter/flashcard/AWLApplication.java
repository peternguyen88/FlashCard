package com.peter.flashcard;

import android.app.Application;

import com.cengalabs.flatui.FlatUI;
import com.google.common.eventbus.EventBus;
import com.j256.ormlite.dao.Dao;
import com.peter.flashcard.content.ResourceLoader;
import com.peter.flashcard.model.Definition;
import com.peter.flashcard.model.Word;
import com.peter.flashcard.persistence.DatabaseHelper;
import com.peter.flashcard.pref.SharedPrefs_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by Peter on 5/19/2014.
 */
@EApplication
public class AWLApplication extends Application {

    @Pref
    SharedPrefs_ prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        FlatUI.initDefaultValues(this);

        if (prefs.firstRun().get()) {
            new DatabaseHelper(this).getWritableDatabase().close();
            prefs.firstRun().put(false);
        }

        loadResources();
    }

    /**
     * An EventBus for Handling all Event in system without using Listener ^_^
     */
    public static final EventBus eventBus = new EventBus();

    @OrmLiteDao(helper = DatabaseHelper.class, model = Word.class)
    Dao<Word, String> wordDao;

    @OrmLiteDao(helper = DatabaseHelper.class, model = Definition.class)
    Dao<Definition, String> definitionDao;

    @Background
    protected void loadResources(){
        ResourceLoader.loadResources(this, wordDao, definitionDao);
    }
}
