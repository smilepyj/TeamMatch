package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import kr.com.yangle.teammatch.util.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private BackPressCloseHandler mBackPressCloseHandler;

    Toolbar toolbar;

    DrawerLayout dl_activity_main;

    NavigationView nv_main_menu;

    LinearLayout ll_main_in_progress_matching, ll_main_ranking;

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

        dl_activity_main = findViewById(R.id.dl_activity_main);
        nv_main_menu = findViewById(R.id.nv_main_menu);

        ll_main_in_progress_matching = findViewById(R.id.ll_main_in_progress_matching);
        ll_main_ranking = findViewById(R.id.ll_main_ranking);

        bt_main_search_match = findViewById(R.id.bt_main_search_match);
        bt_main_registration_match = findViewById(R.id.bt_main_registration_match);

        nv_main_menu.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ll_main_in_progress_matching.setOnClickListener(mOnClickListener);
        ll_main_ranking.setOnClickListener(mOnClickListener);

        bt_main_search_match.setOnClickListener(mOnClickListener);
        bt_main_registration_match.setOnClickListener(mOnClickListener);
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
                if(dl_activity_main.isDrawerOpen(GravityCompat.END)) {
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
            case R.id.id_toolbar_navigation :
                dl_activity_main.openDrawer(GravityCompat.END);
                return true;
            default :
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
                case R.id.it_navigation_user :
                    mIntent = new Intent(mContext, UserInfoActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.it_navigation_setting :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                default :
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
                case R.id.bt_main_search_match :
                    mIntent = new Intent(mContext, SearchingMatchActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.bt_main_registration_match :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                case R.id.ll_main_in_progress_matching :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                case R.id.ll_main_ranking :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                default :
                    break;
            }
        }
    };
}
