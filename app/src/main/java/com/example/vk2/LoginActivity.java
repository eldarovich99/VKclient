package com.example.vk2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vk2.ResponseClasses.AuthResponse;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "ProfileFragment";
    private Button mLoginButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private AuthResponse mAuthResponse;

    private final String url = "https://api.vk.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init(){
        mLoginButton = findViewById(R.id.login_button);
        mUsernameEditText = findViewById(R.id.login_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new VKAuthTask().execute();

            }
        });
    }


    private class VKAuthTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mAuthResponse = new VKFetchr().auth(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
                if (mAuthResponse != null) {
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.putExtra(VKFetchr.ACCESS_TOKEN,mAuthResponse.getAccessToken());
                    intent.putExtra(VKFetchr.EXPIRES_IN, mAuthResponse.getExpiresIn());
                    intent.putExtra(VKFetchr.USER_ID,mAuthResponse.getUserID());
                    startActivity(intent);
                }
        }
    }

}
