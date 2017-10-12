package com.example.admin.wordtrainer20.AdapterFolder;

/**
 * Created by 2andr on 07.10.2017.
 */

public class Model {
    String category;
    boolean isStudied;

    public Model(String category, boolean isStudied) {
        this.category = category;
        this.isStudied = isStudied;
    }

    public String getCategory() {
        return category;
    }

    public boolean isStudied() {
        return isStudied;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStudied(boolean studied) {
        isStudied = studied;
    }
}
