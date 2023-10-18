package com.example.retrofitproject.CommonResponse;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManger {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public PreferenceManger(Context context) {
        sharedPreferences = context.getSharedPreferences(VariableBag.KEY_ref,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public  void setKeyPrefrence(String key , String value){
        editor.putString(key, value).commit();
    }
    public  String getValuePrefrence(String key ){
        return  sharedPreferences.getString(key,"Invalid input");
    }
    public  void removeValuePrefrence(String key ){
        editor.remove(key);
        editor.commit();
    }

    public  void setLogInSession(String key, boolean b) {

        editor.putBoolean(key,b);
        editor.commit();
    }
    public  Boolean getLogInSession(String key, boolean b) {
        return sharedPreferences.getBoolean(key,b);
    }
}
