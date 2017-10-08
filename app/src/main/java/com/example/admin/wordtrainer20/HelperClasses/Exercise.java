package com.example.admin.wordtrainer20.HelperClasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.*;


public class Exercise {

    private List<Word> WordList = new ArrayList<>();

    static private boolean ENG_RUS_CHOISE = true;   // Для Enable всего упражнения
    static private boolean RUS_ENG_CHOISE = true;
    static private boolean WRITING = true;
    static private boolean TRUE_OR_FALSE = true;

    public Exercise() {

    }

    public Exercise(List<Word> WordList) {
        this.WordList = WordList;
    }

    public Word getWordForTextView(){
        int RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);

        // Если на слово уже дали правильный ответ.
        if (WordList.get(RANDOM_INDEX).isStudiedTrainings)) {

            // Генерировать до любого непройденного слова.

            while (WordList.get(RANDOM_INDEX).isCheck()) {
                RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);
            }
        }

        Word word = WordList.get(RANDOM_INDEX);
        return word;
    }


    public int getIdByEnglish(String english, SQLiteDatabase mDb){

        String copyEnglish = english.replaceAll("'", "''");

        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE English='"+ copyEnglish + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
    }

    public Boolean isStudiedTrainings(String field, int id, SQLiteDatabase mDb){ //fiekd - название тренировки
        Cursor cursor = mDb.rawQuery("SELECT * FROM trainings WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean t = cursor.getInt(cursor.getColumnIndex(field))==1;
        cursor.close();
        return t;
    }

/*
    public Boolean getIsSelected(int category){
        Cursor cursor = mDb.rawQuery("SELECT * FROM vocabulary WHERE _id='"+ category + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isSelected")) == 1;
        cursor.close();
        return i;
    }

    public Boolean getIsStudied(int id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM study WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isStudied")) == 1;
        cursor.close();
        return i;
    }
*/





    public void setWord(long id, long val, SQLiteDatabase mDb){
        Cursor cursor = mDb.rawQuery("UPDATE trainings" +
                " SET Choice='" + val +"'"+" WHERE _id='" + id + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }


    public int GetRandomIndexForListWord(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}






   /*
    public List<Word> getListChoice(SQLiteDatabase mDb) throws IOException
    {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words";
        Cursor cursor = mDb.rawQuery(query, null);

        int random = (int)(Math.random()*30)+21;
        int i=0;

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                if (i%random==0) {
                    Word word = new Word();
                    word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                    word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));;
                    result.add(word);
                }
                i++;
            } while (cursor.moveToNext() && result.size()< 4);
        }
        cursor.close();
        return result;
    }
    */











    /*

    // Генерация слова для TextView



    // Список альтернатив
    public List<String> getListChoise(MarkExercise NameExercise, Word WordIntTextView) {

        List<String> choise = new ArrayList<String>();

        if ((MarkExercise.ENG_TO_RUS == NameExercise) || (MarkExercise.TRUE_OR_FALSE == NameExercise))
            choise.add(WordIntTextView.getEnglishWord());
        else
            choise.add(WordIntTextView.getRussianWord());

        while (choise.size() < 5) {

            int index = GetRandomIndexForListWord(0, WordList.size() - 1);

            if ((MarkExercise.ENG_TO_RUS == NameExercise) || (MarkExercise.TRUE_OR_FALSE == NameExercise))
            {
                if (!choise.contains(WordList.get(index).getEnglishWord()))
                    choise.add(WordList.get(index).getEnglishWord());
            }
            else
            {
                if (!choise.contains(WordList.get(index).getRussianWord()))
                    choise.add(WordList.get(index).getRussianWord());
            }
        }
        return choise;
    }

    */

