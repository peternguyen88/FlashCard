package com.peter.flashcard.model;

import android.graphics.Color;
import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.peter.flashcard.R;
import com.peter.flashcard.constant.ColorConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 5/20/2014.
 */
@DatabaseTable(tableName = "DEFINITION")
public class Definition {

    @DatabaseField(columnName = "WORD")
    private String word;

    @DatabaseField(columnName = "TYPE")
    private String type;

    @DatabaseField(columnName = "DEFINITION")
    private String definition;

    public String getWord() {
        return word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getColor(){
        return ColorConstant.colorMap.get(type);
    }
}
