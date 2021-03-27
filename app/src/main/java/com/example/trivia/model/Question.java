package com.example.trivia.model;

public class Question {
    public Question()
    {

    }

    public Question(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }



    public Question(String answer) {
        this.answer = answer;
    }

    private String answer;

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    boolean answerTrue;
}
