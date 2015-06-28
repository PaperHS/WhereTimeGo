package com.paper.loginlibrary;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * ,==.              |~~~
 * /  66\             |
 * \c  -_)         |~~~
 * `) (           |
 * /   \       |~~~
 * /   \ \      |
 * ((   /\ \_ |~~~
 * \\  \ `--`|
 * / / /  |~~~
 * ___ (_(___)_|
 * <p/>
 * Created by Paper on 15/6/28 2015.
 */
public class LoginView extends RelativeLayout {

    Button mGetCodeBtn;
    Button mLoginBtn;
    TextInputLayout mPhoneNumInputLayout;
    TextInputLayout mCheckCodeInputLaout;

    public LoginView(Context context) {
        super(context);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGetCodeBtn = (Button) findViewById(R.id.login_getcode_btn);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mPhoneNumInputLayout = (TextInputLayout) findViewById(R.id.login_phone_num);
        mCheckCodeInputLaout = (TextInputLayout) findViewById(R.id.login_phone_verity);
        mPhoneNumInputLayout.setHint(getResources().getString(R.string.logint_phonenum_hint));
        mCheckCodeInputLaout.setHint(getResources().getString(R.string.login_checkcode_hint));
//        mLoginBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("hshs","click");
//            }
//        });
    }

}
