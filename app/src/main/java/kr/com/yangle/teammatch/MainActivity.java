package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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
import kr.com.yangle.teammatch.util.DialogMatchApplyActivity;
import kr.com.yangle.teammatch.util.DialogMatchSuccessActivity;
import kr.com.yangle.teammatch.util.DialogRatingActivity;
import kr.com.yangle.teammatch.util.DialogReportActivity;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    Toolbar toolbar;

    DrawerLayout dl_activity_main;

    View ll_navigation_draw;

    LinearLayout ll_main_button, ll_main_in_progress_matching, ll_main_ranking, ll_navigation_user, ll_navigation_match, ll_navigation_ranking, ll_navigation_logout, ll_navigation_space, ll_navigation_report,
            ll_navigation_footer;

    Button bt_main_search_match, bt_main_registration_match, bt_main_in_progress_matching;

    ImageButton ib_navigation_close;
    TextView tv_navigation_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

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
        ll_navigation_space = findViewById(R.id.ll_navigation_space);
        ll_navigation_report = findViewById(R.id.ll_navigation_report);
        ll_navigation_footer = findViewById(R.id.ll_navigation_footer);

        tv_navigation_user_name = findViewById(R.id.tv_navigation_user_name);

        ll_main_button = findViewById(R.id.ll_main_button);
//        ll_main_in_progress_matching = findViewById(R.id.ll_main_in_progress_matching);
//        ll_main_ranking = findViewById(R.id.ll_main_ranking);

        bt_main_search_match = findViewById(R.id.bt_main_search_match);
        bt_main_registration_match = findViewById(R.id.bt_main_registration_match);
        bt_main_in_progress_matching = findViewById(R.id.bt_main_in_progress_matching);

        ib_navigation_close.setOnClickListener(mOnClickListener);
        ll_navigation_user.setOnClickListener(mOnClickListener);
        ll_navigation_match.setOnClickListener(mOnClickListener);
        ll_navigation_ranking.setOnClickListener(mOnClickListener);
        ll_navigation_logout.setOnClickListener(mOnClickListener);
        ll_navigation_report.setOnClickListener(mOnClickListener);
        ll_navigation_space.setOnClickListener(mOnClickListener);
        ll_navigation_footer.setOnClickListener(mOnClickListener);

//        ll_main_in_progress_matching.setOnClickListener(mOnClickListener);
//        ll_main_ranking.setOnClickListener(mOnClickListener);

        bt_main_search_match.setOnClickListener(mOnClickListener);
        bt_main_registration_match.setOnClickListener(mOnClickListener);
        bt_main_in_progress_matching.setOnClickListener(mOnClickListener);

//        LayoutSet();

        tv_navigation_user_name.setText(mApplicationTM.getUserName());

        Log.e(TAG, "keyHash : " + getKeyHash(mContext));

        chkPushMessage();
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

            try {
                switch (v.getId()) {
                    case R.id.ib_navigation_close:
                        dl_activity_main.closeDrawers();
                        break;
                    case R.id.ll_navigation_user:
                        mIntent = new Intent(mContext, UserInfoActivity.class);
                        mIntent.putExtra(getString(R.string.user_info_intent_extra), getString(R.string.user_info_type_update));
                        startActivity(mIntent);
                        dl_activity_main.closeDrawers();
                        break;
                    case R.id.ll_navigation_match:
                        mIntent = new Intent(mContext, MatchProcActivity.class);
                        startActivity(mIntent);
                        dl_activity_main.closeDrawers();
                        break;
                    case R.id.ll_navigation_ranking:
                        mIntent = new Intent(mContext, RankingActivity.class);
                        startActivity(mIntent);
                        dl_activity_main.closeDrawers();
                        break;
                    case R.id.ll_navigation_logout:
                        dl_activity_main.closeDrawers();
                        Logout();
                        //mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                        break;
                    case R.id.ll_navigation_footer :
                    case R.id.ll_navigation_space :
                        break;
                    case R.id.bt_main_search_match:
                        mIntent = new Intent(mContext, SearchMatchActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.bt_main_registration_match:
                        mIntent = new Intent(mContext, RegistMatchActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.bt_main_in_progress_matching:
                        mIntent = new Intent(mContext, MatchProcActivity.class);
                        startActivity(mIntent);
                        break;
//                    case R.id.ll_main_ranking:
//                        mIntent = new Intent(mContext, RankingActivity.class);
//                        startActivity(mIntent);
//                        break;
                    case R.id.ll_navigation_report :
                        dl_activity_main.closeDrawers();
                        mIntent = new Intent(mContext, DialogReportActivity.class);
                        startActivity(mIntent);
                        break;
                    default:
                        break;
                }
            }catch(Exception e) {
                Log.e(TAG, "mOnClickListener - " + e);
            }
        }
    };

    /**
     * 화면 비율에 따른 Button Height 조절
     * Created by maloman72 on 2018-11-05
     */
