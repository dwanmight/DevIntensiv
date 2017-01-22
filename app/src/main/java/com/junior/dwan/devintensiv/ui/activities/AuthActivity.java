package com.junior.dwan.devintensiv.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.junior.dwan.devintensiv.R;
import com.junior.dwan.devintensiv.data.managers.DataManager;
import com.junior.dwan.devintensiv.data.network.res.UserModelRes;

import retrofit2.Call;

/**
 * Created by Might on 14.09.2016.
 */
public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private Button mSignIn;
    private TextView mRememberPassword;
    private EditText mLogin, mPassword;
    private CoordinatorLayout mCoordinatorLayout;
    DataManager mDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mSignIn = (Button) findViewById(R.id.login_btn);
        mRememberPassword = (TextView) findViewёById(R.id.remember_txt);
        mLogin = (EditText) findViewById(R.id.login_email_et);
        mPassword = (EditText) findViewById(R.id.login_password_et);

        mSignIn.setOnClickListener(this);
        mRememberPassword.setOnClickListener(this);

        mDataManager=DataManager.getINSTANCE();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loginSucces();
                break;
            case R.id.remember_txt:
                rememberPassword();
                break;

        }

    }

    private void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void rememberPassword() {
        Intent rememberntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberntent);

    }

    private void loginSucces() {
        showSnackbar("Вход");
    }
}
