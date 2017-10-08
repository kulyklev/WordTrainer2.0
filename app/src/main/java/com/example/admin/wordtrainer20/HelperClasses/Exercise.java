package com.example.admin.wordtrainer20.HelperClasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public List<Word> getWordList() {
        return WordList;
    }

    public void setWordList(List<Word> wordList) {
        WordList = wordList;
    }

    // Генерация слова из списка для тренировки

    public Word getWordForTextView(MarkExercise markTypeTraining, SQLiteDatabase mDb){
        int RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);

        Word temp = WordList.get(RANDOM_INDEX);
        String nameFieldCheckTraining = getStringField(markTypeTraining);

        if (isStudiedTrainingsWord(temp.getId(), nameFieldCheckTraining, mDb)) {
            // Генерировать до любого непройденного слова.
            while (isStudiedTrainingsWord(temp.getId(), nameFieldCheckTraining, mDb)) {
                RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);
                temp = WordList.get(RANDOM_INDEX);
            }
        }

        return temp;
    }

    public Boolean isStudiedTrainingsWord(int id, String field, SQLiteDatabase mDb){ //field - название тренировки
        Cursor cursor = mDb.rawQuery("SELECT * FROM trainings WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean t = cursor.getInt(cursor.getColumnIndex(field)) == 1;
        cursor.close();
        return t;
    }

    /* НУЖНА ЛИ?
    public Boolean isTrainingOff(MarkExercise mark, SQLiteDatabase mDb){

        boolean off = false; // Они не изучены
        String field = getStringField(mark);
        for (Word w: this.WordList) {
            if (!isStudiedTrainingsWord(w.getId(), field, mDb)) // Если слово не выучено, вернуть false
            {
                off = true; // Если хотя бы одно не изучено, тогда продолжить упражнение.
                break;
            }
        }
        return off;
    }
    */

    private String getStringField(MarkExercise mark) {
        String s = "";
        if (mark == MarkExercise.ENG_TO_RUS)
            s = "EngtoRus"; // Тоже выбор, но кривой
        else if (mark == MarkExercise.ENG_TO_RUS)
            s =  "Choice";
        else if (mark == MarkExercise.TRUE_OR_FALSE)
            s = "TrueFalse";
        else if (mark == MarkExercise.WRITING)
            s = "Writing";

        return s;
    }

    public void setWord(long id, long valueTrueFalse, SQLiteDatabase mDb, MarkExercise mark){
        String field = getStringField(mark);
        Cursor cursor = mDb.rawQuery("UPDATE trainings" +
                " SET " + field + "='" + valueTrueFalse +"'"+" WHERE _id='" + id + "'",null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void removeWordInList(Word word){
        this.WordList.remove(word);
    }


    public int getIdByEnglish(String english, SQLiteDatabase mDb){

        String copyEnglish = english.replaceAll("'", "''");

        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE English='"+ copyEnglish + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
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

