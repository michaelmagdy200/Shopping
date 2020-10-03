package com.example.shopping.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.Buyers.HomeActivity;
import com.example.shopping.Buyers.MainActivity;
import com.example.shopping.R;

public class AdminHomeActivity extends AppCompatActivity {

    private Button LogoutBtn , CheckOrderBtn  , maintainproductsbtn , checkandapproveorderbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        LogoutBtn =(Button) findViewById(R.id.admin_logout_btn);
        CheckOrderBtn = (Button) findViewById(R.id.check_orders_btn);
        maintainproductsbtn = (Button) findViewById(R.id.maintain_products) ;
        checkandapproveorderbtn = (Button) findViewById(R.id.check_approve_orders_btn) ;

        maintainproductsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this , HomeActivity.class);
                intent.putExtra("Admin" , "Admin");
                startActivity(intent);
                finish();
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this , MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this , AdminCheckNewProductsActivity.class));
            }
        });

        checkandapproveorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}