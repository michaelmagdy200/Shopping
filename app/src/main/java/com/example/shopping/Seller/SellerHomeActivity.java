package com.example.shopping.Seller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Admin.SellerProductCategoryActivity;
import com.example.shopping.Buyers.MainActivity;
import com.example.shopping.Model.Products;
import com.example.shopping.R;
import com.example.shopping.ViewHolder.itemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SellerHomeActivity extends AppCompatActivity {
    private TextView mTextMessage ;
    private RecyclerView recyclerView ;
    RecyclerView.LayoutManager  layoutManager ;
    private DatabaseReference unverifiedProductsRef ;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.navigation_home :
                            Intent intentHome =new Intent(SellerHomeActivity.this , SellerHomeActivity.class) ;
                            startActivity(intentHome);
                        return true ;
                        case R.id.navigation_add :
                            Intent intentCat =new Intent(SellerHomeActivity.this , SellerProductCategoryActivity.class) ;
                            startActivity(intentCat);

                            return true ;
                        case R.id.navigation_logout :
                            mTextMessage.setText(R.string.title_notifications);
                            final FirebaseAuth mAuth ;
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.signOut();

                            Intent intentMain =new Intent(SellerHomeActivity.this , MainActivity.class) ;
                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intentMain);
                            finish();
                            return true ;
                    }
                    return false;
                }
            } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_logout)
                .build();

        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products") ;

        recyclerView = findViewById(R.id.seller_home_recyclerview) ;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(unverifiedProductsRef.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()) ,Products.class)
                        .build() ;
        final FirebaseRecyclerAdapter<Products , itemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, itemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull itemViewHolder holder, int i, @NonNull final Products products) {
                        holder.txtProductName.setText(products.getPname());
                        holder.txtProductDescription.setText(products.getDescription());
                        holder.txtProductPrice.setText("Price = " + products.getPrice() + "$");
                        holder.txtProductStatus.setText(products.getProductState());
                        Picasso.get().load(products.getImage()).into(products.imageView);

                        final Products itemClick = products ;
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID =  products.getPid() ;

                                CharSequence options[] = new CharSequence[] {
                                        "Yes" ,
                                        "NO"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this) ;
                                builder.setTitle("Do You Want to delete this Product . Are You Sure ?? ");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (which == 0) {
                                            deleteproducts (productID) ;
                                        }
                                        if (which == 1){

                                        }

                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                        itemViewHolder holder = new itemViewHolder(view);
                        return holder;
                    }
                } ;
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteproducts(String productID) {
        unverifiedProductsRef.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SellerHomeActivity.this, "That item has been approved, and it is now available for sale from the Seller .", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}