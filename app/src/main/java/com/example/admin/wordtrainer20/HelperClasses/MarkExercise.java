package com.example.admin.wordtrainer20.HelperClasses;

public enum MarkExercise
{
    ENG_TO_RUS ("EngToRus"),             // Проверка русского слова
    RUS_TO_ENG ("RusToEng"),             // Проверка английского слова
    WRITING ("Writing"),                // Проверка английского слова
    TRUE_OR_FALSE ("TrueFalse");           // Проверка русского слова

    private String fieldName;

    MarkExercise(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
