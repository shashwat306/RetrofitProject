package com.example.retrofitproject.CommonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse {


    @SerializedName("product_list")
    @Expose
    private List<Product> productList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public  class Product{
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("old_product_image")
        @Expose
        private String oldProductImage;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("product_desc")
        @Expose
        private String productDesc;
        @SerializedName("is_veg")
        @Expose
        private String isVeg;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getOldProductImage() {
            return oldProductImage;
        }

        public void setOldProductImage(String oldProductImage) {
            this.oldProductImage = oldProductImage;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public String getIsVeg() {
            return isVeg;
        }

        public void setIsVeg(String isVeg) {
            this.isVeg = isVeg;
        }

    }
}
