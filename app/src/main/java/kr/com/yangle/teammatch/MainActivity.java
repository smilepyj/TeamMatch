package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import kr.com.yangle.teammatch.util.BackPressCloseHandler;
import kr.com.yangle.teammatch.util.DialogAlertActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    Toolbar toolbar;

    DrawerLayout dl_activity_main;

    NavigationView nv_main_menu;

    LinearLayout ll_main_button, ll_main_in_progress_matching, ll_main_ranking;

    Button bt_main_search_match, bt_main_registration_match;

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
        nv_main_menu = findViewById(R.id.nv_main_menu);

        ll_main_button = findViewById(R.id.ll_main_button);
        ll_main_in_progress_matching = findViewById(R.id.ll_main_in_progress_matching);
        ll_main_ranking = findViewById(R.id.ll_main_ranking);

        bt_main_search_match = findViewById(R.id.bt_main_search_match);
        bt_main_registration_match = findViewById(R.id.bt_main_registration_match);

        nv_main_menu.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ll_main_in_progress_matching.setOnClickListener(mOnClickListener);
        ll_main_ranking.setOnClickListener(mOnClickListener);

        bt_main_search_match.setOnClickListener(mOnClickListener);
        bt_main_registration_match.setOnClickListener(mOnClickListener);

        LayoutSet();
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
                dl_activity_main.openDrawer(GravityCompat.END);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent mIntent;

            item.setChecked(true);
            dl_activity_main.closeDrawers();

            switch (item.getItemId()) {
                case R.id.it_navigation_user:
                    mIntent = new Intent(mContext, UserInfoActivity.class);
                    mIntent.putExtra(getString(R.string.user_info_intent_extra), getString(R.string.user_info_type_update));
                    startActivity(mIntent);
                    break;
//                case R.id.it_navigation_setting:
//                    mIntent = new Intent(mContext, DialogAlertActivity.class);
//                    mIntent.putExtra(getString(R.string.alert_dialog_title), "예약확인");
//                    mIntent.putExtra(getString(R.string.alert_dialog_contents_header), "[필독] 당일예약 상품입니다.");
//                    mIntent.putExtra(getString(R.string.alert_dialog_contents), "이용가능 여부를 업체측에 확인 후 예약을 진행해주세요.\n업체 사정에 따라 이용이 불가할 수 있습니다.");
//                    mIntent.putExtra(getString(R.string.alert_dialog_cancel_text), "취소");
//                    mIntent.putExtra(getString(R.string.alert_dialog_ok_text), "예약하기");
//                    mIntent.putExtra(getString(R.string.alert_dialog_type), 0);
//
//                    startActivity(mIntent);
////                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
//                    break;
                default:
                    break;
            }

            return true;
        }
    };

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
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
}
