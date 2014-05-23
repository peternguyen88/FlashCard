package com.peter.flashcard.content;

import com.google.common.collect.ListMultimap;
import com.peter.flashcard.model.Word;

import java.util.List;

/**
 * Created by Peter on 5/13/2014.
 */
public class ContentProvider {
    public static int NUMBER_OF_WORD_LIST;

    static int currentIndex = 0;

    private static List<Word> wordList;
    private static ListMultimap<Integer,Word> awlMap;

    public static Word nextWord(){
        currentIndex++;
        if(currentIndex == wordList.size()) currentIndex = 0;
        return wordList.get(currentIndex);
    }

    public static Word previousWord(){
        currentIndex--;
        if(currentIndex == -1) currentIndex = wordList.size()-1;
        return wordList.get(currentIndex);
    }

    public static Word currentWord(){
        return wordList.get(currentIndex);
    }

    public static List<Word> getWordList() {
        return wordList;
    }

    public static void setWordList(List<Word> wordList) {
        ContentProvider.wordList = wordList;
    }

    public static void setNUMBER_OF_WORD_LIST(int NUMBER_OF_WORD_LIST) {
        ContentProvider.NUMBER_OF_WORD_LIST = NUMBER_OF_WORD_LIST;
    }

    public static ListMultimap<Integer, Word> getAwlMap() {
        return awlMap;
    }

    public static void setAwlMap(ListMultimap<Integer, Word> awlMap) {
        ContentProvider.awlMap = awlMap;
    }
}
