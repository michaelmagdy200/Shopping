package com.example.shopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {

    private Button applyChangesBtn , deleteBtn ;
    private EditText name , price , description ;
    private ImageView imageView ;

    private String productID = " " ;

    private DatabaseReference productsRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        productID = getIntent().getStringExtra("pid") ;
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID) ;

        applyChangesBtn = (Button) findViewById(R.id.product_maintain_btn);
        name = (EditText) findViewById(R.id.product_name_maintain) ;
        price = (EditText) findViewById(R.id.product_price_maintain) ;
        description = (EditText) findViewById(R.id.product_description_maintain);
        imageView = (ImageView) findViewById(R.id.product_image_maintain) ;
        deleteBtn = (Button) findViewById(R.id.delete_product_maintain_btn);

        displaySpecificProductInfo () ;

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProducts() ;
            }
        });
    }

    private void deleteThisProducts() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductsActivity.this, "The Product is delete successfully .", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminMaintainProductsActivity.this , SellerProductCategoryActivity.class));
                finish();
            }
        });
    }

    private void applyChanges() {

        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();

        if (pName.equals("")){
            Toast.makeText(this, "Write down Product Name.", Toast.LENGTH_SHORT).show();
        }
       else if (pPrice.equals("")){
            Toast.makeText(this, "Write down Product Price.", Toast.LENGTH_SHORT).show();
        }
        else if (pDescription.equals("")){
            Toast.makeText(this, "Write down Product Description.", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AdminMaintainProductsActivity.this, "Changes applied Successfully . ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminMaintainProductsActivity.this , SellerProductCategoryActivity.class));
                        finish();
                    }
                }
            });

        }
    }

    private void displaySpecificProductInfo() {

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String Pname = snapshot.child("pname").getValue().toString();
                    String Pprice = snapshot.child("price").getValue().toString();
                    String Pdescription = snapshot.child("description").getValue().toString();
                    String Pimage = snapshot.child("image").getValue().toString();

                    name.setText(Pname);
                    price.setText(Pprice);
                    description.setText(Pdescription);
                    Picasso.get().load(Pimage).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}