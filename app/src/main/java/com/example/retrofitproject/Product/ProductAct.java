package com.example.retrofitproject.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.CommonResponse.CommonResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.ProductListResponse;
import com.example.retrofitproject.CommonResponse.ProgressBar;
import com.example.retrofitproject.CommonResponse.SubCategoryListResponse;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.R;
import com.example.retrofitproject.SubCategory.AddSubCategoryAct;
import com.example.retrofitproject.SubCategory.SubCategoryAct;
import com.example.retrofitproject.SubCategory.SubCategoryAdapter;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ProductAct extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    AppCompatSpinner spinner_Category, spinner_SubCategory;
    RecyclerView rvProduct;
    RestCall restCall;
    ProgressBar progressBar;
    ProductAdapter productAdapter;
    PreferenceManger preferenceManger;
    String user_id, category_Id, subCat_id, product_id;

    List<String> sub_Category_name, sub_Category_id, category_id, categoryNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        floatingActionButton = findViewById(R.id.btn_product_float);
        spinner_Category = findViewById(R.id.spinner_category);
        spinner_SubCategory = findViewById(R.id.spinner_subcategory);
        rvProduct = findViewById(R.id.rv_product_data);
        progressBar = new ProgressBar(this);
        sub_Category_name = new ArrayList<>();
        sub_Category_id = new ArrayList<>();
        category_id = new ArrayList<>();
        categoryNames = new ArrayList<>();
        preferenceManger = new PreferenceManger(this);
        user_id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);


        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductAct.this, AddProductActivity.class);
                intent.putExtra("category_Id", category_Id);
                intent.putExtra("subCat_id", subCat_id);
                startActivity(intent);
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (category_Id == null){
            getCategories();
        }
        else {
            GetProduct(subCat_id);
        }

    }


    private void getCategories() {
        restCall.GetCategory("getCategory", user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) &&
                                        categoryListResponse.getCategoryList() != null &&
                                        categoryListResponse.getCategoryList().size() > 0) {

                                    categoryNames.clear();
                                    category_id.clear();
                                    categoryNames.add("Select Category");

                                    for (int i = 0; i < categoryListResponse.getCategoryList().size(); i++) {
                                        categoryNames.add(categoryListResponse.getCategoryList().get(i).getCategoryName());
                                    }


                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProductAct.this,
                                            android.R.layout.simple_spinner_dropdown_item, categoryNames);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_Category.setAdapter(arrayAdapter);

                                    spinner_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                                            for (int i = 0; i < categoryListResponse.getCategoryList().size(); i++) {

                                                if (categoryListResponse.getCategoryList().get(i).getCategoryName() == adapterView.getSelectedItem()) {

                                                    category_Id = categoryListResponse.getCategoryList().get(i).getCategoryId();
                                                    getSub_Category(category_Id);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });

                                } else {
                                    Toast.makeText(ProductAct.this, "No Categories Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    public void getSub_Category(String categoryId) {

        restCall.getSubCategory("getSubCategory", categoryId, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Log.d("APIDEBUG", "Status: " + subCategoryListResponse.getMessage());

                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) &&
                                        subCategoryListResponse.getSubCategoryList() != null &&
                                        subCategoryListResponse.getSubCategoryList().size() > 0) {

                                    sub_Category_name.clear();
                                    sub_Category_id.clear();
                                    sub_Category_name.add("Select SubCategory");

                                    for (int i = 0; i < subCategoryListResponse.getSubCategoryList().size(); i++) {
                                        sub_Category_name.add(subCategoryListResponse.getSubCategoryList().get(i).getSubcategoryName());
                                    }


                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ProductAct.this,
                                            android.R.layout.simple_spinner_dropdown_item, sub_Category_name);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_SubCategory.setAdapter(arrayAdapter);

                                    spinner_SubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            for (int i = 0; i < subCategoryListResponse.getSubCategoryList().size(); i++) {

                                                if (subCategoryListResponse.getSubCategoryList().get(i).getSubcategoryName() == adapterView.getSelectedItem()) {

                                                    subCat_id = subCategoryListResponse.getSubCategoryList().get(i).getSubCategoryId();
                                                    GetProduct(subCat_id);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(ProductAct.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });
    }


    public void GetProduct(String subCategoryID) {

        restCall.GetProduct("getProduct", category_Id, subCategoryID, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ProductListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ProductListResponse productListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) &&
                                        productListResponse.getProductList() != null &&
                                        productListResponse.getProductList().size() > 0) {

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductAct.this);
                                    productAdapter = new ProductAdapter(ProductAct.this, productListResponse.getProductList());
                                    rvProduct.setLayoutManager(layoutManager);
                                    rvProduct.setAdapter(productAdapter);

                                    productAdapter.setUpItemClickListener(new ProductAdapter.onProductItemClickListener() {
                                        @Override
                                        public void onProductItemClick(ProductListResponse.Product product) {

                                            Intent intent = new Intent(ProductAct.this, AddProductActivity.class);
                                            intent.putExtra("category_Id", category_Id);
                                            intent.putExtra("subCat_id", subCat_id);
                                            intent.putExtra("product_Id", product.getProductId());
                                            intent.putExtra("getProductName", product.getProductName());
                                            intent.putExtra("getProductPrice", product.getProductPrice());
                                            intent.putExtra("getProductDesc", product.getProductDesc());
                                            intent.putExtra("getOldProductImage", product.getOldProductImage());
                                            intent.putExtra("getIsVeg", product.getIsVeg());
                                            intent.putExtra("getProductImage", product.getProductImage());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onProductItemClickDelete(ProductListResponse.Product product) {
                                            product_id = product.getProductId();
                                            deleteProduct(product_id);

                                        }
                                    });

                                } else {
                                    Log.d("API_DEBUG", "No Data Found");
                                    Toast.makeText(ProductAct.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                });
    }


    public void deleteProduct(String productId) {
        restCall.DeleteProduct("DeleteProduct", productId, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)) {
                                    getCategories();
                                    Toast.makeText(ProductAct.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProductAct.this, "Not able to Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}
