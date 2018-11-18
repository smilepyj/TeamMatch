package kr.com.yangle.teammatch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

    String search_date, search_time, search_area_group, search_area, search_ground, search_team_member, search_team_lvl;

    int search_area_group_cnt, search_area_cnt, search_ground_cnt, search_team_lvl_cnt;

    LinearLayout ll_search_result_yes, ll_search_result_no;

    TextView tv_search_result_ground, tv_search_result_date, tv_search_result_time, tv_search_result_member, tv_search_result_level;

    Button bt_search_result_change_condition;

    ListView lv_search_result_match;

    SearchResultListViewAdapter mSearchResultListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ll_search_result_yes = findViewById(R.id.ll_search_result_yes);
        ll_search_result_no = findViewById(R.id.ll_search_result_no);

        tv_search_result_ground = findViewById(R.id.tv_search_result_ground);
        tv_search_result_date = findViewById(R.id.tv_search_result_date);
        tv_search_result_time = findViewById(R.id.tv_search_result_time);
        tv_search_result_member = findViewById(R.id.tv_search_result_member);
        tv_search_result_level = findViewById(R.id.tv_search_result_level);
        bt_search_result_change_condition = findViewById(R.id.bt_search_result_change_condition);

        lv_search_result_match = findViewById(R.id.lv_search_result_match);

        bt_search_result_change_condition.setOnClickListener(mOnClickListener);

        search_date = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_date));
        search_time = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_start_time));
        search_area_group = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_area_group));
        search_area_group_cnt = getIntent().getIntExtra(getString(R.string.search_match_extra_search_area_group_cnt), 0);
        search_area = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_area));
        search_area_cnt = getIntent().getIntExtra(getString(R.string.search_match_extra_search_area_cnt), 0);
        search_ground = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_ground));
        search_ground_cnt = getIntent().getIntExtra(getString(R.string.search_match_extra_search_ground_cnt), 0);
        search_team_member = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_team_member));
        search_team_lvl = getIntent().getStringExtra(getString(R.string.searchmatchlist_param_search_team_lvl));
        search_team_lvl_cnt = getIntent().getIntExtra(getString(R.string.search_match_extra_search_team_lvl_cnt), 0);

        mSearchResultListViewAdapter = new SearchResultListViewAdapter(mContext);
        lv_search_result_match.setAdapter(mSearchResultListViewAdapter);

        setSearchCondition();

        mService.searchMatchList(searchMatchList_Listener, search_date, search_time, search_area_group, search_area, search_ground, search_team_member, search_team_lvl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mService.searchMatchList(searchMatchList_Listener, search_date, search_time, search_area_group, search_area, search_ground, search_team_member, search_team_lvl);
        }catch(Exception e) {
            Log.e(TAG, "SearchResutActivity onResume - " + e);
        }
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
            tv_search_result_ground.setText(getString(R.string.search_result_condition_ground) + "(" + search_area_group_cnt + search_area_cnt + search_ground_cnt + ")");

            Date mDate = new SimpleDateFormat(getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(search_date);
            String mHopeDate = new SimpleDateFormat(getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
            tv_search_result_date.setText(mHopeDate);

            if(!"".equals(search_time)) {
                Date mTime = new SimpleDateFormat(getString(R.string.search_result_time_format_base), Locale.getDefault()).parse(search_time);
                String mHopeTime = new SimpleDateFormat(getString(R.string.search_result_time_format_view), Locale.getDefault()).format(mTime);
                tv_search_result_time.setText(mHopeTime);
            } else {
                tv_search_result_time.setText(getString(R.string.search_result_nothing_time));
            }

            if(!"".equals(search_team_member)) {
                tv_search_result_member.setText(mApplicationTM.getC003().get(search_team_member));
            } else {
                tv_search_result_member.setText(getString(R.string.search_result_nothing_member));
            }

            if(search_team_lvl_cnt == 1) {
                tv_search_result_level.setText(getString(R.string.search_result_condition_level) + "(" + mApplicationTM.getC002().get(search_team_lvl) + ")");
            } else if(search_team_lvl_cnt > 1) {
                tv_search_result_level.setText(getString(R.string.search_result_condition_level) + "(" + search_team_lvl_cnt + ")");
            } else {
                tv_search_result_level.setText(getString(R.string.search_result_nothing_level));
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

                    mSearchResultListViewAdapter.setMDataJSONArray(mJSONArray);
                    mSearchResultListViewAdapter.notifyDataSetChanged();

                    ll_search_result_no.setVisibility(View.GONE);
                    ll_search_result_yes.setVisibility(View.VISIBLE);
                } else {
                    mSearchResultListViewAdapter.setMDataJSONArray(new JSONArray());
                    mSearchResultListViewAdapter.notifyDataSetChanged();

//                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                    ll_search_result_yes.setVisibility(View.GONE);
                    ll_search_result_no.setVisibility(View.VISIBLE);
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
