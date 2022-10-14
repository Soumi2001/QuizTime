package com.example.quiztime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserLogIn extends AppCompatActivity {

    AppCompatButton login;
    VideoView logInVideo;
    EditText name, password, email;
    TextView headLog, passforget, reg;
    RequestQueue reqobj;

    public static final String logurl = "https://soumitri.000webhostapp.com/QuizProject/LogIn.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        logInVideo = (VideoView) findViewById(R.id.logInVideo);
        logInVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.coding));
        logInVideo.start();

        logInVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        headLog = (TextView) findViewById(R.id.headLog);
        passforget = (TextView) findViewById(R.id.passforget);
        reg = (TextView) findViewById(R.id.reg);
        login = (AppCompatButton) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reqobj = Volley.newRequestQueue(UserLogIn.this);

                String em = name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, logurl,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                String res = response.toString();

                                if(res.equals("success")){
                                    Intent intent = new Intent(UserLogIn.this, HomeScreen.class);
                                    intent.putExtra("nm", em);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(UserLogIn.this, "Invalid Username or Password!!" +res, Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(UserLogIn.this, "No Internet! Check your connection\n", Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    public Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> map = new HashMap<>();
                        map.put("email", mail);
                        map.put("password", pass);
                        return map;
                    }
                };
                reqobj.add(stringRequest);
            }
        });

        passforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(UserLogIn.this, ForgetPasswordActivity.class);
                    startActivity(i);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserLogIn.this, RegisActivity.class);
                startActivity(i);
            }
        });
    }
}