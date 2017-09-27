package com.example.admin.wordtrainer20;

import java.util.List;

/**
 * Created by dell on 27.09.2017.
 */

public class Vocabulary {
    private List<Word> words;

    public Vocabulary(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
