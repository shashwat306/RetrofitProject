package com.example.retrofitproject.Category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.CommonResponse.CommonResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.R;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    RestCall restCall;
    EditText editTextsearch;
    TextView textViewMsg;
    ImageView im_close;
    String categoryId , user_id;
    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView = findViewById(R.id.rv_data);
        floatingActionButton = findViewById(R.id.btn_float);
        textViewMsg = findViewById(R.id.txtmessage);
        editTextsearch = findViewById(R.id.edtSearch);
        im_close = findViewById(R.id.imgClose);
        im_close.setVisibility(View.GONE);
        preferenceManger = new PreferenceManger(this);
        user_id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);



        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL,VariableBag.API_KEY);
        editTextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (myAdapter != null) {
                    if (!editTextsearch.getText().toString().isEmpty()) {
                        im_close.setVisibility(View.VISIBLE);
                    } else {

                        im_close.setVisibility(View.GONE);
                    }

                    myAdapter.searchdata(charSequence, textViewMsg, recyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im_close.setVisibility(View.GONE);
                editTextsearch.setText("");
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, Add_Data.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategory();

    }

    public  void  categoryDelete(){
        restCall.DeleteCategory("DeleteCategory",user_id,categoryId)
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
                                Toast.makeText(CategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    getCategory();
                                    Toast.makeText(CategoryActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CategoryActivity.this, "Not able to Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
public void getCategory(){

        restCall.GetCategory("getCategory",user_id)
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
                                Toast.makeText(CategoryActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)&& categoryListResponse.getCategoryList() != null && categoryListResponse.getCategoryList().size() >0){
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryActivity.this);
                                    myAdapter = new MyAdapter(CategoryActivity.this,categoryListResponse.getCategoryList());
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(myAdapter);

                                    myAdapter.setUpItemClickListner(new MyAdapter.onItemClickListner() {
                                        @Override
                                        public void onItemClick(CategoryListResponse.Category category) {

                                            Intent intent = new Intent(CategoryActivity.this, Add_Data.class);
                                            intent.putExtra("getCategoryId", category.getCategoryId());
                                            intent.putExtra("getCategoryName", category.getCategoryName());
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onItemClickDelete(CategoryListResponse.Category category) {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                                            builder.setTitle("Warning");
                                            builder.setMessage("Are you sure you want to delete");
                                            builder.setPositiveButton("Yes ,Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    categoryId = category.getCategoryId();
                                                    categoryDelete();
                                                }
                                            });
                                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.show();
                                        }
                                    });

                                }else {
                                    Toast.makeText(CategoryActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
       }
}