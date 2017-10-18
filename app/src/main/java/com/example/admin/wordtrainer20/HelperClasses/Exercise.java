package com.example.admin.wordtrainer20.HelperClasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InterruptedIOException;
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

     // Изучено ли слово на данной тренировке

    public Boolean isStudiedTrainingsWord(int id, String field, SQLiteDatabase mDb)
    { //field - название тренировки
        Cursor cursor = mDb.rawQuery("SELECT * FROM trainings WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean t = cursor.getInt(cursor.getColumnIndex(field)) == 1;
        cursor.close();
        return t;
    }


    // Выучены ли все слова на данной тренировке
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


    // Получения поля для обновления базы

    private String getStringField(MarkExercise mark) {
        String s = "";
        if (mark == MarkExercise.ENG_TO_RUS)
            s = "EngToRus"; // Тоже выбор, но кривой
        else if (mark == MarkExercise.RUS_TO_ENG)
            s =  "Choice";
        else if (mark == MarkExercise.TRUE_OR_FALSE)
            s = "TrueFalse";
        else if (mark == MarkExercise.WRITING)
            s = "Writing";

        return s;
    }


    // Установить слово изученым или нет
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

    public void insertWordInList(Word word) { WordList.add(word); }

    // Если пользователю сгенерировало набор , где есть слова , которые прошли данную тренировку, тогда
    // с помощью функции enableStudiedWords перемещаем их в дополнительный массив.


    public List<Word> enableStudiedWords(MarkExercise mark, SQLiteDatabase mDb){
        List<Word> copy = new ArrayList<Word>();
        String field = getStringField(mark);
        for (Word w: this.WordList) {
            if (isStudiedTrainingsWord(w.getId(), field, mDb)) // Если слово не выучено, вернуть false
                copy.add(w);
        }
        return copy;
    }


    // Генерация списка слов для выбора


    public List<Word> getListChoice(SQLiteDatabase mDb, int countRandom) throws IOException
    {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words ORDER BY RANDOM() LIMIT " + Integer.toString(countRandom);
        Cursor cursor = mDb.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                Word word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));;
                result.add(word);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }


    public int GetRandomIndexForListWord(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }


}






   /*

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

