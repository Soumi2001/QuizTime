package com.example.quiztime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText password, email;
    TextView headLog;
    AppCompatButton up;

    public static final String upurl = "https://soumitri.000webhostapp.com/QuizProject/forgetPassword.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        headLog = (TextView) findViewById(R.id.headLog);

        up = (AppCompatButton) findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                String np = password.getText().toString();

                ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
                progressDialog.setMessage("Updating.....");
                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST, upurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ForgetPasswordActivity.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),UserLogIn.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ForgetPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<String,String>();
                        params.put("email", mail);
                        params.put("password", np);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(ForgetPasswordActivity.this);
                requestQueue.add(request);

            }
        });


    }
}