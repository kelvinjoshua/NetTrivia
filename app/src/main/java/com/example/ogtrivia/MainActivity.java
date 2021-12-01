package com.example.ogtrivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ogtrivia.Controller.Singleton;
import com.example.ogtrivia.Data.AsyncSignal;
import com.example.ogtrivia.Data.Repository;
import com.example.ogtrivia.Model.Question;
import com.example.ogtrivia.Util.Preferences;
import com.example.ogtrivia.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.example.ogtrivia.R.color.red;
import static com.example.ogtrivia.R.color.white;
import static com.example.ogtrivia.R.layout.activity_main;

//This is Our view class
public class MainActivity extends AppCompatActivity {
   // private static final String HIGHSCORE = "Player Scores";
    private ActivityMainBinding binding;
    public  final String TAG = MainActivity.class.getSimpleName();
    public int current = 0;
    public List<Question> all;
    public int questSize;
    private Preferences preferences;
    public int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, activity_main);
        binding.next.setOnClickListener(this::next);
        binding.trueButton.setOnClickListener(v -> { checkAnswer(true);
        });
        binding.falseButton.setOnClickListener(v -> { checkAnswer(false);
        });
         all = new Repository().getQuestion(new AsyncSignal() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                updateCounter(questionArrayList);
                questSize= all.size();
               // Log.d("taggg", String.valueOf(questSize));
            }
        });
         preferences = new Preferences(this);
         //retrieve last state.
         current = preferences.getState();


    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.questionCount.setText(String.valueOf("Question : " + current + "/" + questSize));
    }

    private void checkAnswer(boolean correct) {
        Boolean check = all.get(current).isCorrect();

        if(check == correct){
            Snackbar.make(binding.card, R.string.correct,Snackbar.LENGTH_LONG).show();
            update();
            Fade();
            addScore();
        }
        else {
            Snackbar.make(binding.card, R.string.wrong,Snackbar.LENGTH_LONG).show();
            update();
            shake();
            deduct();
        }
    }

    private void deduct() {
        if(score > 0){
            score-=100;
            binding.score.setText(String.format("Your score is %d", score));
        }
    }

    private void addScore() {
        score += 100;
        binding.score.setText(String.format("Your score is %d", score));
    }




    private void Fade() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(100);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(1);

        binding.question.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                binding.question.setTextColor(getColor(R.color.green_lizard));
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationEnd(Animation animation) {
                binding.question.setTextColor(getColor(white));
                getQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shake() {
        Animation Shake = AnimationUtils.loadAnimation(this,R.anim.rot);
        binding.card.setAnimation(Shake);
        Shake.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationStart(Animation animation) {
                //Red color on text if false
                binding.question.setTextColor(getColor(red));
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationEnd(Animation animation) {
                binding.question.setTextColor(getColor(white));
                getQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void next(View v) {
        getQuestion();
       // current = (current + 1)% all.size();
        update();//update value of index
    }

    private void getQuestion() {
        //this will be called to dynamically go to the next question once animation ends
        //To prevent out of bounds exeption
        current = (current + 1)% all.size();
    }

    private void update() {

        binding.question.setText(String.valueOf(all.get(current).getQuery()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        preferences.saveState(current);
        preferences.saveHighest(score);
        binding.high.setText(String.valueOf(preferences.getHighest()));
        super.onPause();
    }
}

  /*
    private void preferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(HIGHSCORE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score", score);
        editor.apply();

        //Retrieval
        SharedPreferences getData = getPreferences(MODE_PRIVATE);
        int savedScore = getData.getInt("Score",0);
        binding.score.setText(R.id.score + savedScore);
    }
    */