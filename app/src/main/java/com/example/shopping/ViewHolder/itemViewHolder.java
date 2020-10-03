package com.example.shopping.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Interface.ItemClickListener;
import com.example.shopping.R;

public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName , txtProductDescription , txtProductPrice , txtProductStatus;
    public ImageView imageView ;
    public ItemClickListener listener ;

    public itemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image) ;
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description) ;
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price) ;
        txtProductStatus = (TextView) itemView.findViewById(R.id.product_status) ;
    }

    public void setItemClickListener (ItemClickListener listener){
        this.listener = listener ;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v , getAdapterPosition() , false);

    }
}
