package com.example.ogtrivia.Model;

public class Question {
    public String Query;
    public boolean isCorrect;

    public Question(String query, boolean isCorrect) {
        Query = query;
        this.isCorrect = isCorrect;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }


    @Override
    public String toString() {
        return "Question{" +
                "Query='" + Query + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
