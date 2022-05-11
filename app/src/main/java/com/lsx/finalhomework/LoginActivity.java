package com.lsx.finalhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView loginTitle;
    Button loginButton;
    TextView toggleLoginReg;
    EditText username;
    EditText password;

    boolean login = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTitle = (TextView) findViewById(R.id.loginTitle);
        loginButton = (Button) findViewById(R.id.loginButton);
        toggleLoginReg = (TextView) findViewById(R.id.toggleLoginReg);
        username = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);

        toggleLoginReg.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        String defaultUsername = sp.getString("username", "");
        if (!defaultUsername.equals("")) {
            this.username.setText(defaultUsername);
        }
    }

    private void goToMain() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toggleLoginReg:
                login = !login;
                loginTitle.setText(login ? "登录" : "注册");
                loginButton.setText(login ? "登录" : "注册");
                toggleLoginReg.setText(login ? "立即注册" : "立即登录");
                break;
            case R.id.loginButton:
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                MyAuth auth = new MyAuth(LoginActivity.this);
                if (login) {
                    switch (auth.authUser(user, pwd)) {
                        case SUCCESS:
                            SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username", user);
                            editor.apply();
                            goToMain();
                            break;
                        case INVALID_USERNAME_OR_PWD:
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            break;
                        case USER_EXISTED:
                            break;
                        case TOKEN_TOO_LONG:
                            Toast.makeText(LoginActivity.this, "用户名或密码太长", Toast.LENGTH_SHORT).show();
                            break;
                        case UNKNOWN_ERROR:
                            Toast.makeText(LoginActivity.this, "出现未知错误", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    // 注册
                    switch (auth.addUser(user, pwd)) {
                        case SUCCESS:
                            goToMain();
                            break;
                        case INVALID_USERNAME_OR_PWD:
                            break;
                        case USER_EXISTED:
                            Toast.makeText(LoginActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                            break;
                        case TOKEN_TOO_LONG:
                            Toast.makeText(LoginActivity.this, "用户名或密码太长", Toast.LENGTH_SHORT).show();
                            break;
                        case UNKNOWN_ERROR:
                            Toast.makeText(LoginActivity.this, "出现未知错误", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }
}