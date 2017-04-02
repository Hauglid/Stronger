package com.hauglidtech.stronger;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle loginData = getIntent().getExtras();

        if(loginData == null){
            return;
        }

        username = loginData.getString("username");
        password = loginData.getString("password");

        TextView usernameText = (TextView) findViewById(R.id.usernameInput);
        TextView passwordText = (TextView) findViewById(R.id.passwordInput);

        usernameText.setText(username);
        passwordText.setText(password);

    }

    public void onClickReturn(View v){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("password", username);
        returnIntent.putExtra("username", password);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
