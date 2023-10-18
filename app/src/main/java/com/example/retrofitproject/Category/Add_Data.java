package com.example.retrofitproject.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CommonResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.R;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class Add_Data extends AppCompatActivity {
    EditText editText;
    Button buttonAdd;
    RestCall restCall;
    PreferenceManger preferenceManger;
    String category_id , category_name , user_id;
    Boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        editText = findViewById(R.id.ed);
        buttonAdd = findViewById(R.id.btnadd);
        preferenceManger = new PreferenceManger(this);
        user_id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);


        if (getIntent().getStringExtra("getCategoryId") != null){
            category_id = getIntent().getStringExtra("getCategoryId");
            category_name= getIntent().getStringExtra("getCategoryName");
            buttonAdd.setText("Edit");
            editText.setText(category_name);
            isEditMode = true;
        }
        else {
            isEditMode = false;
            buttonAdd.setText("Add");
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextData = editText.getText().toString();
                if(editTextData.equalsIgnoreCase("")){
                    editText.setError("Enter the data");
                }else {
                    restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL,VariableBag.API_KEY);
                    if(isEditMode == true){
                        categoryedit();
                    }else{

                        categoryadd();
                    }
                }
            }
        });
    }

    public void categoryedit(){
        restCall.EditCategory("EditCategory",editText.getText().toString().trim(),category_id,user_id)
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
                                Toast.makeText(Add_Data.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonResponse.getStatus().equals(VariableBag.SUCCESS_CODE)){
                                    Toast.makeText(Add_Data.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(Add_Data.this, "Not able to edit", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });



    }
    public  void categoryadd(){
     restCall.AddCategory("AddCategory",user_id,editText.getText().toString().trim())
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
                             Toast.makeText(Add_Data.this, "No Internet", Toast.LENGTH_SHORT).show();
                         }
                     });


                 }

                 @Override
                 public void onNext(CommonResponse commonResponse) {

                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             if (commonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    editText.setText("");
                                    finish();
                                }
                                Toast.makeText(Add_Data.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     });
                 }
             });
    }
}



