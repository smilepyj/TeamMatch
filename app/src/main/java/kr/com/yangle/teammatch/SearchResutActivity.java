package kr.com.yangle.teammatch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.com.yangle.teammatch.adapter.SearchResultListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class SearchResutActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    String search_date, search_time, search_ground, search_team_member, search_team_lvl;

    int search_ground_cnt, search_team_lvl_cnt;

    TextView tv_search_result_ground, tv_search_result_date, tv_search_result_time, tv_search_result_member, tv_search_result_level;

    Button bt_search_result_change_condition;

    ListView lv_search_result_match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.search_result_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        tv_search_result_ground = findViewById(R.id.tv_search_result_ground);
        tv_search_result_date = findViewById(R.id.tv_search_result_date);
        tv_search_result_time = findViewById(R.id.tv_search_result_time);
        tv_search_result_member = findViewById(R.id.tv_search_result_member);
        tv_search_result_level = findViewById(R.id.tv_search_result_level);
        bt_search_result_change_condition = findViewById(R.id.bt_search_result_change_condition);

        lv_search_result_match = findViewById(R.id.lv_search_result_match);

        bt_search_result_change_condition.setOnClickListener(mOnClickListener);

        search_date = getIntent().getStringExtra("search_date");
        search_time = getIntent().getStringExtra("search_time");
        search_ground = getIntent().getStringExtra("search_ground");
        search_ground_cnt = getIntent().getIntExtra("search_ground_cnt", 0);
        search_team_member = getIntent().getStringExtra("search_team_member");
        search_team_lvl = getIntent().getStringExtra("search_team_lvl");
        search_team_lvl_cnt = getIntent().getIntExtra("search_team_lvl_cnt", 0);

        setSearchCondition();

        mService.searchMatchList(searchMatchList_Listener, search_date, search_time, "", search_ground, search_team_member, search_team_lvl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_search_result_change_condition :
                    finish();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 화면 진입 시 검색 조건 정보 로딩
     * Created by maloman72 on 2018-11-01
     * */
    private void setSearchCondition() {
        try {
            if(search_ground_cnt > 1) {
                tv_search_result_ground.setText(getString(R.string.search_result_condition_ground) + "(" + search_ground_cnt + ")");
            } else if (search_ground_cnt == 1) {
                tv_search_result_ground.setText(search_ground);
            }

            Date mDate = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).parse(search_date);
            String mHopeDate = new SimpleDateFormat("yyyy년 mm월 dd일", Locale.getDefault()).format(mDate);
            tv_search_result_date.setText(mHopeDate);

            if(!"".equals(search_time)) {
                Date mTime = new SimpleDateFormat("hhmmss", Locale.getDefault()).parse(search_time);
                String mHopeTime = new SimpleDateFormat("hh시 mm분", Locale.getDefault()).format(mTime);
                tv_search_result_time.setText(mHopeTime);
            } else {
                tv_search_result_time.setText("시간 무관");
            }

            if(!"".equals(search_team_member)) {
                tv_search_result_member.setText(search_team_member);
            } else {
                tv_search_result_member.setText("인원 무관");
            }

            if(search_team_lvl_cnt != 0) {
                tv_search_result_level.setText(getString(R.string.search_result_condition_level) + "(" + search_team_lvl_cnt + ")");
            } else {
                tv_search_result_level.setText("레벨 무관");
            }

        } catch (Exception e) {
            Log.e(TAG, "setSearchCondition - " + e);
        }

    }

    ResponseListener searchMatchList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    SearchResultListViewAdapter mSearchResultListViewAdapter = new SearchResultListViewAdapter(mContext, mJSONArray);
                    lv_search_result_match.setAdapter(mSearchResultListViewAdapter);

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
