package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.com.yangle.teammatch.util.BackPressCloseHandler;
import kr.com.yangle.teammatch.util.DialogAlertActivity;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    Toolbar toolbar;

    DrawerLayout dl_activity_main;

    View ll_navigation_draw;

    LinearLayout ll_main_button, ll_main_in_progress_matching, ll_main_ranking, ll_navigation_user, ll_navigation_match, ll_navigation_ranking, ll_navigation_logout;

    Button bt_main_search_match, bt_main_registration_match;

    ImageButton ib_navigation_close;
    TextView tv_navigation_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mBackPressCloseHandler = new BackPressCloseHandler(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/match");
        FirebaseInstanceId.getInstance().getToken();
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.e(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
        }

        dl_activity_main = findViewById(R.id.dl_activity_main);
        ll_navigation_draw = findViewById(R.id.ll_navigation_draw);
        ib_navigation_close = findViewById(R.id.ib_navigation_close);
        ll_navigation_user = findViewById(R.id.ll_navigation_user);
        ll_navigation_match = findViewById(R.id.ll_navigation_match);
        ll_navigation_ranking = findViewById(R.id.ll_navigation_ranking);
        ll_navigation_logout = findViewById(R.id.ll_navigation_logout);
        tv_navigation_user_name = findViewById(R.id.tv_navigation_user_name);

        ll_main_button = findViewById(R.id.ll_main_button);
        ll_main_in_progress_matching = findViewById(R.id.ll_main_in_progress_matching);
        ll_main_ranking = findViewById(R.id.ll_main_ranking);

        bt_main_search_match = findViewById(R.id.bt_main_search_match);
        bt_main_registration_match = findViewById(R.id.bt_main_registration_match);

        ib_navigation_close.setOnClickListener(mOnClickListener);
        ll_navigation_user.setOnClickListener(mOnClickListener);
        ll_navigation_match.setOnClickListener(mOnClickListener);
        ll_navigation_ranking.setOnClickListener(mOnClickListener);
        ll_navigation_logout.setOnClickListener(mOnClickListener);

        ll_main_in_progress_matching.setOnClickListener(mOnClickListener);
        ll_main_ranking.setOnClickListener(mOnClickListener);

        bt_main_search_match.setOnClickListener(mOnClickListener);
        bt_main_registration_match.setOnClickListener(mOnClickListener);

        LayoutSet();

        tv_navigation_user_name.setText(mApplicationTM.getUserName());

        Log.e(TAG, "keyHash : " + getKeyHash(mContext));
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
                if (dl_activity_main.isDrawerOpen(GravityCompat.END)) {
                    dl_activity_main.closeDrawers();
                } else {
                    mBackPressCloseHandler.onBackPressed();
                }

                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_toolbar_navigation:
                dl_activity_main.openDrawer(ll_navigation_draw);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.ib_navigation_close :
                    dl_activity_main.closeDrawers();
                    break;
                case R.id.ll_navigation_user :
                    dl_activity_main.closeDrawers();
                    mIntent = new Intent(mContext, UserInfoActivity.class);
                    mIntent.putExtra(getString(R.string.user_info_intent_extra), getString(R.string.user_info_type_update));
                    startActivity(mIntent);
                    break;
                case R.id.ll_navigation_match :
                    dl_activity_main.closeDrawers();
                    mIntent = new Intent(mContext, MatchProcActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_navigation_ranking :
                    dl_activity_main.closeDrawers();
                    mIntent = new Intent(mContext, RankingActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_navigation_logout :
                    dl_activity_main.closeDrawers();
                    Logout();
                    //mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                case R.id.bt_main_search_match:
                    mIntent = new Intent(mContext, SearchMatchActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.bt_main_registration_match:
                    mIntent = new Intent(mContext, RegistMatchActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_main_in_progress_matching:
                    mIntent = new Intent(mContext, MatchProcActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_main_ranking:
                    mIntent = new Intent(mContext, RankingActivity.class);
                    startActivity(mIntent);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 화면 비율에 따른 Button Height 조절
     * Created by maloman72 on 2018-11-05
     */
    public void LayoutSet() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        double mRealWidth, mRealHeight;

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        mDisplay.getRealMetrics(mDisplayMetrics);
        mRealWidth = mDisplayMetrics.widthPixels;
        mRealHeight = mDisplayMetrics.heightPixels;

        Log.e(TAG, "mRealWidth - " + mRealWidth + ", mRealHeight - " + mRealHeight);

        LinearLayout.LayoutParams ll_main_button_param = (LinearLayout.LayoutParams) ll_main_button.getLayoutParams();

        double mScreenRate = mRealHeight / mRealWidth;

        if (mScreenRate <= 1.6) {
            ll_main_button_param.weight = 3f;
        } else if (mScreenRate >= 2) {
            ll_main_button_param.weight = 2.5f;
        } else {
            ll_main_button_param.weight = 2.75f;
        }
    }

    public String getKeyHash(Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.e(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    private void Logout() {
        try {
            String loginType = mApplicationTM.getLoginType();

            if(mContext.getString(R.string.login_naver_login_type).equals(loginType)) {

                OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
                mOAuthLogin.init(mContext, getString(R.string.login_naver_client_id), getString(R.string.login_naver_client_secret), getString(R.string.app_name));
                mOAuthLogin.logout(mContext);

                mApplicationTM.setLoginType("");
                mApplicationTM.setUserId("");
                mApplicationTM.setUserEmail("");
                mApplicationTM.setUserName("");
                mApplicationTM.setTeamId("");

                Intent mIntent = new Intent(mContext, IntroActivity.class);
                startActivity(mIntent);
                finish();

                /*if(mOAuthLogin.logout(mContext)) {
                    mApplicationTM.setLoginType("");
                    mApplicationTM.setUserId("");
                    mApplicationTM.setUserName("");
                    mApplicationTM.setTeamId("");

                    Intent mIntent = new Intent(mContext, IntroActivity.class);
                    startActivity(mIntent);
                    finish();
                }else {
                    Log.e(TAG, "errorCode:" + OAuthLogin.getInstance().getLastErrorCode(mContext));
                    Log.e(TAG, "errorDesc:" + OAuthLogin.getInstance().getLastErrorDesc(mContext));
                }*/
            }else if(mContext.getString(R.string.login_kakao_login_type).equals(loginType)){

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.e(TAG, "Kakao LogOut");
                    }
                });

                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e(TAG, "onSessionClosed : " + errorResult.getErrorMessage());
                        //redirectLoginActivity();
                    }

                    @Override
                    public void onNotSignedUp() {
                        //redirectSignupActivity();
                    }

                    @Override
                    public void onSuccess(Long userId) {
                        Log.e(TAG, "UnLink onSuccess");

                        mApplicationTM.setLoginType("");
                        mApplicationTM.setUserId("");
                        mApplicationTM.setUserEmail("");
                        mApplicationTM.setUserName("");
                        mApplicationTM.setTeamId("");

                        Intent mIntent = new Intent(mContext, IntroActivity.class);
                        startActivity(mIntent);
                        finish();
                        //redirectLoginActivity();
                    }
                });
            }
        } catch (Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "Logout - " + e);
        } finally {
            mApplicationTM.stopProgress();
        }
    }
}
