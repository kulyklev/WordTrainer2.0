package com.example.admin.wordtrainer20;

public class Word {
    private String englishWord;
    private String russianWord;
    private boolean check = false; // Временное поле , пока нет базы и проверки полей Trainings

    public Word(){
        englishWord ="";
        russianWord ="";
    }

    public Word(String englishWord, String translateWord){
        this.englishWord = englishWord;
        this.russianWord = translateWord;
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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean checkWorld(String UserTranslate, MarkExercise NameExercise){
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
}
