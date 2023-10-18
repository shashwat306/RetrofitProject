package com.example.retrofitproject.Catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitproject.CommonResponse.CatalogListResponse;
import com.example.retrofitproject.R;

import java.util.List;

public class CatalogProductAdapter extends RecyclerView.Adapter<CatalogProductAdapter.CatalogProductViewHolder> {

    List<CatalogListResponse.Category.SubCategory.Product> productList;
    Context context;

    public CatalogProductAdapter(List<CatalogListResponse.Category.SubCategory.Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatalogProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_catalog_product,parent,false);
        return new CatalogProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogProductViewHolder holder, int position) {
        holder.tvProductName.setText(productList.get(position).getProductName());
        holder.tvProductPrice.setText(productList.get(position).getProductPrice());
        holder.tvProductDescription.setText(productList.get(position).getProductDesc());

        Glide
                .with(context)
                .load(productList.get(position).getProductImage())
                .placeholder(R.drawable.ic_imageholder)
                .into(holder.imgProduct);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public  static  class CatalogProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView tvProductName,tvProductPrice,tvProductDescription;

        public CatalogProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.ProductNameTV);
            tvProductPrice = itemView.findViewById(R.id.tVPrice);
            tvProductDescription = itemView.findViewById(R.id.ProductDescTV);
        }
    }



}
