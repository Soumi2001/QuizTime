package com.example.quiztime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionAllShow extends AppCompatActivity {

    CountDownTimer count;
    int time = 20, records;
    public int counter;

    ProgressDialog progressDialog;
    TextView qidtxt, questionNumber;
    RadioButton op1radio, op2radio, op3radio, op4radio;
    Button nextbtn;
    ProgressBar quizTimer;
    ArrayList<modelClass> ListofQ = new ArrayList<>();
    RequestQueue reque;
    int index = 0, marks, subcode = 0;

    public  static final String showurl = "https://soumitri.000webhostapp.com/QuizProject/Display_all_qna.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_all_show);

        Bundle bundle = getIntent().getExtras();
        String sub = bundle.getString("subject");
        String name = bundle.getString("nm");


        if (sub.equals("C")){
            subcode = 1;
        }else if (sub.equals("C++")){
            subcode = 2;
        }else if (sub.equals("Java")){
            subcode = 3;
        }else if (sub.equals("Python")){
            subcode = 4;
        }

        hook();

        progressDialog = new ProgressDialog(QuestionAllShow.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Questions & Options"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkans(index);
                if(ListofQ.size()-1 == index)
                {
                    Toast.makeText(QuestionAllShow.this, "marks "+ marks, Toast.LENGTH_SHORT).show();
                    time = 0;
                    Intent intent = new Intent(QuestionAllShow.this, com.example.quiztime.Scoreboard.class);
                    intent.putExtra("marks", marks);
                    intent.putExtra("len", records);
                    intent.putExtra("paper", sub);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }

                else
                {
                    index++;
                    int qn = index;
                    questionNumber.setText((qn+1)+"/"+records);
                    displayQuestion(index);
                }            }
        });

        final TextView counttime=findViewById(R.id.counttime);
        new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                counttime.setText("Finished");

            }
        }.start();


        count = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                time-=1;
                quizTimer.setProgress(time);
            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(QuestionAllShow.this, com.example.quiztime.Scoreboard.class);
                intent.putExtra("marks", marks);
                intent.putExtra("len", records);
                intent.putExtra("paper", sub);
                intent.putExtra("name", name);
                startActivity(intent);

                /*Dialog dialog = new Dialog(QuestionAllShow.this, R.style.Dialogue);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.timeout);
                dialog.findViewById(R.id.homepage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionAllShow.this, HomeScreen.class);
                        startActivity(intent);
                    }
                });
                dialog.findViewById(R.id.timeoutbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionAllShow.this, Scoreboard.class);
                        intent.putExtra("marks", marks);
                        intent.putExtra("len", records);
                        intent.putExtra("paper", sub);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }
                });
                dialog.show();*/
            }
        }.start();


        reque = Volley.newRequestQueue(QuestionAllShow.this);

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, showurl,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        String res = response.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            records = jsonArray.length();

                            int qn = index;
                            questionNumber.setText((qn+1)+"/"+records);

                            ListofQ = new ArrayList<>();

                            for(int i=0;i<records;i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String id = jsonObject1.getString("ID");
                                String question = jsonObject1.getString("Question");
                                String opt1 = jsonObject1.getString("OptionA");
                                String opt2 = jsonObject1.getString("OptionB");
                                String opt3 = jsonObject1.getString("OptionC");
                                String opt4 = jsonObject1.getString("OptionD");
                                String ans = jsonObject1.getString("Answer");
                                int subject = jsonObject1.getInt("Subject");

                                modelClass m = new modelClass(question,opt1,opt2,opt3,opt4, ans);

                                ListofQ.add(m);

                            }
                            displayQuestion(index);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QuestionAllShow.this, "No Internet! Check your connection\n", Toast.LENGTH_SHORT).show();
                    }
                }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Subject", ""+subcode);
                return map;
            }
        };
        reque.add(stringRequest1);

    }

    private void hook() {
        quizTimer = (ProgressBar) findViewById(R.id.quizTimer);
        qidtxt = (TextView) findViewById(R.id.qidtxt);
        op1radio  = (RadioButton) findViewById(R.id.op1radio);
        op2radio  = (RadioButton) findViewById(R.id.op2radio);
        op3radio  = (RadioButton) findViewById(R.id.op3radio);
        op4radio  = (RadioButton) findViewById(R.id.op4radio);
        nextbtn = (Button) findViewById(R.id.nextbtn);
        questionNumber = (TextView) findViewById(R.id.questionNumber);
    }

    private void checkans(int pos) {

        String answer = ListofQ.get(pos).getAns();

        String userAnswer;
        if(op1radio.isChecked())
        {
            userAnswer = ListofQ.get(pos).getoA();

        }
        else if(op2radio.isChecked())
        {
            userAnswer = ListofQ.get(pos).getoB();

        }
        else if(op3radio.isChecked())
        {
            userAnswer = ListofQ.get(pos).getoC();

        }
        else
        {
            userAnswer = ListofQ.get(pos).getoD();

        }

        if(userAnswer.equals(answer)) {
            marks++;
        }

    }

    public void displayQuestion(int position)
    {
        if(index == ListofQ.size()-1)
        {
            nextbtn.setText("Finish");
        }

        op1radio.setChecked(false);
        op2radio.setChecked(false);
        op3radio.setChecked(false);
        op4radio.setChecked(false);

        qidtxt.setText(ListofQ.get(position).getQuestion());
        op1radio.setText(ListofQ.get(position).getoA());
        op2radio.setText(ListofQ.get(position).getoB());
        op3radio.setText(ListofQ.get(position).getoC());
        op4radio.setText(ListofQ.get(position).getoD());
    }

}



