package com.example.retrofitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofitproject.Catalog.CatalogActivity;
import com.example.retrofitproject.Category.CategoryActivity;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.EntryModule.LoginAct;
import com.example.retrofitproject.Product.ProductAct;
import com.example.retrofitproject.SubCategory.SubCategoryAct;

public class HomeActivity extends AppCompatActivity {

    Button btn_Category , btn_SubCategory , btn_Product , btn_Catalog, btn_logout;
    TextView textViewName;
    PreferenceManger preferenceManger;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewName = findViewById(R.id.txtName);
        btn_logout = findViewById(R.id.btn_logout);
        btn_Category = findViewById(R.id.btnCategory);
        btn_SubCategory = findViewById(R.id.btnSubCategory);
        btn_Product = findViewById(R.id.btnProduct);
        btn_Catalog = findViewById(R.id.btnCatalog);
        preferenceManger = new PreferenceManger(this);
        String name = preferenceManger.getValuePrefrence(VariableBag.KEY_FirstNAME);
        String lastname = preferenceManger.getValuePrefrence(VariableBag.KEY_LastName);

        textViewName.setText(name +" "+ lastname);

        btn_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                startActivity(intent);

            }
        });
        btn_SubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SubCategoryAct.class);
                startActivity(intent);

            }
        });
        btn_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductAct.class);
                startActivity(intent);

            }
        });
        btn_Catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CatalogActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManger.getLogInSession(" ",false);
                Intent intent = new Intent(HomeActivity.this, LoginAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }




}
