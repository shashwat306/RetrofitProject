package com.example.retrofitproject.Catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CatalogListResponse;
import com.example.retrofitproject.CommonResponse.CategoryListResponse;
import com.example.retrofitproject.CommonResponse.CommonResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.ProductListResponse;
import com.example.retrofitproject.CommonResponse.ProgressBar;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.Product.AddProductActivity;
import com.example.retrofitproject.Product.ProductAct;
import com.example.retrofitproject.Product.ProductAdapter;
import com.example.retrofitproject.R;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CatalogActivity extends AppCompatActivity {
    RestCall restCall;
    PreferenceManger preferenceManger;
    String user_id;
    RecyclerView recyclerViewCatalog;
    ProgressBar progressBar;

    CatalogAdapter catalogAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        preferenceManger = new PreferenceManger(this);
        progressBar = new ProgressBar(this);
        user_id = preferenceManger.getValuePrefrence(VariableBag.KEY_USERID);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        GetCatalog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setLoading();
        GetCatalog();
    }

    public void GetCatalog() {
        restCall.GetCatalog("GetCatalog", user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CatalogListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.stopLoading();

                                Toast.makeText(CatalogActivity.this, "No Internet", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onNext(CatalogListResponse catalogListResponse) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             progressBar.stopLoading();

                             if(catalogListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE) &&
                                     catalogListResponse.getCategoryList() != null &&
                                     catalogListResponse.getCategoryList().size() > 0 ){

                                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CatalogActivity.this);
                                 catalogAdapter = new CatalogAdapter(catalogListResponse.getCategoryList() , CatalogActivity.this);
                                 recyclerViewCatalog.setLayoutManager(layoutManager);
                                 recyclerViewCatalog.setAdapter(catalogAdapter);

                             }

                         }
                     });
                    }

                });
    }
}