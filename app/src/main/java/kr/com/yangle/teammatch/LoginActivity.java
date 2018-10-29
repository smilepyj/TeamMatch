package kr.com.yangle.teammatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import kr.com.yangle.teammatch.util.BackPressCloseHandler;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    OAuthLoginButton bt_login_naver;

    LinearLayout ll_login_kakao;

    private OAuthLogin mOAuthLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        bt_login_naver = findViewById(R.id.bt_login_naver);

        ll_login_kakao = findViewById(R.id.ll_login_kakao);

        bt_login_naver.setOAuthLoginHandler(mOAuthLoginHandler);

        ll_login_kakao.setOnClickListener(mOnClickListener);

        initNaverLogin();
    }

    /**
     * Back Button Listener
     * 프로그램 종료
     * Created by maloman72 on 2018-10-05
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mBackPressCloseHandler.onBackPressed();
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_login_kakao :
                    GoMain();
                    break;
                default :
                    break;
            }
        }
    };

    private void GoMain() {
        Intent mIntent = new Intent(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void initNaverLogin() {
        mOAuthLogin = OAuthLogin.getInstance();
        mOAuthLogin.init(mContext, getString(R.string.login_naver_client_id), getString(R.string.login_naver_client_secret), getString(R.string.app_name));
    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLogin.getAccessToken(mContext);
                String refreshToken = mOAuthLogin.getRefreshToken(mContext);
                long expiresAt = mOAuthLogin.getExpiresAt(mContext);
                String tokenType = mOAuthLogin.getTokenType(mContext);

                Log.e(TAG, "accessToken - " + accessToken + ", refreshToken - " + refreshToken + ", expiresAt - " + expiresAt + ", tokenType - " + tokenType);
            } else {
                String errorCode = mOAuthLogin.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLogin.getLastErrorDesc(mContext);

                Log.e(TAG, "errorCode - " + errorCode + ", errorDesc" + errorDesc);
            }
        }
    };
}
