package com.example.quiztime;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    CardView coption, cplusop, javaop, pythonop;
    String sub, nm;
    ProgressDialog progressDialog;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private static long mBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        coption = (CardView) findViewById(R.id.coption);
        cplusop = (CardView) findViewById(R.id.cplusop);
        javaop = (CardView) findViewById(R.id.javaop);
        pythonop = (CardView) findViewById(R.id.pythonop);

        coption.setOnClickListener(this);
        cplusop.setOnClickListener(this);
        javaop.setOnClickListener(this);
        pythonop.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        nm = bundle.getString("nm");


    }
        @Override
        public void onClick(View view) {
            Intent i;
            switch (view.getId()) {
//                case R.id.coption:
//
//                    i = new Intent(this, cQuiz.class);
//                    startActivity(i);
//                    Toast.makeText(HomeScreen.this, "welcome to C Quiz!", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.cplusop:
//                    i = new Intent(HomeScreen.this, cplusQuiz.class);
//                    startActivity(i);
//                    Toast.makeText(HomeScreen.this, "welcome to C++ Quiz!", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.javaop:
//                    i = new Intent(HomeScreen.this, javaQuiz.class);
//                    startActivity(i);
//                    Toast.makeText(HomeScreen.this, "welcome to JAVA Quiz!", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.pythonop:
//                    i = new Intent(HomeScreen.this, pythonQuiz.class);
//                    startActivity(i);
//                    Toast.makeText(HomeScreen.this, "welcome to Python Quiz!", Toast.LENGTH_SHORT).show();
//                    break;

                case R.id.coption:
                    sub = "C";
                    i = new Intent(this, QuestionAllShow.class);
                    i.putExtra("subject", sub);
                    i.putExtra("nm", nm);
                    startActivity(i);
                    Toast.makeText(HomeScreen.this, "welcome to C Quiz!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cplusop:
                    sub = "C++";
                    i = new Intent(HomeScreen.this, QuestionAllShow.class);
                    i.putExtra("subject", sub);
                    i.putExtra("nm", nm);
                    startActivity(i);
                    Toast.makeText(HomeScreen.this, "welcome to C++ Quiz!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.javaop:
                    sub = "Java";
                    i = new Intent(HomeScreen.this, QuestionAllShow.class);
                    i.putExtra("subject", sub);
                    i.putExtra("nm", nm);
                    startActivity(i);
                    Toast.makeText(HomeScreen.this, "welcome to JAVA Quiz!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pythonop:
                    sub = "Python";
                    i = new Intent(HomeScreen.this, QuestionAllShow.class);
                    i.putExtra("subject", sub);
                    i.putExtra("nm", nm);
                    startActivity(i);
                    Toast.makeText(HomeScreen.this, "welcome to Python Quiz!", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
        @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