//    public void LayoutSet() {
//        Display mDisplay = getWindowManager().getDefaultDisplay();
//        double mRealWidth, mRealHeight;
//
//        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//        mDisplay.getRealMetrics(mDisplayMetrics);
//        mRealWidth = mDisplayMetrics.widthPixels;
//        mRealHeight = mDisplayMetrics.heightPixels;
//
//        Log.e(TAG, "mRealWidth - " + mRealWidth + ", mRealHeight - " + mRealHeight);
//
//        LinearLayout.LayoutParams ll_main_button_param = (LinearLayout.LayoutParams) ll_main_button.getLayoutParams();
//
//        double mScreenRate = mRealHeight / mRealWidth;
//
//        if (mScreenRate <= 1.6) {
//            ll_main_button_param.weight = 3f;
//        } else if (mScreenRate >= 2) {
//            ll_main_button_param.weight = 2.5f;
//        } else {
//            ll_main_button_param.weight = 2.75f;
//        }
//    }

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
                        Log.e(TAG, "onFailure : " + errorResult.getErrorMessage());
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e(TAG, "onSessionClosed : " + errorResult.getErrorMessage());

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
            mApplicationTM.stopCustomProgressDialog();
        }
    }

    public void chkPushMessage() {
        String match_alert_type = getIntent().getStringExtra("MATCH_ALERT_TYPE");

        String team_id = mApplicationTM.getTeamId();

        Log.e(TAG, "match_alert_type : " + match_alert_type);

        if("1".equals(match_alert_type)) {
            String match_alert_at = getIntent().getStringExtra("MATCH_ALERT_AT");
            String host_team_id = getIntent().getStringExtra("HOST_TEAM_ID");
            String title = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_title));
            String contents = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_contents));
            String match_id = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_match_id));
            String match_apply_id = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_match_apply_id));
            String opponent_id = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_team_id));
            String opponent_name = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_team_name));
            String opponent_lvl = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_team_lvl));
            String opponent_point = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_team_point));
            String hope_match_time = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_match_time));
            String hope_match_ground = getIntent().getStringExtra(getApplicationContext().getString(R.string.match_apply_extra_match_ground));

            if (team_id.equals(host_team_id)) {
                Intent mIntent = new Intent(getApplicationContext(), DialogMatchApplyActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_title), title);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_contents), contents);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_id), match_id);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_apply_id), match_apply_id);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_id), opponent_id);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_name), opponent_name);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_lvl), opponent_lvl);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_point), opponent_point);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_time), hope_match_time);
                mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_ground), hope_match_ground);
                startActivity(mIntent);
            }
        }else if("2".equals(match_alert_type)) {
            String host_team_id = getIntent().getStringExtra("HOST_TEAM_ID");
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");

            if(team_id.equals(guest_team_id) || team_id.equals(host_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 성사");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "매치가 성사 되었습니다.\n구장 이용여부 확인 중입니다.");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                startActivity(mIntent);
            }else {
                String reject_team_ids = getIntent().getStringExtra("REJECT_TEAM_IDS");

                if(!"".equals(reject_team_ids)) {
                    String[] reject_team_list = reject_team_ids.split("\\|");

                    for(String reject_team : reject_team_list) {
                        if(team_id.equals(reject_team)) {
                            Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                            mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                            startActivity(mIntent);

                            break;
                        }
                    }
                }
            }
        }else if("3".equals(match_alert_type)) {
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");

            if(team_id.equals(guest_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                startActivity(mIntent);
            }
        }else if("4".equals(match_alert_type)) {
            String type = getIntent().getStringExtra(getString(R.string.match_success_extra_type));
            String sub_title = getIntent().getStringExtra("SUB_TITLE");
            String sub_title_etc = getIntent().getStringExtra("SUB_TITLE_ETC");
            String match_id = getIntent().getStringExtra("MATCH_ID");
            String host_team_id = getIntent().getStringExtra("HOST_TEAM_ID");
            String host_team_name = getIntent().getStringExtra("HOST_TEAM_NAME");
            String host_team_lvl = getIntent().getStringExtra("HOST_TEAM_LVL");
            String host_team_point = getIntent().getStringExtra("HOST_TEAM_POINT");
            String host_team_user_name = getIntent().getStringExtra("HOST_TEAM_USER_NAME");
            String host_team_user_tel = getIntent().getStringExtra("HOST_TEAM_USER_TEL");
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");
            String guest_team_name = getIntent().getStringExtra("GUEST_TEAM_NAME");
            String guest_team_lvl = getIntent().getStringExtra("GUEST_TEAM_LVL");
            String guest_team_point = getIntent().getStringExtra("GUEST_TEAM_POINT");
            String guest_team_user_name = getIntent().getStringExtra("GUEST_TEAM_USER_NAME");
            String guest_team_user_tel = getIntent().getStringExtra("GUEST_TEAM_USER_TEL");
            String hope_match_time = getIntent().getStringExtra("MATCH_TIME");
            String hope_match_ground = getIntent().getStringExtra("HOPE_MATCH_GROUND");
            String hope_match_ground_tel = getIntent().getStringExtra("GROUND_TEL");
            String hope_match_ground_cost = getIntent().getStringExtra("GROUND_COST");

            if(team_id.equals(host_team_id)) {

                Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                mIntent.putExtra(getString(R.string.match_success_extra_type), type);
                mIntent.putExtra("SUB_TITLE", sub_title);
                mIntent.putExtra("SUB_TITLE_ETC", sub_title_etc);
                mIntent.putExtra("MATCH_ID", match_id);
                mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                mIntent.putExtra("MATCH_TIME", hope_match_time);
                mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                mIntent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_proc));
                startActivity(mIntent);

            }else if(team_id.equals(guest_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                mIntent.putExtra("SUB_TITLE", sub_title);
                mIntent.putExtra("MATCH_ID", match_id);
                mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                mIntent.putExtra("MATCH_TIME", hope_match_time);
                mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                mIntent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_regist));
                startActivity(mIntent);

            }
        }else if("5".equals(match_alert_type)) {
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");

            if(team_id.equals(guest_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 반려");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "해당 구장이 이미 예약되어 있습니다.\n다른 일정 또는 구장으로 매치를 다시 진행하시길 바랍니다.");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 4);
                startActivity(mIntent);
            }
        }else if("6".equals(match_alert_type)) {
            String type = getIntent().getStringExtra(getString(R.string.match_success_extra_type));
            String match_id = getIntent().getStringExtra("MATCH_ID");
            String host_team_id = getIntent().getStringExtra("HOST_TEAM_ID");
            String host_team_name = getIntent().getStringExtra("HOST_TEAM_NAME");
            String host_team_lvl = getIntent().getStringExtra("HOST_TEAM_LVL");
            String host_team_point = getIntent().getStringExtra("HOST_TEAM_POINT")==null?"0":getIntent().getStringExtra("HOST_TEAM_POINT");
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");
            String guest_team_name = getIntent().getStringExtra("GUEST_TEAM_NAME");
            String guest_team_lvl = getIntent().getStringExtra("GUEST_TEAM_LVL");
            String guest_team_point = getIntent().getStringExtra("GUEST_TEAM_POINT")==null?"0":getIntent().getStringExtra("GUEST_TEAM_POINT");
            String hope_match_time = getIntent().getStringExtra("HOPE_MATCH_TIME");
            String hope_match_ground = getIntent().getStringExtra("HOPE_MATCH_GROUND");

            if(team_id.equals(host_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogRatingActivity.class);
                mIntent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                mIntent.putExtra("MATCH_ID", match_id);
                mIntent.putExtra("TEAM_ID", guest_team_id);
                mIntent.putExtra("TEAM_NAME", guest_team_name);
                mIntent.putExtra("TEAM_LVL", guest_team_lvl);
                mIntent.putExtra("TEAM_POINT", guest_team_point);
                mIntent.putExtra("GROUND_NAME", hope_match_ground);
                mIntent.putExtra("MATCH_TIME", hope_match_time);
                startActivity(mIntent);
            }else if(team_id.equals(guest_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogRatingActivity.class);
                mIntent.putExtra(getString(R.string.match_success_extra_type), "GUEST");
                mIntent.putExtra("MATCH_ID", match_id);
                mIntent.putExtra("TEAM_ID", host_team_id);
                mIntent.putExtra("TEAM_NAME", host_team_name);
                mIntent.putExtra("TEAM_LVL", host_team_lvl);
                mIntent.putExtra("TEAM_POINT", host_team_point);
                mIntent.putExtra("GROUND_NAME", hope_match_ground);
                mIntent.putExtra("MATCH_TIME", hope_match_time);
                startActivity(mIntent);
            }
        }else if("10".equals(match_alert_type)) {
            String type = getIntent().getStringExtra(getString(R.string.match_success_extra_type));
            String host_team_id = getIntent().getStringExtra("HOST_TEAM_ID");
            String guest_team_id = getIntent().getStringExtra("GUEST_TEAM_ID");

            if(team_id.equals(host_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "구장이용 승인 완료");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "구장사용이 승인되었습니다. 구장이용료를 결제하시고 상대팀과 연락하시길 바랍니다.\n(선 결제의 경우에는 동의 및 확인만 하시면 됩니다.)");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 4);
                startActivity(mIntent);
            }else if(team_id.equals(guest_team_id)){
                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "구장이용 승인 완료");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "구장사용이 승인되었습니다. 상대팀이 구장 사용료를 결제하고 있습니다. 상대팀과 연락하시길 바랍니다.");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 4);
                startActivity(mIntent);
            }
        }
    }
}
