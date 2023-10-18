package com.example.retrofitproject.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DataViewHolder> {

    List<CategoryListResponse.Category> categoryList, searchlist;
    Context context;
    onItemClickListner onItemClickListner;

    public MyAdapter(Context context,List<CategoryListResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.searchlist =categoryList;
    }

      public  interface onItemClickListner{
        void  onItemClick(CategoryListResponse.Category category);
        void  onItemClickDelete(CategoryListResponse.Category category);

    }
    public void setUpItemClickListner(onItemClickListner itemClickListner){
        this.onItemClickListner = itemClickListner;

    }
    void searchdata(CharSequence charSequence, TextView textView,RecyclerView recyclerView){

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()){
            searchlist = categoryList;
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);

            int flag = 0;
            List<CategoryListResponse.Category> categoryArrayList = new ArrayList<>();
            for (CategoryListResponse.Category row : categoryList){
                if (row.getCategoryName().toLowerCase().contains(charString.toLowerCase())){
                    categoryArrayList.add(row);
                    flag = 1;
                }
            }
            if (flag == 1){
                searchlist = categoryArrayList;
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
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_category ,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        holder.tv_category_name.setText(searchlist.get(position).getCategoryName());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClick(searchlist.get(position));
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClickDelete(searchlist.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }

    public  class DataViewHolder extends RecyclerView.ViewHolder{

        TextView tv_category_name;
        ImageButton editButton , btnDelete;
         public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            editButton = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
