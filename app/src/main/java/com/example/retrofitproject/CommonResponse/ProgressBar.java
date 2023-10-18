package com.example.retrofitproject.CommonResponse;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

import com.example.retrofitproject.R;

public class ProgressBar {

    Context context;
    Dialog dialog;


    public ProgressBar(Context context) {
        this.context = context;

        if(dialog == null){
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.item_progressbar);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
        }
    }

    public void  setLoading(){
        dialog.show();
    }
    public  void stopLoading(){
        dialog.dismiss();

    }
}
