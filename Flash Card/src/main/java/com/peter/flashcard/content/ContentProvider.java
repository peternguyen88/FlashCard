package com.peter.flashcard.content;

import com.peter.flashcard.model.Word;

import java.util.List;

/**
 * Created by Peter on 5/13/2014.
 */
public class ContentProvider {
    static String[] words = {"analyse","approach","assess","assume"};
    static String[] meanings = {"to study or examine something in detail, in order to discover more about it","1) a way of dealing with something\n" +
            "2) ideas or actions intended to deal with a problem or situation","to judge how good, bad or important something is","to think that something is true without knowing all the facts"};

    static int currentIndex = 0;

    private static List<Word> wordList;

    public static Word nextWord(){
        if(currentIndex == wordList.size()) currentIndex = 0;
        return wordList.get(currentIndex++);
    }

    public static List<Word> getWordList() {
        return wordList;
    }

    public static void setWordList(List<Word> wordList) {
        ContentProvider.wordList = wordList;
    }
}
