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

import kr.com.yangle.teammatch.adapter.RankingListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class RankingActivity extends AppCompatActivity {private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    LinearLayout ll_ranking_term;
    Button bt_ranking_total, bt_ranking_week, bt_ranking_area;
    TextView tv_ranking_term;
    ListView lv_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ll_ranking_term = findViewById(R.id.ll_ranking_term);

        bt_ranking_total = findViewById(R.id.bt_ranking_total);
        bt_ranking_week = findViewById(R.id.bt_ranking_week);
        bt_ranking_area = findViewById(R.id.bt_ranking_area);

        tv_ranking_term = findViewById(R.id.tv_ranking_term);

        lv_ranking = findViewById(R.id.lv_ranking);

        bt_ranking_total.setOnClickListener(mOnClickListener);
        bt_ranking_week.setOnClickListener(mOnClickListener);
        bt_ranking_area.setOnClickListener(mOnClickListener);

        bt_ranking_total.setSelected(true);

        mService.searchRankList(searchRankList_Listener, getString(R.string.ranking_type_total));
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
                case R.id.bt_ranking_total :
                    setRankingType();
                    bt_ranking_total.setSelected(true);
                    mService.searchRankList(searchRankList_Listener, getString(R.string.ranking_type_total));
                    break;
                case R.id.bt_ranking_week :
                    setRankingType();
                    bt_ranking_week.setSelected(true);
                    ll_ranking_term.setVisibility(View.VISIBLE);
                    mService.searchRankList(searchRankList_Listener, getString(R.string.ranking_type_week));
                    break;
                case R.id.bt_ranking_area :
//                    setRankingType();
//                    bt_ranking_area.setSelected(true);
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message_ranking_area));
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 랭킹 선택 종류 탭 초기화
     * Created by maloman72 on 2018-11-10
     * */
    private void setRankingType() {
        bt_ranking_total.setSelected(false);
        bt_ranking_week.setSelected(false);
        bt_ranking_area.setSelected(false);

        ll_ranking_term.setVisibility(View.GONE);
    }

    ResponseListener searchRankList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    RankingListViewAdapter mRankingListViewAdapter = new RankingListViewAdapter(mContext, mJSONArray);
                    lv_ranking.setAdapter(mRankingListViewAdapter);
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }

                if(ll_ranking_term.getVisibility() == View.VISIBLE) {
                    Date mDate = new SimpleDateFormat(getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(getString(R.string.result_search_start_date)).toString());
                    String mStartDate = new SimpleDateFormat(getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
                    mDate = new SimpleDateFormat(getString(R.string.search_result_date_format_base), Locale.getDefault()).parse(mJSONObject.get(getString(R.string.result_search_end_date)).toString());
                    String mEndDate = new SimpleDateFormat(getString(R.string.search_result_date_format_view), Locale.getDefault()).format(mDate);
                    tv_ranking_term.setText(getString(R.string.ranking_week_term) + mStartDate + getString(R.string.ranking_week_dash) + mEndDate);

                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchRankList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
