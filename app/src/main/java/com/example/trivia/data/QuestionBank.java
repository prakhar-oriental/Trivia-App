package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.Controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    String url = "https://jsonplaceholder.typicode.com/todos";
    ArrayList<Question> questionArrayList = new ArrayList<Question>();
    public List<Question> getQuestions(AnswerListAsyncResponse callBack)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length();i++)
                {
                    Question question = new Question();
                    try {
                        question.setAnswer((String) response.getJSONObject(i).get("title"));
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(3));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("json","que"+question);
                    questionArrayList.add(question);
                }
                if(callBack!=null) callBack.processFinished(questionArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return  questionArrayList;
    }
}
