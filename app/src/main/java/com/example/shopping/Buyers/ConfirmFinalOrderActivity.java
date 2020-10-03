package com.example.shopping.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Button confirmOrderBtn ;
    private TextView nameEditText , phoneEditText , addressEditText , cityEditText ;

    private String totalAmount = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price") ;
        Toast.makeText(this, "Total price = $" + totalAmount     , Toast.LENGTH_SHORT).show();

        confirmOrderBtn =(Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText =(EditText) findViewById(R.id.shipment_name);
        phoneEditText =(EditText) findViewById(R.id.shipment_phone);
        addressEditText = (EditText) findViewById(R.id.shipment_address);
        cityEditText = (EditText) findViewById(R.id.shipment_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Please Provide Your Full Name ... ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Please Provide Your Number Phone ... ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Please Provide Your Home Address... ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this, "Please Provide Your City name ... ", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        final String saveCurrentData ,saveCurrentTime ;
        Calendar calForData = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentData = currentData.format(calForData.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentData.format(calForData.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child("InfoCustomsOrder");

        HashMap<String , Object> ordersMap = new HashMap<>() ;
        ordersMap.put("totalAmount" , totalAmount);
        ordersMap.put("name" ,nameEditText.getText().toString());
        ordersMap.put("phone" ,phoneEditText.getText().toString());
        ordersMap.put("address" ,addressEditText.getText().toString());
        ordersMap.put("city" , cityEditText.getText().toString());
        ordersMap.put("data",saveCurrentData);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child("InfoCustoms")
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final order has been placed successfully .", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this , HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });

    }
}