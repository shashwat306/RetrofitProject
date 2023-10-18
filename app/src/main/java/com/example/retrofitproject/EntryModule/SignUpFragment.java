package com.example.retrofitproject.EntryModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.retrofitproject.R;

public class SignUpFragment extends Fragment {

    EditText ed_fname , ed_lname ,ed_email,ed_pass ;
    Button btn_signUp;

    onButtonSignUpClick onButtonSignUpClick;


    public interface onButtonSignUpClick {

      void  onClickSignUp(String fName , String lName , String email , String password);
    }

    public  void setUpButtonSignUpInterface(onButtonSignUpClick buttonSignUpClick){
        this.onButtonSignUpClick = buttonSignUpClick;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
    ed_fname = view.findViewById(R.id.edFname);
    ed_lname = view.findViewById(R.id.edLname);
    ed_email = view.findViewById(R.id.edEmail);
    ed_pass = view.findViewById(R.id.ed_pass);
    btn_signUp = view.findViewById(R.id.btnSignUp);

    btn_signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String first_name = ed_fname.getText().toString();
            String last_name = ed_lname.getText().toString();
            String emailAddress = ed_email.getText().toString();
            String passWord = ed_pass.getText().toString();

            if(first_name.equalsIgnoreCase("")){
                ed_fname.setError("Enter the first name ");
                ed_fname.requestFocus();
            }else if(last_name.equalsIgnoreCase("")){
                ed_lname.setError("Enter the last name ");
                ed_lname.requestFocus();
            }else if(emailAddress.equalsIgnoreCase("")){
                ed_email.setError("Enter the email ");
                ed_email.requestFocus();
            }  else if(passWord.equalsIgnoreCase("")){
                ed_pass.setError("Enter password ");
                ed_pass.requestFocus();
            }else if(isValidEmail(emailAddress) && isValidPassword(passWord)) {
                onButtonSignUpClick.onClickSignUp(first_name,last_name,emailAddress,passWord);
            }
        }
    });
        return view ;
    }

    public  boolean isValidEmail(String email){
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";

        if (email.matches(emailPattern)) {
            return true;
        }else {
            ed_email.setError("Enter a valid email address");
            ed_email.requestFocus();
            return false;
        }
    }
    public boolean isValidPassword(String password){

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{7,}$";

        if (password.matches(passwordPattern)) {
            return true;
        }else {
            ed_pass.setError("Password must be at least 7 characters contain a lowercase,uppercase letter and a special character");
            ed_pass.requestFocus();
            return false;
        }
    }

}


























//
//        btn_signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String first_name = ed_fname.getText().toString();
//                String last_name = ed_lname.getText().toString();
//                String emailAddress = ed_email.getText().toString();
//                String passWord = ed_pass.getText().toString();
//
//                if (isValidEmail(emailAddress) && isValidPassword(passWord)) {
//                    onButtonSignUpClick.onClickSignUp(first_name, last_name, emailAddress, passWord);
//                }
//            }
//        });
//        return view;
//    }
//
//    public boolean isValidEmail(String email) {
//        // Define a regular expression for a valid email address
//        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
//
//        if (email.matches(emailPattern)) {
//            return true;
//        } else {
//            ed_email.setError("Enter a valid email address");
//            ed_email.requestFocus();
//            return false;
//        }
//    }
//
//    public boolean isValidPassword(String password) {
//        // Password should have at least 7 characters and contain a-z, A-Z, 0-9, and a special character
//        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{7,}$";
//
//        if (password.matches(passwordPattern)) {
//            return true;
//        } else {
//            ed_pass.setError("Password must be at least 7 characters long and contain a lowercase letter, an uppercase letter, a digit, and a special character");
//            ed_pass.requestFocus();
//            return false;
//        }
//    }
//}