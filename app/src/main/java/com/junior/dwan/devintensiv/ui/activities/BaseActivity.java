package com.junior.dwan.devintensiv.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.junior.dwan.devintensiv.R;
import com.junior.dwan.devintensiv.utils.ConstantManager;

/**
 * Created by Might on 07.09.2016.
 */
public class BaseActivity extends AppCompatActivity {
    static final String TAG= ConstantManager.DEV_PREFIX+"BaseActivity";
    protected ProgressDialog mProgressDialog;

    public void showProgress(){
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(this, R.style.Custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    public void hideProgress(){
        if(mProgressDialog!=null){
            if(mProgressDialog.isShowing()) mProgressDialog.hide();
        }

    }

    public void showError(String message, Error error){
        showToast(message);
        Log.e(TAG,String.valueOf(error));
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
