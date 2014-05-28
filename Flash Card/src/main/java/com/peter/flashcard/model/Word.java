package com.peter.flashcard.model;

import android.graphics.Bitmap;

import com.google.common.collect.ComparisonChain;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Peter on 5/15/2014.
 */
@DatabaseTable(tableName = "WORD")
public class Word implements Comparable<Word> {

    private static Random random = new Random();

    @DatabaseField(columnName = "WORD")
    private String word;

    @DatabaseField(columnName = "LIST")
    private int list;

    List<String> filePaths = new ArrayList<String>();
    List<Definition> definitions = new ArrayList<Definition>();

    private int currentDefinitionIndex;

    public void resetList() {
        filePaths = new ArrayList<String>();
    }

    public int getList() {
        return list;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinitionText() {
        currentDefinitionIndex = random.nextInt(definitions.size());
        return definitions.get(currentDefinitionIndex).getDefinition();
    }

    public String nextDefinitionText() {
        currentDefinitionIndex++;
        if (currentDefinitionIndex == definitions.size()) currentDefinitionIndex = 0;
        return definitions.get(currentDefinitionIndex).getDefinition();
    }

    public Definition getRandomDefinition() {
        currentDefinitionIndex = random.nextInt(definitions.size());
        return definitions.get(currentDefinitionIndex);
    }

    public Definition getCurrentDefinition() {
        return definitions.get(currentDefinitionIndex);
    }

    public Definition nextDefinition() {
        currentDefinitionIndex++;
        if (currentDefinitionIndex == definitions.size()) currentDefinitionIndex = 0;
        return definitions.get(currentDefinitionIndex);
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

    public String getDefinitionOrder() {
        return (currentDefinitionIndex + 1) + "/" + (definitions.size());
    }

    @Override
    public int compareTo(Word another) {
        return ComparisonChain.start().compare(this.list, another.list)
                .compare(this.word, another.word).result();
    }
}
