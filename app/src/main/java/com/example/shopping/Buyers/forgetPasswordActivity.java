package com.example.shopping.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class forgetPasswordActivity extends AppCompatActivity {

    private String check = "" ;

    private TextView pageTitle , titleQuestions ;
    private EditText phoneNumber , question1 , question2 ;
    private Button verifyBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        check = getIntent().getStringExtra("check") ;

        pageTitle  = (TextView) findViewById(R.id.page_title) ;
        titleQuestions = (TextView) findViewById(R.id.text_view) ;
        phoneNumber = (EditText) findViewById(R.id.find_phone_number) ;
        question1 = (EditText) findViewById(R.id.question_1) ;
        question2 = (EditText) findViewById(R.id.question_2) ;
        verifyBtn = (Button) findViewById(R.id.verify_btn) ;

    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings"))
        {
            pageTitle.setText("Set Questions");
            titleQuestions.setText("Please , Answer for the Following Security Questions ...");
            verifyBtn.setText("Set");

            displayPreviousAnswer();

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswer();
                }
            });
        }


        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });

        }
    }

    private void setAnswer(){
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (question1.equals("") && question2.equals("")){
            Toast.makeText(forgetPasswordActivity.this, "Please answer both questions .", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child("InfoCustoms");

            HashMap<String , Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1" , answer1) ;
            userdataMap.put("answer2" , answer2) ;

            questionRef.child("Security Questions").updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(forgetPasswordActivity.this, "you have set questions successfully .", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgetPasswordActivity.this , HomeActivity.class));
                            }
                        }
                    });
        }
    }

    private void displayPreviousAnswer(){
        DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child("InfoCustoms") ;
        questionRef.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String ans1 = snapshot.child("answer1").getValue().toString();
                    String ans2 = snapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void  verifyUser() {
            final String answer1 = question1.getText().toString().toLowerCase();
            final String answer2 = question2.getText().toString().toLowerCase();
            final String phone = phoneNumber.getText().toString() ;

            if (!phone.equals("") && !answer1.equals("") && !answer2.equals("")) {

                final DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(phone);

                questionRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            String mphone = snapshot.child("phone").getValue().toString();
                            if (phone.equals(mphone)) {

                                if (snapshot.hasChild("Security Questions")) {
                                    String ans1 = snapshot.child("Users").child("InfoCustoms").child("Security Questions").child("answer1").getValue().toString();
                                    String ans2 = snapshot.child("Users").child("InfoCustoms").child("Security Questions").child("answer2").getValue().toString();
                                    if (!ans1.equals(answer1)) {
                                        Toast.makeText(forgetPasswordActivity.this, "Your question 1's answer is Wrong", Toast.LENGTH_SHORT).show();
                                    } else if (!ans2.equals(answer2)) {
                                        Toast.makeText(forgetPasswordActivity.this, "Your question 2's answer is Wrong", Toast.LENGTH_SHORT).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(forgetPasswordActivity.this);
                                        builder.setTitle("Change Password");
                                        final EditText newPassword = new EditText(forgetPasswordActivity.this);
                                        newPassword.setHint("New Password");
                                        builder.setView(newPassword);
                                        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!newPassword.getText().toString().equals("")) {
                                                    questionRef.child("password")
                                                            .setValue(newPassword.getText().toString())
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(forgetPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(forgetPasswordActivity.this , LoginActivity.class));
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                            }
                                        });
                                        builder.show();
                                    }

                                }

                            } else {
                                Toast.makeText(forgetPasswordActivity.this, "This User's Number phone is not exists .", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(forgetPasswordActivity.this, "You have not set the security questions.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else {
                Toast.makeText(this, "Please Complete the form ", Toast.LENGTH_SHORT).show();
            }
        }

}