package com.example.quiztime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard extends AppCompatActivity {

    CircularProgressBar scoreProgressBar;
    TextView score, points;
    CardView shareBtn;
    RequestQueue requestQueue;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private static long mBackPressed;

    public static final String resurl = "https://soumitri.000webhostapp.com/QuizProject/add_result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Bundle bundle = getIntent().getExtras();
        int marks = bundle.getInt("marks");
        int total= bundle.getInt("len");
        String subject = bundle.getString("paper");
        String name = bundle.getString("name");

        scoreProgressBar = (CircularProgressBar) findViewById(R.id.scoreProgressBar);
        score = (TextView) findViewById(R.id.score);
        points = (TextView) findViewById(R.id.points);
        shareBtn = (CardView) findViewById(R.id.shareBtn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QuizTime");
                    String shareMessage= "\nI have got "+ marks +"Out of "+total+"! You can also try!!";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    Toast.makeText(Scoreboard.this, "Check your connection!" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreProgressBar.setProgressMax(total);
        scoreProgressBar.setProgress(marks);
        score.setText(marks+"/"+total);

        if (marks==0){
            //Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
            points.setText("Try Again!");
        }
        else if(marks>0 && marks<=2){
            //Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_SHORT).show();
            points.setText("Good");
        }
        else if(marks>2 && marks<=total){
            //Toast.makeText(getApplicationContext(), "Well Done", Toast.LENGTH_SHORT).show();
            points.setText("Well Done");
        }
        else {
            //Toast.makeText(getApplicationContext(), "Excellent", Toast.LENGTH_SHORT).show();
            points.setText("Excellent");
        }


       requestQueue = Volley.newRequestQueue(Scoreboard.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, resurl,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        String res = response.toString();
                        Toast.makeText(Scoreboard.this, res, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Scoreboard.this, "No Internet! check your connection", Toast.LENGTH_SHORT).show();
                    }
                }) {
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("name", name);
                map.put("subject", subject);
                map.put("marks",""+marks);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Scoreboard.this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit app");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}