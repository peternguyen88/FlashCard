package com.peter.flashcard.content;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.j256.ormlite.dao.Dao;
import com.peter.flashcard.model.Definition;
import com.peter.flashcard.model.Word;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Peter on 6/9/2014.
 */
public class ResourceLoader {
    public static void loadResources(Context context, Dao<Word, String> wordDao, Dao<Definition, String> definitionDao) {
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
            ContentProvider.setFullWordList(wordList);

            loadImages(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadImages(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
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
