package com.example.admin.wordtrainer20;

import java.util.ArrayList;
import java.util.List;

enum MarkExercise
{
    ENG_TO_RUS,             // Проверка русского слова
    RUS_TO_ENG,             // Проверка английского слова
    WRITING,                // Проверка английского слова
    TRUE_OR_FALSE           // Проверка русского слова
}


public class Exercise {

    private List<Word> WordList = new ArrayList<>();


    static private boolean ENG_RUS_CHOISE = true;   // Для Enable всего упражнения
    static private boolean RUS_ENG_CHOISE = true;
    static private boolean WRITING = true;
    static private boolean TRUE_OR_FALSE = true;

    public Exercise(){

    }

    public Exercise(List<Word> WordList){

        this.WordList = WordList;
    }

    // Генерация слова для TextView
    public Word getWordForTextView(){
        int RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);

        // Если на слово уже дали правильный ответ.
        if (WordList.get(RANDOM_INDEX).isCheck()) {
            // Генерировать до любого непройденного слова.
            while (WordList.get(RANDOM_INDEX).isCheck()) {
                RANDOM_INDEX = GetRandomIndexForListWord(0, WordList.size() - 1);
            }
        }

        Word word = WordList.get(RANDOM_INDEX);
        return word;
    }


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


    public int GetRandomIndexForListWord(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}
