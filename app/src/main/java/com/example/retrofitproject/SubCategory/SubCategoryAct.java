package com.example.retrofitproject.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitproject.Category.Add_Data;
import com.example.retrofitproject.Category.CategoryActivity;
import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.CommonResponse.CommonSubCategoryResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.SubCategoryListResponse;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.R;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SubCategoryAct extends AppCompatActivity {

    AppCompatSpinner spinner_subcategory;
    RecyclerView rv_subCategory;
    FloatingActionButton actionButton;
    SubCategoryAdapter subCategoryAdapter;
    RestCall rest_Call;
    EditText edt_searchs;
    TextView tVMsg;
    ImageView img_close;
    String category_Id , subCat_id , user_id;
    PreferenceManger preferenceManger;
    List<String> sub_Category_list, category_id ,categoryNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        rv_subCategory = findViewById(R.id.rv_subcategory_data);
        actionButton = findViewById(R.id.btn_subcat_float);
        tVMsg = findViewById(R.id.txt_message);
        edt_searchs = findViewById(R.id.edt_Search);
        img_close = findViewById(R.id.img_Close);
        img_close.setVisibility(View.GONE);
        spinner_subcategory = findViewById(R.id.spinner_subcategory);
//        sub_Category_list = new ArrayList<>();
       category_id = new ArrayList<>();
       categoryNames = new ArrayList<>();
        preferenceManger = new PreferenceManger(this);
        user_id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);


        //actionButton.setVisibility(View.GONE);
       rest_Call = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        edt_searchs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (subCategoryAdapter != null) {
                    if (!edt_searchs.getText().toString().isEmpty()) {
                        img_close.setVisibility(View.VISIBLE);
                    } else {

                        img_close.setVisibility(View.GONE);
                    }

                    subCategoryAdapter.searchdata(charSequence, tVMsg, rv_subCategory);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_close.setVisibility(View.GONE);
                edt_searchs.setText("");
            }
        });


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubCategoryAct.this, AddSubCategoryAct.class);
               intent.putExtra("category_Id" , category_Id);
                startActivity(intent);
            }
        });

        getCategories();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSub_Category(category_Id);

    }
    private void getCategories() {
        rest_Call.GetCategory("getCategory" , user_id)
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
                                Toast.makeText(SubCategoryAct.this, "No Internet", Toast.LENGTH_SHORT).show();
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

                                    for(int i = 0; i< categoryListResponse.getCategoryList().size(); i++){
                                        categoryNames.add(categoryListResponse.getCategoryList().get(i).getCategoryName());
                                    }


                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SubCategoryAct.this,
                                            android.R.layout.simple_spinner_dropdown_item, categoryNames);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_subcategory.setAdapter(arrayAdapter);


                                    spinner_subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                                            for(int i = 0; i< categoryListResponse.getCategoryList().size();i++){

                                                if(categoryListResponse.getCategoryList().get(i).getCategoryName() == adapterView.getSelectedItem()){

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
                                    Toast.makeText(SubCategoryAct.this, "No Categories Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    public void getSub_Category(String categoryId) {

        rest_Call.getSubCategory("getSubCategory", categoryId,user_id)
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
                                Toast.makeText(SubCategoryAct.this, "No Internet", Toast.LENGTH_SHORT).show();
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


                                    //actionButton.setVisibility(View.VISIBLE);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubCategoryAct.this);
                                    subCategoryAdapter = new SubCategoryAdapter(SubCategoryAct.this, subCategoryListResponse.getSubCategoryList());
                                    rv_subCategory.setLayoutManager(layoutManager);
                                    rv_subCategory.setAdapter(subCategoryAdapter);


                                    subCategoryAdapter.setUpItemClickListner(new SubCategoryAdapter.onDataItemClickListner() {
                                        @Override
                                        public void onDataItemClick(SubCategoryListResponse.SubCategory subCategory) {

                                            Intent intent = new Intent(SubCategoryAct.this, AddSubCategoryAct.class);
                                            intent.putExtra("category_Id",category_Id);
                                            intent.putExtra("getSubCategoryId", subCategory.getSubCategoryId());
                                            intent.putExtra("getSubcategoryName", subCategory.getSubcategoryName());
//                                            intent.putExtra("getUserId" ,user_id);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onDataItemClickDelete(SubCategoryListResponse.SubCategory subCategory) {

                                            subCat_id = subCategory.getSubCategoryId();
                                            delete_SubCategory(subCat_id);

                                        }
                                    });

                                } else {
                                    Log.d("API_DEBUG", "No Data Found");
                                    //rv_subCategory.setVisibility(View.GONE);
                                    Toast.makeText(SubCategoryAct.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                });
    }


    public  void delete_SubCategory(String subCategoryId){
        rest_Call.deleteSubCategory("DeleteSubCategory",subCategoryId,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommonSubCategoryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SubCategoryAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNext(CommonSubCategoryResponse commonSubCategoryResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonSubCategoryResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    getCategories();
                                    Toast.makeText(SubCategoryAct.this, commonSubCategoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SubCategoryAct.this, "Not able to Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}
