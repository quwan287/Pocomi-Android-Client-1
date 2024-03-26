package com.example.poly_truyen_client;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poly_truyen_client.api.ConnectAPI;
import com.example.poly_truyen_client.api.UserServices;
import com.example.poly_truyen_client.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edFullName;
    private EditText edUsername;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConfirmPassword;
    private Button btnSignUp;
    private TextView TextBtnSignIn;
    private View rootView;
    private UserServices userServices;

    void showSnack (String msg) {
        Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        userServices = new ConnectAPI().connect.create(UserServices.class);

        rootView = findViewById(R.id.root_view_sign_up);

        edFullName = (EditText) findViewById(R.id.edFullName);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edConfirmPassword = (EditText) findViewById(R.id.edConfirmPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        TextBtnSignIn = (TextView) findViewById(R.id.TextBtnSignIn);

        TextView[] eds = {edFullName, edUsername, edEmail, edPassword, edConfirmPassword};

        btnSignUp.setOnClickListener(v -> {

            for (TextView ed : eds) {
                if (ed.getText().toString().trim().equals("")) {
                    showSnack("Error, Please fill these form!");
                    return;
                }
            }

            String fullName = edFullName.getText().toString().trim();
            String username = edUsername.getText().toString().trim();
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String confirmPassword = edConfirmPassword.getText().toString().trim();

            if (!confirmPassword.equals(password)) {
                showSnack("Error, password do not match!");
                return;
            }

            JsonObject userPayload = new JsonObject();
            userPayload.addProperty("fullName", fullName);
            userPayload.addProperty("email", email);
            userPayload.addProperty("password", password);
            userPayload.addProperty("username", username);

            Call<User> callRegister = userServices.registerAccount(userPayload);
            callRegister.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();

                        if (!user.get_id().equals("")) {
                            showSnack("Success, register account completed!");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    } catch (Exception e) {

                                    }
                                }
                            }).start();
                        }

                    } else {
                        showSnack("Error, email or username already exists!");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    showSnack("Error, unknown error please try again or contact to admin!");
                }
            });

        });

        findViewById(R.id.TextBtnSignIn).setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}