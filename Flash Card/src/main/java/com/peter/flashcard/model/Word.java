package com.peter.flashcard.model;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Peter on 5/15/2014.
 */
@DatabaseTable(tableName = "WORD")
public class Word{

    private static Random random = new Random();

    @DatabaseField(columnName = "WORD")
    private String word;

    @DatabaseField(columnName = "LIST")
    private int list;

    List<String> filePaths = new ArrayList<String>();
    List<Definition> definitions = new ArrayList<Definition>();

    public void resetList(){
        filePaths = new ArrayList<String>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definitions.get(random.nextInt(definitions.size())).getDefinition();
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }
}
