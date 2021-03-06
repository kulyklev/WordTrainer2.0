package com.example.admin.wordtrainer20.HelperClasses;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

public class Word implements Serializable {
    private int id;
    private String englishWord;
    private String russianWord;

    public Word(){
        englishWord ="";
        russianWord ="";
    }

    public Word(int id, String englishWord, String translateWord){
        this.id = id;
        this.englishWord = englishWord;
        this.russianWord = translateWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
    }

    public boolean checkWord(String UserTranslate, MarkExercise NameExercise){
        UserTranslate = UserTranslate.toLowerCase();
        this.englishWord = this.englishWord.toLowerCase();
        this.russianWord = this.russianWord.toLowerCase();
        if ((NameExercise == MarkExercise.RUS_TO_ENG) || (NameExercise == MarkExercise.WRITING))
        {
            if (this.englishWord.equals(UserTranslate))
                return true;
            else
                return false;
        }
        else
        {
            if (this.russianWord.equals(UserTranslate))
                return true;
            else
                return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;
        ((Word) o).englishWord.toLowerCase();
        ((Word) o).russianWord.toLowerCase();

        if (!englishWord.equals(word.englishWord)) return false;
        return russianWord.equals(word.russianWord);
    }

    @Override
    public int hashCode() {
        int result = englishWord.hashCode();
        result = 31 * result + russianWord.hashCode();
        return result;
    }

    public boolean checkComplete(SQLiteDatabase mDb){
        Cursor cursor = mDb.rawQuery("SELECT * FROM trainings WHERE _id='"+ this.id + "'", null);
        cursor.moveToFirst();
        Boolean t = cursor.getInt(cursor.getColumnIndex("Complete")) == 1;
        cursor.close();
        return t;
    }

}
