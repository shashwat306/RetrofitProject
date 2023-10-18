package com.example.retrofitproject.SubCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.retrofitproject.CommonResponse.SubCategoryListResponse;
import com.example.retrofitproject.R;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

Context context;
List <SubCategoryListResponse.SubCategory> subCategories , searchlist;

onDataItemClickListner onDataItemClickListner;
    public SubCategoryAdapter(Context context  , List <SubCategoryListResponse.SubCategory> subCategoryList) {

        this.context = context;
        this.subCategories = subCategoryList;
        this.searchlist = subCategories;

    }



    public  interface onDataItemClickListner{
        void  onDataItemClick(SubCategoryListResponse.SubCategory subCategory);
        void  onDataItemClickDelete(SubCategoryListResponse.SubCategory subCategory);

    }
    public void setUpItemClickListner(onDataItemClickListner dataItemClickListner){
        this.onDataItemClickListner = dataItemClickListner;

    }

    void searchdata(CharSequence charSequence, TextView textView,RecyclerView recyclerView){

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()){
            searchlist = subCategories;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);

            int flag = 0;
            List<SubCategoryListResponse.SubCategory> subCategoryArrayList = new ArrayList<>();
            for (SubCategoryListResponse.SubCategory row : subCategories){
                if (row.getSubcategoryName().toLowerCase().contains(charString.toLowerCase())){
                    subCategoryArrayList.add(row);
                    flag = 1;
                }
            }
            if (flag == 1){
                searchlist = subCategoryArrayList;
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
            else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_subcategory ,parent,false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        holder.tv_subcategory_name.setText(searchlist.get(position).getSubcategoryName());

        holder.editButtonSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDataItemClickListner.onDataItemClick(searchlist.get(position));
            }
        });

        holder.btnDeleteSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataItemClickListner.onDataItemClickDelete(searchlist.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }


    public  class  SubCategoryViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_subcategory_name;
        ImageButton editButtonSubCategory , btnDeleteSubCategory;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subcategory_name = itemView.findViewById(R.id.tv_subcategory_name);
            editButtonSubCategory = itemView.findViewById(R.id.btn_subcat_edit);
            btnDeleteSubCategory = itemView.findViewById(R.id.btn_subcat_delete);
        }
    }
}
