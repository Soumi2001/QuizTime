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

public class RegisActivity extends AppCompatActivity {

    public static final String regurl = "https://soumitri.000webhostapp.com/QuizProject/add_registration.php";
    AppCompatButton register;
    EditText regEmail, regPhone, regName, regPassword;
    TextView log;
    VideoView regVideo;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        regVideo = (VideoView) findViewById(R.id.regVideo);
        regVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.registration));
        regVideo.start();

        regVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        regEmail = (EditText) findViewById(R.id.regEmail);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regPhone = (EditText) findViewById(R.id.regPhone);
        regName = (EditText) findViewById(R.id.regName);
        log = (TextView) findViewById(R.id.log);


        register = (AppCompatButton) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    requestQueue = Volley.newRequestQueue(RegisActivity.this);

                    String name = regName.getText().toString();
                    String phone = regPhone.getText().toString();
                    String email = regEmail.getText().toString();
                    String password = regPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (password.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phone.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (name.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, regurl,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {
                                    String res = response.toString();

                                    Toast.makeText(RegisActivity.this, res, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisActivity.this, HomeScreen.class);
                                    intent.putExtra("nm", name);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(RegisActivity.this, "No Internet! check your connection", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();

                            map.put("Name", name);
                            map.put("Phone", phone);
                            map.put("Email", email);
                            map.put("Password", password);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisActivity.this, UserLogIn.class);
                startActivity(i);
            }
        });

    }
}