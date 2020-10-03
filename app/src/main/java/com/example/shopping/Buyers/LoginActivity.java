package com.example.shopping.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.Admin.SellerProductCategoryActivity;
import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText InputEmail, InputPassword;
    private ProgressDialog loadingBar;
    private TextView AdminLink, notAdminLink;
    private FirebaseAuth auth;
    private CheckBox chkBoxRememberMe;
    private TextView forgetPasswordTxt ;

    private String parentDbName = "Users";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("checkbox" , MODE_PRIVATE);
        String checkbox = preferences.getString("remember" , "") ;

        if (checkbox.equals("true")){
            startActivity(new Intent(LoginActivity.this , HomeActivity.class));

        }
        else if (checkbox.equals("false")){
            Toast.makeText(this, "Please , LogIn ...", Toast.LENGTH_SHORT).show();
        }

        InputEmail = (EditText) findViewById(R.id.login_email_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        AdminLink = (TextView) findViewById(R.id.admin_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_link);
        loadingBar = new ProgressDialog(this);
        final Button loginBtn = (Button) findViewById(R.id.login_btn);
        forgetPasswordTxt =(TextView) findViewById(R.id.forget_password_link)  ;


        forgetPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , forgetPasswordActivity.class);
                intent.putExtra("check" , "login") ;
                startActivity(intent);
            }
        });


        auth = FirebaseAuth.getInstance();

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);

        chkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember" , "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
            
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";

            }
        });
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });

    }


    private void LoginUser() {

        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(email, password);

            }
        }



    private void AllowAccessToAccount(final String email, final String password) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in Successfully ....", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();

                        } else if (email.equals("admin@admin.com") && password.equals("admin")) {
                            Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            startActivity(new Intent(LoginActivity.this, SellerProductCategoryActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error signing in with email link" + task.getException(), Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                        }
                    }
                });
    }
}
