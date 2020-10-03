package com.example.shopping.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.R;
import com.example.shopping.Seller.SellerAddNewProductActivity;

public class SellerProductCategoryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);



        ImageView tShirts = (ImageView) findViewById(R.id.t_shirts);
        ImageView sportsTshirts = (ImageView) findViewById(R.id.sports_t_shirts);
        ImageView femaleDresses = (ImageView) findViewById(R.id.female_dresses);
        ImageView sweethers = (ImageView) findViewById(R.id.sweathers);
        ImageView glasses = (ImageView) findViewById(R.id.glasses);
        ImageView hatsCaps = (ImageView) findViewById(R.id.hats_caps);
        ImageView walletsBagsPurses = (ImageView) findViewById(R.id.purses_bags_wallets);
        ImageView shoes = (ImageView) findViewById(R.id.shoes);
        ImageView headPhonesHandFree = (ImageView) findViewById(R.id.headphones_handfree);
        ImageView laptops = (ImageView) findViewById(R.id.laptop_pc);
        ImageView watches = (ImageView) findViewById(R.id.watches);
        ImageView mobilePhone = (ImageView) findViewById(R.id.mobilephones);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "tShirts") ;
                startActivity(intent);
            }
        });
        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "SportsShirts") ;
                startActivity(intent);
            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Female Dresses") ;
                startActivity(intent);
            }
        });
        sweethers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Sweethers") ;
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Glasses") ;
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Hats Caps") ;
                startActivity(intent);
            }
        });
        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Wallets Bages Purses") ;
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Shoes") ;
                startActivity(intent);
            }
        });
        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "HeadPhones HandFree") ;
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Laptops") ;
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Watches") ;
                startActivity(intent);
            }
        });
        mobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this , SellerAddNewProductActivity.class);
                intent.putExtra("category" , "Mobile Phone") ;
                startActivity(intent);
            }
        });



    }
}