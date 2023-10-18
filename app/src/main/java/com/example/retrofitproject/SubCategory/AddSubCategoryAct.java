package com.example.retrofitproject.SubCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.CommonResponse.CommonSubCategoryResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.R;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddSubCategoryAct extends AppCompatActivity {

    EditText ed_subCat_name;
    Button btnAddCat;

    RestCall restCall;
    String categoryID  , subCategoryID , subCategoryName , user_Id;
    PreferenceManger preferenceManger;
    AppCompatSpinner spinnerId;
    List<String> categoryNames , category_Id;
    Boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);

        ed_subCat_name = findViewById(R.id.ed_subName);
        btnAddCat = findViewById(R.id.btn_add_data);
        spinnerId = findViewById(R.id.spinner_add);
        categoryNames = new ArrayList<>();
        category_Id = new ArrayList<>();
        preferenceManger = new PreferenceManger(this);
        user_Id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);


        if (getIntent().getStringExtra("getSubCategoryId") != null) {
            categoryID = getIntent().getStringExtra("category_Id");
            subCategoryID = getIntent().getStringExtra("getSubCategoryId");
            subCategoryName = getIntent().getStringExtra("getSubcategoryName");
            btnAddCat.setText(R.string.resource_edit);
            ed_subCat_name.setText(subCategoryName);
            isEditMode = true;

        } else {
            isEditMode = false;
            btnAddCat.setText(R.string.addbtn);
        }
        btnAddCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                categoryID = ((Intent) intent).getExtras().getString("category_Id");

                if (ed_subCat_name.getText().toString().isEmpty()) {
                    Toast.makeText(AddSubCategoryAct.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
                } else {

                    restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
                    if (isEditMode == true) {
                        subCategoryEdit();
                    } else {
                        subCategoryAdd();
                    }
                }
            }
        });
    }
    public void subCategoryAdd(){

        restCall.addSubCategory("AddSubCategory",categoryID, ed_subCat_name.getText().toString(), user_Id)
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
                                Toast.makeText(AddSubCategoryAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonSubCategoryResponse commonSubCategoryResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonSubCategoryResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    ed_subCat_name.setText("");
                                    finish();
                                }
                                Toast.makeText(AddSubCategoryAct.this, commonSubCategoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    public  void subCategoryEdit(){
        restCall.editSubCategory("EditSubCategory", ed_subCat_name.getText().toString().trim(),subCategoryID,categoryID, user_Id)
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
                                Toast.makeText(AddSubCategoryAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CommonSubCategoryResponse commonSubCategoryResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonSubCategoryResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    Toast.makeText(AddSubCategoryAct.this, commonSubCategoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(AddSubCategoryAct.this, "Not able to edit", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}