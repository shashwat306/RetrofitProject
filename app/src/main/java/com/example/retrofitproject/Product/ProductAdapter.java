package com.example.retrofitproject.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.retrofitproject.CommonResponse.ProductListResponse;
import com.example.retrofitproject.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductDataViewHolder> {

    Context context;
    List<ProductListResponse.Product> productList;

    onProductItemClickListener onProductItemClickListener;
    public ProductAdapter(Context context, List<ProductListResponse.Product> productList) {
        this.context = context;
        this.productList = productList;
    }


    public  interface onProductItemClickListener{
        void  onProductItemClick(ProductListResponse.Product product);
        void  onProductItemClickDelete(ProductListResponse.Product product);

    }
    public void setUpItemClickListener(onProductItemClickListener clickListener){
        this.onProductItemClickListener = clickListener;

    }

    @NonNull
    @Override
    public ProductDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product,parent,false);
        return new ProductDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDataViewHolder holder, int position) {
        holder.txtName.setText(productList.get(position).getProductName());
        holder.txtPrice.setText(productList.get(position).getProductPrice());
        holder.txtDesc.setText(productList.get(position).getProductDesc());

        Glide.with(context)
                .load(productList.get(position).getProductImage())
                .placeholder(R.drawable.ic_imageholder)
                .error(R.drawable.ic_image_not_supported)
                .into(holder.imageViewProduct);
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductItemClickListener.onProductItemClickDelete(productList.get(position));
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onProductItemClickListener.onProductItemClick(productList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductDataViewHolder extends RecyclerView.ViewHolder{

        TextView txtName,txtPrice ,txtDesc;
        ImageButton btnDel,btnEdit;
        ImageView imageViewProduct;
        public ProductDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.ProductNameTextView);
            txtPrice = itemView.findViewById(R.id.textViewPrice);
            txtDesc = itemView.findViewById(R.id.ProductDescTextView);
            btnDel = itemView.findViewById(R.id.btn_product_delete);
            btnEdit = itemView.findViewById(R.id.btn_product_edit);
            imageViewProduct = itemView.findViewById(R.id.img_productt);
        }
    }

}
