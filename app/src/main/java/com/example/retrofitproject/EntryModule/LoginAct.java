package com.example.retrofitproject.EntryModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitproject.CommonResponse.CommonUserResponse;
import com.example.retrofitproject.CommonResponse.LoginResponse;
import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.ProgressBar;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.HomeActivity;
import com.example.retrofitproject.R;
import com.example.retrofitproject.network.RestCall;
import com.example.retrofitproject.network.RestClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class LoginAct extends AppCompatActivity {

    TabLayout tabLayout;

    RestCall restCall;
    TextView txt_greeting, txt_msg;
    ViewPager2 viewPager2;
    ProgressBar progressBar;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    PreferenceManger preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager2);
        txt_greeting = findViewById(R.id.txt_greet);
        txt_msg = findViewById(R.id.txt_msg);
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        preferenceManger = new PreferenceManger(LoginAct.this);
        viewPager2.setAdapter(new ViewPagerAdapter(LoginAct.this));
        progressBar = new ProgressBar(this);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL,VariableBag.API_KEY);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (position == 0){
                    tab.setText("Sign In");

                }else{
                    tab.setText("Sign Up");
                }
            }
        }).attach();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        txt_greeting.setText(R.string.welcome_back);
                        txt_msg.setText(R.string.log_in_to_your_account_to_get_an_update);
                    } else {
                        txt_greeting.setText(R.string.hello);
                        txt_msg.setText(R.string.register_to_create_new_your_account);
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        signInFragment.setUpButtonInterface(new SignInFragment.onButtonSignInClick() {
            @Override
            public void onClickSignIn(String email, String password) {

                LoginUser(email,password);
            }
        });

        signUpFragment.setUpButtonSignUpInterface(new SignUpFragment.onButtonSignUpClick() {
            @Override
            public void onClickSignUp(String fName, String lName, String email, String password) {
                addUser( fName,  lName,  email, password);
            }
        });

    }
    class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position == 0){
                return signInFragment;

            }
            return signUpFragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }

    }

    public  void LoginUser(String email, String password){
        restCall.LoginUser("LoginUser",email,password)
                .subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).
                subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.stopLoading();

                                Toast.makeText(LoginAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse ) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.stopLoading();

                                if (loginResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                   preferenceManger.setKeyPrefrence(VariableBag.KEY_FirstNAME,loginResponse.getFirst_name());
                                   preferenceManger.setKeyPrefrence(VariableBag.KEY_LastName ,loginResponse.getLast_name());
                                   preferenceManger.setLogInSession(VariableBag.SESSION_CHECK,true);
                                   preferenceManger.setKeyPrefrence(VariableBag.KEY_USERID,loginResponse.getUser_id());
                                    Intent intent = new Intent(LoginAct.this , HomeActivity.class);
                                    startActivity(intent);
                                }
                                Toast.makeText(LoginAct.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    public  void  addUser(String fName, String lName, String email, String password){
        restCall.AddUser("AddUser",fName ,lName ,email,password)
                .subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).
                subscribe(new Subscriber<CommonUserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginAct.this, "No Internet", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNext(CommonUserResponse commonUserResponse) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (commonUserResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_CODE)){
                                    finish();
                                }
                                Toast.makeText(LoginAct.this, commonUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}