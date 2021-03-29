package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.example.trivia.util.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private   TextView questionCounterTextView;
    private TextView highestTextView;
    private   TextView questionTextView;
    private   Button trueButton;
    private   Button falseButton;
    private   ImageButton prevButton;
    private   ImageButton nextButton;
    private   TextView scoreView;
    private   int currentQuestionIndex = 0;
    private  List<Question> questionList;
    private int scoreCounter = 0;
    Prefs prefs;
    private static final String MessageId = "uyt";
    Score score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreView = findViewById(R.id.scoreText);

       highestTextView = findViewById(R.id.highestScore);
        questionCounterTextView = findViewById(R.id.counterView);
        questionTextView = findViewById(R.id.questionTextView);
        trueButton = findViewById(R.id.trueButton);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    falseButton = findViewById(R.id.falseButton);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        score = new Score();

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prefs = new Prefs(MainActivity.this);
        //yurhrt
       //   currentQuestionIndex =  prefs.getState();
        highestTextView.setText("HS:"+prefs.getHighScore());

      //  Log.d("second", String.valueOf(prefs.getHighScore()));
         questionList =  new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
              //  questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                Log.d("inside","process Finished"+questionArrayList);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.prevButton:
                if(currentQuestionIndex>0) {
                    currentQuestionIndex--;
                    updateQuestion();
                }else
                    questionTextView.setText("no que before this");
                break;
            case R.id.nextButton: {

             //   currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
               // updateQuestion();
                goNext();
                break;
            }
            case R.id.trueButton: {
                checkAnswer(true);
                break;
            }
            case R.id.falseButton: {
                checkAnswer(false);

                break;
            }
        }
    }

    private void checkAnswer(boolean userChoice) {
        boolean isAnswerTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
      int toastMessageId = 0;
        if(userChoice==isAnswerTrue)
        {


            addPoints();
            toastMessageId = R.string.correct_answer;
        }else
        {


            deductPoints();
           toastMessageId = R.string.wrong_answer;

        }
       Toast.makeText(this,toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void deductPoints() {
        if(scoreCounter>0) {
            scoreCounter -= 100;
            score.setScore(scoreCounter);
            Log.d("score", String.valueOf(score.getScore()));
            scoreView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
goNext();
        }else
        {
            scoreCounter = 0;
            score.setScore(scoreCounter);
            Log.d("score", String.valueOf(score.getScore()));
            scoreView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
            goNext();
        }

    }

    private void addPoints() {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        Log.d("score", String.valueOf(score.getScore()));
        scoreView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        goNext();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();

        questionTextView.setText(question);
        questionCounterTextView.setText(currentQuestionIndex+"/"+questionList.size());

    }
  public void goNext()
  {
      currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
      updateQuestion();
  }

    @Override
    protected void onPause() {
        prefs.saveHighScore(score.getScore());
       // prefs.setState(currentQuestionIndex);
        super.onPause();
    }
}