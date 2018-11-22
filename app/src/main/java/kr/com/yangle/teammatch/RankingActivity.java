package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.com.yangle.teammatch.adapter.RankingListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_1ListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class RankingActivity extends AppCompatActivity {private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    LinearLayout ll_ranking_term, ll_ranking_search, ll_ranking_search_init;
    Button bt_ranking_total, bt_ranking_week, bt_ranking_area;
    TextView tv_ranking_term;
    ListView lv_ranking;
    EditText et_ranking_search_area, et_ranking_search_team_name;

    JSONArray area_groups;

    String search_area_group, search_team_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        ll_ranking_term = findViewById(R.id.ll_ranking_term);
        ll_ranking_search = findViewById(R.id.ll_ranking_search);
        ll_ranking_search_init = findViewById(R.id.ll_ranking_search_init);

        bt_ranking_total = findViewById(R.id.bt_ranking_total);
        bt_ranking_week = findViewById(R.id.bt_ranking_week);
        bt_ranking_area = findViewById(R.id.bt_ranking_area);

        tv_ranking_term = findViewById(R.id.tv_ranking_term);

        lv_ranking = findViewById(R.id.lv_ranking);

        et_ranking_search_area = findViewById(R.id.et_ranking_search_area);
        et_ranking_search_team_name = findViewById(R.id.et_ranking_search_team_name);

        et_ranking_search_area.setOnClickListener(mOnClickListener);
        ll_ranking_search.setOnClickListener(mOnClickListener);
        ll_ranking_search_init.setOnClickListener(mOnClickListener);

        bt_ranking_total.setSelected(true);

        search_area_group = "";
        search_team_name = "";

        mService.searchAreaGroupList(searchAreaGroupList_Listener);
        mService.searchRankList(searchRankList_Listener, search_area_group, search_team_name);
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
                case R.id.et_ranking_search_area :
                    AreaGroup_AlertDialog();
                    break;
                case R.id.ll_ranking_search :
                    search_team_name = et_ranking_search_team_name.getText().toString();
                    mService.searchRankList(searchRankList_Listener, search_area_group, search_team_name);
                    break;
                case R.id.ll_ranking_search_init :
                    search_area_group = "";
                    et_ranking_search_area.setText("전체");
                    search_team_name = "";
                    et_ranking_search_team_name.setText("");
                    mService.searchRankList(searchRankList_Listener, search_area_group, search_team_name);
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

    /**
     * 지역그룹 Spinner AlertDialog
     * Created by maloman72 on 2018-11-05
     * */
    private void AreaGroup_AlertDialog() {
        try {
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
            mAlertDialogBuilder.setTitle(getString(R.string.ranking_area_group_dialog_title));

            final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.select_dialog_item);

            mArrayAdapter.add("전체");
            for (int i = 0; i < area_groups.length(); i++) {
                mArrayAdapter.add(((JSONObject) area_groups.get(i)).getString("area_group_name"));
            }

            mAlertDialogBuilder.setNegativeButton(getString(R.string.user_info_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mAlertDialogBuilder.setAdapter(mArrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        String search_area_group_name = mArrayAdapter.getItem(i);

                        if("전체".equals(search_area_group_name)) {
                            search_area_group = "";
                        }else {
                            for (int j = 0; j < area_groups.length(); j++) {
                                if (search_area_group_name.equals(((JSONObject) area_groups.get(j)).getString("area_group_name"))) {
                                    search_area_group = ((JSONObject) area_groups.get(j)).getString("area_group_code");
                                    break;
                                }
                            }
                        }

                        et_ranking_search_area.setText(mArrayAdapter.getItem(i));

                    }catch(Exception e) {
                        mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                        Log.e(TAG, "AreaGroup_AlertDialog onClick - " + e);
                    }

                    dialogInterface.dismiss();
                }
            });

            mAlertDialogBuilder.show();
        }catch(Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "AreaGroup_AlertDialog - " + e);
        }
    }

    ResponseListener searchAreaGroupList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    area_groups = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };

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
                e.printStackTrace();
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };
}
