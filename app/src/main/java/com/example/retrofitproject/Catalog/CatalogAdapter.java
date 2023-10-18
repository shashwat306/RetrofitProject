package com.example.retrofitproject.Catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitproject.CommonResponse.CatalogListResponse;
import com.example.retrofitproject.CommonResponse.ProductListResponse;
import com.example.retrofitproject.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogDataViewHolder> {


    List<CatalogListResponse.Category> catalogListResponse;
    Context context;

    public CatalogAdapter(List<CatalogListResponse.Category> categoryList, Context context) {
        this.catalogListResponse = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatalogDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View  view = layoutInflater.inflate(R.layout.item_catelog,parent,false);
        return new CatalogDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogDataViewHolder holder, int position) {

        holder.tvName.setText(catalogListResponse.get(position).getCategoryName());


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        CatalogSubCategoryAdapter catalogSubCategoryAdapter = new CatalogSubCategoryAdapter(catalogListResponse.get(position).getSubCategoryList(),context);
        holder.rv_CatalogCat.setLayoutManager(layoutManager);
        holder.rv_CatalogCat.setAdapter(catalogSubCategoryAdapter);

    }

    @Override
    public int getItemCount() {
        return catalogListResponse.size();
    }


    public class CatalogDataViewHolder extends RecyclerView.ViewHolder{

        TextView tvName ;
        RecyclerView rv_CatalogCat;
        public CatalogDataViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvCategoryName);
            rv_CatalogCat = itemView.findViewById(R.id.rv_CatalogCategory  );


        }
    }
}
