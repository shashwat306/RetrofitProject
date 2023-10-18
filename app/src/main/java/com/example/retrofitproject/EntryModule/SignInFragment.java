package com.example.retrofitproject.EntryModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.retrofitproject.CommonResponse.PreferenceManger;
import com.example.retrofitproject.CommonResponse.VariableBag;
import com.example.retrofitproject.R;

public class SignInFragment extends Fragment {

    EditText edUname , ed_password;
    Button btnSignIn;
    PreferenceManger manger;

    onButtonSignInClick buttonSignInClick;
    public interface onButtonSignInClick{

        void  onClickSignIn( String email , String password);
    }

    public  void  setUpButtonInterface(onButtonSignInClick buttonInterface){
        this.buttonSignInClick = buttonInterface;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        edUname = view.findViewById(R.id.eduname);
        ed_password = view.findViewById(R.id.edpswd);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        manger = new PreferenceManger(getContext());

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edUname.getText().toString().equalsIgnoreCase("")){
                    edUname.setError("Enter the email ");
                    edUname.requestFocus();
                }else if(ed_password.getText().toString().equalsIgnoreCase("")){
                    ed_password.setError("Enter password ");
                    ed_password.requestFocus();
                }else {
                    buttonSignInClick.onClickSignIn(edUname.getText().toString(),ed_password.getText().toString());
                }
            }
        });
        return view ;
    }
}