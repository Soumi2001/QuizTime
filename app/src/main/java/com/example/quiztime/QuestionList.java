package com.example.quiztime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {

    public static ArrayList<modelClass> ListofC_PlusQ;
    public static ArrayList<modelClass> ListofJavaQ;
    public static ArrayList<modelClass> ListofPythonQ;
    public static ArrayList<modelClass> ListofQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);


    }
}