package com.example.shopping.Seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerResgisterActivity extends AppCompatActivity {

    private Button sellerLoginBegin , registerBtn ;
    private EditText nameInput , emailInput , phoneInput , passwordInput , addressInput ;
    private FirebaseAuth mAuth ;
    ProgressDialog loadingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_resgister);

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        nameInput = (EditText) findViewById(R.id.seller_name) ;
        emailInput = (EditText) findViewById(R.id.seller_email) ;
        phoneInput = (EditText) findViewById(R.id.seller_phone) ;
        passwordInput = (EditText) findViewById(R.id.seller_password) ;
        addressInput = (EditText) findViewById(R.id.seller_address) ;
        registerBtn = (Button) findViewById(R.id.seller_register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });



        sellerLoginBegin = (Button) findViewById(R.id.seller_already_have_account_btn) ;
        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerResgisterActivity.this , SellerLoginActivity.class));
            }
        });
    }

    private void registerSeller() {

        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();

        if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")){

            loadingBar.setTitle("Create Seller Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email , password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final DatabaseReference rootRef ;
                            rootRef = FirebaseDatabase.getInstance().getReference();
                            String uid = mAuth.getCurrentUser().getUid();
                            HashMap<String , Object> sellerMap = new HashMap<>();
                            sellerMap.put("uid" , uid);
                            sellerMap.put("name" , name) ;
                            sellerMap.put("password" , password) ;
                            sellerMap.put("email" , email) ;
                            sellerMap.put("phone" , phone) ;
                            sellerMap.put("address" , address);

                            rootRef.child("Sellers").child(uid).updateChildren(sellerMap)
                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             loadingBar.dismiss();
                                             Toast.makeText(SellerResgisterActivity.this, "you are Registered Successfully .", Toast.LENGTH_SHORT).show();

                                             Intent intent =new Intent(SellerResgisterActivity.this , SellerHomeActivity.class) ;
                                             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                             finish();
                                         }
                                     });


                        }
                    });
        }
        else {
            Toast.makeText(this, "Please complete the registeration form .", Toast.LENGTH_SHORT).show();
        }

    }
}