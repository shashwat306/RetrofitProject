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

import com.example.retrofitproject.CommonResponse.CatalogListResponse;
import com.example.retrofitproject.R;

import java.util.List;

public class CatalogSubCategoryAdapter extends RecyclerView.Adapter<CatalogSubCategoryAdapter.CatalogSubCategoryViewHolder> {

    List<CatalogListResponse.Category.SubCategory> subCategoryList;
    Context context;
    boolean status = false;


    public CatalogSubCategoryAdapter(List<CatalogListResponse.Category.SubCategory> subCategoryList,Context context) {
        this.subCategoryList = subCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatalogSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_catalog_subcategory,parent,false);
        return new CatalogSubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogSubCategoryViewHolder holder, int position) {
        holder.tvSubCategoryName.setText(subCategoryList.get(position).getSubcategoryName());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        CatalogProductAdapter catalogProductAdapter = new CatalogProductAdapter(subCategoryList.get(position).getProductList(),context);
        holder.rv_CatalogProduct.setLayoutManager(layoutManager);
        holder.rv_CatalogProduct.setAdapter(catalogProductAdapter);

//        holder.imgDropDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (status == false){
//                    holder.recyclerViewCatalogProduct.setVisibility(View.GONE);
//                    status = true;
//                }
//                else {
//                    holder.recyclerViewCatalogProduct.setVisibility(View.VISIBLE);
//                    status = false;
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

  public static   class CatalogSubCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvSubCategoryName;
        ImageView imgDropDown;
        RecyclerView rv_CatalogProduct;
        public CatalogSubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubCategoryName = itemView.findViewById(R.id.tvSubCategoryName);
            imgDropDown = itemView.findViewById(R.id.imgDown);
            rv_CatalogProduct = itemView.findViewById(R.id.rv_catalogProduct);
        }
    }
}
