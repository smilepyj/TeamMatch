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

import org.json.JSONObject;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.BackPressCloseHandler;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    private Service mService;

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

        mService = new Service(mContext);

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
                    mApplicationTM.makeToast(mContext, getString(R.string.login_kakao_not_support));
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

                mService.naverSearchProfile(naverSearchProfile_Listener, mOAuthLogin);
            } else {
                String errorCode = mOAuthLogin.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLogin.getLastErrorDesc(mContext);

                Log.e(TAG, "errorCode - " + errorCode + ", errorDesc" + errorDesc);
            }
        }
    };

    ResponseListener naverSearchProfile_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    Log.e(TAG, mJSONObject.toString());

                    if(mContext.getString(R.string.login_naver_success).equals(mJSONObject.get(getString(R.string.login_naver_result_code)))) {
                        JSONObject mResult = mJSONObject.getJSONObject(mContext.getString(R.string.login_naver_response));

                        if(!"".equals(mResult.get(mContext.getString(R.string.login_naver_result_email)))) {
                            mApplicationTM.setUserEmail(mResult.get(mContext.getString(R.string.login_naver_result_email)).toString());
                            mApplicationTM.setUserName(mResult.get(mContext.getString(R.string.login_naver_result_name)).toString());

                            mService.userLogin(userLogin_Listener, mApplicationTM.getUserEmail());
                        }
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "naverSearchProfile_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };

    /**
     * userLogin Service Listener
     * Created by maloman72 on 2018-11-02
     * */
    ResponseListener userLogin_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                        JSONObject mResult = mJSONObject.getJSONObject(mContext.getString(R.string.result_data));

                        Intent mIntent;

                        if("".equals(mResult.get(getString(R.string.userlogin_result_user_name)))) {
                            mIntent = new Intent(mContext, UserInfoActivity.class);
                            mIntent.putExtra(getString(R.string.user_info_intent_extra), getString(R.string.user_info_type_input));
                        } else {
                            mIntent = new Intent(mContext, MainActivity.class);
                        }

                        startActivity(mIntent);
                        finish();
                    } else {
                        mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "userLogin_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
