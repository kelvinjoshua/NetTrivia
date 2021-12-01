package com.example.ogtrivia.Data;

import com.example.ogtrivia.Model.Question;

import java.util.ArrayList;

public interface AsyncSignal {
    void processFinished(ArrayList<Question> questionArrayList);
}
