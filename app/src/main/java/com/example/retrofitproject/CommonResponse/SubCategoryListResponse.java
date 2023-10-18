package com.example.retrofitproject.CommonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubCategoryListResponse implements Serializable {

    @SerializedName("sub_category_list")
    @Expose
    private List<SubCategory> subCategoryList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
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


    public  class  SubCategory{
        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("subcategory_name")
        @Expose
        private String subcategoryName;
        @SerializedName("subcategory_status")
        @Expose
        private String subcategoryStatus;

        @SerializedName("user_id")
        @Expose
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubcategoryName() {
            return subcategoryName;
        }

        public void setSubcategoryName(String subcategoryName) {
            this.subcategoryName = subcategoryName;
        }

        public String getSubcategoryStatus() {
            return subcategoryStatus;
        }

        public void setSubcategoryStatus(String subcategoryStatus) {
            this.subcategoryStatus = subcategoryStatus;
        }
    }
}
