package com.example.ogtrivia.Data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ogtrivia.Controller.Singleton;
import com.example.ogtrivia.Model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    //store our questions
    ArrayList<Question> questionArrayList = new ArrayList();
    String Url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    JsonArrayRequest jsonArrayRequest;
    public  final String TAG = this.getClass().getSimpleName();

    //getQuestions from url
    //implement async interface to handle asynchronous communication
    public List<Question> getQuestion(final AsyncSignal callback){
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        //get question
                        String quest = String.valueOf(response.getJSONArray(i).getString(0));
                        // get Answer
                        Boolean ans = response.getJSONArray(i).getBoolean(1);

                        //Create Question object
                        Question question = new Question(quest,ans);
                        //add Question
                        questionArrayList.add(question);
                        if(callback != null) callback.processFinished(questionArrayList);
                       // int arrLen = questionArrayList.size();
                       // Log.d(TAG,String.valueOf(arrLen));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
       Singleton.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;

    }



}


