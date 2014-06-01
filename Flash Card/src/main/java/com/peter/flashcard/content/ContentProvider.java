package com.peter.flashcard.content;

import com.google.common.collect.Collections2;
import com.google.common.collect.ListMultimap;
import com.peter.flashcard.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 5/13/2014.
 */
public class ContentProvider {
    public static int NUMBER_OF_WORD_LIST;

    static int currentIndex = 0;

    private static List<Word> wordList = new ArrayList<Word>();
    private static List<Word> fullWordList = new ArrayList<Word>();
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

    public static void setCurrentWord(Word word){
        currentIndex = wordList.indexOf(word);
    }

    public static List<Word> getWordList() {
        return wordList;
    }

    public static void setWordList(List<Word> wordList) {
        ContentProvider.wordList = wordList;
        currentIndex = 0;
    }

    public static List<Word> getFullWordList() {
        return fullWordList;
    }

    public static void setFullWordList(List<Word> fullWordList) {
        ContentProvider.fullWordList = fullWordList;
    }

    public static void setNUMBER_OF_WORD_LIST(int NUMBER_OF_WORD_LIST) {
        ContentProvider.NUMBER_OF_WORD_LIST = NUMBER_OF_WORD_LIST;
    }

    public static ListMultimap<Integer, Word> getAwlMap() {
        return awlMap;
    }

    public static void setAwlMap(ListMultimap<Integer, Word> awlMap) {
        ContentProvider.setNUMBER_OF_WORD_LIST(awlMap.keySet().size());
        ContentProvider.awlMap = awlMap;
    }

    public static void changeCurrentList(List<Word> words){
        wordList = words;
        currentIndex = 0;
    }

    public static void shuffleList(){
        Collections.shuffle(wordList);
    }

    public static void order(boolean asc){
        Collections.sort(wordList);
        if(!asc){
            Collections.reverse(wordList);
        }
    }

    public static void order(){
        order(true);
    }
}
