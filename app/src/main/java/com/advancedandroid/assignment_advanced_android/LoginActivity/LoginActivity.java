package com.advancedandroid.assignment_advanced_android.LoginActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.advancedandroid.assignment_advanced_android.MainActivity;
import com.advancedandroid.assignment_advanced_android.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    public boolean checkLogin;
    EditText edtName, edtPass;
    Button btnLogin;

    CheckBox chkRemember;
    SharedPreferences sharedPreferences;
    Intent intent;

    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ
        mapping();

        //
//        getShared();
        if (checkLogin == true){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        //Login và lưu
        sharedPreferences = getSharedPreferences("ACCOUNT_DATA", MODE_PRIVATE);
        edtName.setText(sharedPreferences.getString("USERNAME",""));
        edtPass.setText(sharedPreferences.getString("PASSWORD",""));
        chkRemember.setChecked(sharedPreferences.getBoolean("CHKREMEMBER",false));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.assignment_huynqhpd02718",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void mapping(){
        edtName = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
    }

    private void login(){

            String username = edtName.getText().toString();
            String password = edtPass.getText().toString();

            if (username.equals("admin") && password.equals("admin")){

                if (chkRemember.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME",username);
                    editor.putString("PASSWORD",password);
                    editor.putBoolean("CHKREMEMBER",true);
                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("USERNAME");
                    editor.remove("PASSWORD");
                    editor.remove("CHKREMEMBER");
                    editor.commit();
                }

                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(LoginActivity.this, "Kiểm tra lại tài khoản", Toast.LENGTH_LONG).show();

            }
    }

}