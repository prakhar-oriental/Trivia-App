package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private   TextView questionCounterTextView;
    private   TextView questionTextView;
    private   Button trueButton;
    private   Button falseButton;
    private   ImageButton prevButton;
    private   ImageButton nextButton;
    private   int currentQuestionIndex = 0;
    private  List<Question> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionCounterTextView = findViewById(R.id.counterView);
        questionTextView = findViewById(R.id.questionTextView);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
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
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();

                break;
            }
            case R.id.trueButton: {
                checkAnswer(true);
                updateQuestion();
                break;
            }
            case R.id.falseButton: {
                checkAnswer(false);
                updateQuestion();
                break;
            }
        }
    }

    private void checkAnswer(boolean userChoice) {
        boolean isAnswerTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
      int toastMessageId = 0;
        if(userChoice==isAnswerTrue)
        {
            fakeAnimation();
            toastMessageId = R.string.correct_answer;
        }else
        {
            shakeAnimation();
           toastMessageId = R.string.wrong_answer;

        }
       Toast.makeText(this,toastMessageId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
       questionCounterTextView.setText(currentQuestionIndex+"/"+questionList.size());
    }
    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);
          shake.setAnimationListener(new Animation.AnimationListener() {
              @Override
              public void onAnimationStart(Animation animation) {
                  cardView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
              }

              @Override
              public void onAnimationEnd(Animation animation) {
                  cardView.setBackgroundColor(getResources().getColor(android.R.color.white));
              }

              @Override
              public void onAnimationRepeat(Animation animation) {

              }
          });
    }
    private void fakeAnimation()
    {
        Animation fake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fake_animation);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(fake);
        fake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}