package com.junior.dwan.devintensiv.utils;

import android.widget.EditText;

/**
 * Created by Might on 12.09.2016.
 */
public class ValidateEditText {

    public static boolean isPhoneValid(EditText editText){
        if(editText.getText().length()>11&&editText.getText().length()<20)
            return true;
        else return false;
    }

    public final static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
