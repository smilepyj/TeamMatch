package kr.com.yangle.teammatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.BackPressCloseHandler;
import kr.com.yangle.teammatch.util.DialogAlertActivity;
import kr.com.yangle.teammatch.util.DialogPrivacyActivity;
import kr.com.yangle.teammatch.util.DialogTermServiceActivity;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    private Service mService;

    OAuthLoginButton bt_login_naver;

    LoginButton bt_login_kakao;

    private OAuthLogin mOAuthLogin;

    private SessionCallback callback;

    LinearLayout ll_login_kakao;
    TextView tv_login_term_service, tv_login_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        mService = new Service(this);

        ll_login_kakao = findViewById(R.id.ll_login_kakao);
        bt_login_naver = findViewById(R.id.bt_login_naver);
        bt_login_kakao = findViewById(R.id.bt_login_kakao);

        tv_login_term_service = findViewById(R.id.tv_login_term_service);
        tv_login_privacy = findViewById(R.id.tv_login_privacy);

        bt_login_naver.setOAuthLoginHandler(mOAuthLoginHandler);

        bt_login_kakao.performClick();

        tv_login_term_service.setOnClickListener(mOnClickListener);
        tv_login_privacy.setOnClickListener(mOnClickListener);

        LayoutSet();

        initNaverLogin();
        initKakaoLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
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
            Intent mIntent;

            switch (v.getId()) {
                case R.id.bt_login_kakao :
                    mApplicationTM.makeToast(mContext, getString(R.string.login_kakao_not_support));
                    break;
                case R.id.tv_login_term_service :
                    mIntent = new Intent(mContext, DialogTermServiceActivity.class);
                    mContext.startActivity(mIntent);
                    break;
                case R.id.tv_login_privacy :
                    mIntent = new Intent(mContext, DialogPrivacyActivity.class);
                    mContext.startActivity(mIntent);
                    break;
                default :
                    break;
            }
        }
    };

    private void LayoutSet() {
        DisplayMetrics mDisplayMetrics_margin = getResources().getDisplayMetrics();
        int mSmallMarginsSize = Math.round(15 * mDisplayMetrics_margin.density);
        int mMiddleMarginsSize = Math.round(20 * mDisplayMetrics_margin.density);
        int mLargeMarginsSize = Math.round(40 * mDisplayMetrics_margin.density);

        Display mDisplay = getWindowManager().getDefaultDisplay();
        double mRealWidth, mRealHeight;

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        mDisplay.getRealMetrics(mDisplayMetrics);
        mRealWidth = mDisplayMetrics.widthPixels;
        mRealHeight = mDisplayMetrics.heightPixels;

        LinearLayout.LayoutParams ll_login_kakao_param = (LinearLayout.LayoutParams) ll_login_kakao.getLayoutParams();

        double mScreenRate = mRealHeight / mRealWidth;

        if (mScreenRate <= 1.6) {
            ll_login_kakao_param.setMargins(mMiddleMarginsSize, 0, mMiddleMarginsSize, 0);
        } else if (mScreenRate >= 2) {
            ll_login_kakao_param.setMargins(mLargeMarginsSize, 0, mLargeMarginsSize, 0);
        } else {
            ll_login_kakao_param.setMargins(mSmallMarginsSize, 0, mSmallMarginsSize, 0);
        }
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

    private void initKakaoLogin() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public class SessionCallback implements ISessionCallback {

        // 로그인에 성공한 상태
        @Override
        public void onSessionOpened() {
            requestMe();
        }

        // 로그인에 실패한 상태
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
        }

        // 사용자 정보 요청
        public void requestMe() {
            List<String> keys = new ArrayList<>();
            keys.add("properties.nickname");
            keys.add("properties.profile_image");
            keys.add("kakao_account.email");

            UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.d(TAG, "onFailure : " + errorResult.getErrorMessage());
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e(TAG, "onSessionClosed : " + errorResult.getErrorMessage());
                }

                @Override
                public void onSuccess(MeV2Response response) {
                    try {
                        Log.d(TAG, "user id : " + response.getId());
                        Log.d(TAG, "email: " + response.getKakaoAccount().getEmail());
                        Log.d(TAG, "profile image: " + response.getProfileImagePath());

                        double id = response.getId();

                        if(!"".equals(id)) {
                            mApplicationTM.setLoginType(mContext.getString(R.string.login_kakao_login_type));
                            mApplicationTM.setUserId(mContext.getString(R.string.login_kakao_login_type) + "_" + new DecimalFormat("###.#####").format(id));
                            mApplicationTM.setUserEmail(response.getKakaoAccount().getEmail());
                            mApplicationTM.setUserName(response.getNickname());

                            mService.userLogin(userLogin_Listener, mApplicationTM.getUserId(), mApplicationTM.getUserEmail());
                        }
                    } catch (Exception e) {
                        mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                        Log.e(TAG, "MeV2ResponseCallback onSuccess - " + e);
                    } finally {
                        mApplicationTM.stopCustomProgressDialog();
                    }
                }
            });
        }
    }

    ResponseListener naverSearchProfile_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    Log.e(TAG, mJSONObject.toString());

                    if(mContext.getString(R.string.login_naver_success).equals(mJSONObject.get(getString(R.string.login_naver_result_code)))) {
                        JSONObject mResult = mJSONObject.getJSONObject(mContext.getString(R.string.login_naver_response));

                        if(!"".equals(mResult.get(mContext.getString(R.string.login_naver_result_id)))) {
                            mApplicationTM.setLoginType(mContext.getString(R.string.login_naver_login_type));
                            mApplicationTM.setUserId(mContext.getString(R.string.login_naver_login_type) + "_" + mResult.get(mContext.getString(R.string.login_naver_result_id)).toString());
                            mApplicationTM.setUserEmail(mResult.get(mContext.getString(R.string.login_naver_result_email)).toString());
                            mApplicationTM.setUserName(mResult.get(mContext.getString(R.string.login_naver_result_name)).toString());

                            mService.userLogin(userLogin_Listener, mApplicationTM.getUserId(), mApplicationTM.getUserEmail());
                        }
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "naverSearchProfile_Listener - " + e);
            } finally {
                mApplicationTM.stopCustomProgressDialog();
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

                        mApplicationTM.setTeamId(mResult.get(mContext.getString(R.string.userlogin_result_team_id)).toString());

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
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };
}
