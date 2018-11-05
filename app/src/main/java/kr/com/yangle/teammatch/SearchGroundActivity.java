package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.com.yangle.teammatch.adapter.SearchGroundType1ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType2ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_1ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_2ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_3ListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class SearchGroundActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    String search_type_code, search_loc_lat, search_loc_lon, search_area_code, area_group_code, search_area_group_code;
    Button bt_search_ground_type_1, bt_search_ground_type_2, bt_search_ground_type_3, bt_search_ground_select_end;
    ListView lv_search_ground_type_1, lv_search_ground_type_2, lv_search_ground_type_3_1, lv_search_ground_type_3_2, lv_search_ground_type_3_3;
    LinearLayout ll_search_ground_type_1, ll_search_ground_type_2, ll_search_ground_type_3;

    SearchGroundType1ListViewAdapter mSearchGroundType1ListViewAdapter;
    SearchGroundType2ListViewAdapter mSearchGroundType2ListViewAdapter;
    SearchGroundType3_1ListViewAdapter mSearchGroundType3_1ListViewAdapter;
    SearchGroundType3_2ListViewAdapter mSearchGroundType3_2ListViewAdapter;
    SearchGroundType3_3ListViewAdapter mSearchGroundType3_3ListViewAdapter;

    List<JSONObject> search_grounds = new ArrayList<JSONObject>();

//    String search_date, search_time, search_ground, search_team_member, search_team_lvl;
//    int search_ground_cnt, search_team_lvl_cnt;
//    TextView tv_search_result_ground, tv_search_result_date, tv_search_result_time, tv_search_result_member, tv_search_result_level;
//    Button bt_search_result_change_condition;
//    ListView lv_search_result_match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ground);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.search_ground_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        search_type_code = "F";
        search_loc_lat = "";
        search_loc_lon = "";
        search_area_code = "";

        mService.searchGroundList(searchGroundList1_Listener, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);

        bt_search_ground_type_1 = findViewById(R.id.bt_search_ground_type_1);
        bt_search_ground_type_2 = findViewById(R.id.bt_search_ground_type_2);
        bt_search_ground_type_3 = findViewById(R.id.bt_search_ground_type_3);
        bt_search_ground_select_end = findViewById(R.id.bt_search_ground_select_end);

        lv_search_ground_type_1 = findViewById(R.id.lv_search_ground_type_1);
        lv_search_ground_type_2 = findViewById(R.id.lv_search_ground_type_2);
        lv_search_ground_type_3_1 = findViewById(R.id.lv_search_ground_type_3_1);
        lv_search_ground_type_3_2 = findViewById(R.id.lv_search_ground_type_3_2);
        lv_search_ground_type_3_3 = findViewById(R.id.lv_search_ground_type_3_3);

        ll_search_ground_type_1 = findViewById(R.id.ll_search_ground_type_1);
        ll_search_ground_type_2 = findViewById(R.id.ll_search_ground_type_2);
        ll_search_ground_type_3 = findViewById(R.id.ll_search_ground_type_3);

        bt_search_ground_type_1.setOnClickListener(mOnClickListener);
        bt_search_ground_type_2.setOnClickListener(mOnClickListener);
        bt_search_ground_type_3.setOnClickListener(mOnClickListener);
        bt_search_ground_select_end.setOnClickListener(mOnClickListener);

        lv_search_ground_type_1.setOnItemClickListener(mOnItemClickListener_1);
        lv_search_ground_type_3_1.setOnItemClickListener(mOnItemClickListener_3_1);
        lv_search_ground_type_3_2.setOnItemClickListener(mOnItemClickListener_3_2);


        bt_search_ground_type_1.setSelected(true);

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
                case R.id.bt_search_ground_type_1 :
                    setButtonGroundType();
                    bt_search_ground_type_1.setSelected(true);

                    ll_search_ground_type_1.setVisibility(View.VISIBLE);
                    ll_search_ground_type_2.setVisibility(View.GONE);
                    ll_search_ground_type_3.setVisibility(View.GONE);

                    search_type_code = "F";
                    search_loc_lat = "";
                    search_loc_lon = "";
                    search_area_code = "";
                    search_area_group_code = "";

                    mService.searchGroundList(searchGroundList1_Listener, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
                    break;
                case R.id.bt_search_ground_type_2 :
                    setButtonGroundType();
                    bt_search_ground_type_2.setSelected(true);

                    ll_search_ground_type_1.setVisibility(View.GONE);
                    ll_search_ground_type_2.setVisibility(View.VISIBLE);
                    ll_search_ground_type_3.setVisibility(View.GONE);

                    search_type_code = "N";
                    search_loc_lat = "35.8686045";
                    search_loc_lon = "128.5992773";
                    search_area_code = "";
                    search_area_group_code = "";

                    mService.searchGroundList(searchGroundList2_Listener, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
                    break;
                case R.id.bt_search_ground_type_3 :
                    setButtonGroundType();
                    bt_search_ground_type_3.setSelected(true);

                    JSONArray mJSONArray = new JSONArray();
                    mSearchGroundType3_2ListViewAdapter = new SearchGroundType3_2ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_2.setAdapter(mSearchGroundType3_2ListViewAdapter);
                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                    ll_search_ground_type_1.setVisibility(View.GONE);
                    ll_search_ground_type_2.setVisibility(View.GONE);
                    ll_search_ground_type_3.setVisibility(View.VISIBLE);

                    mService.searchAreaGroupList(searchAreaGroupList_Listener);
                    break;
                case R.id.bt_search_ground_select_end :
                    try {
                        ArrayList<String> search_ground_ids = new ArrayList<String>();
                        ArrayList<String> search_ground_names = new ArrayList<String>();
                        for (JSONObject search_ground : search_grounds) {
                            search_ground_ids.add(search_ground.get("ground_id").toString());
                            search_ground_names.add(search_ground.get("ground_name").toString());
                        }

                        Intent mIntent = new Intent(mContext, SearchMatchActivity.class);
                        mIntent.putExtra(getString(R.string.searchmatchlist_param_search_area), "");
                        mIntent.putExtra(getString(R.string.searchmatchlist_param_search_ground), search_ground_ids);
                        mIntent.putExtra("search_ground_name", search_ground_names);
                        startActivity(mIntent);
                        finish();
                    }catch(Exception e) {
                        mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                        Log.e(TAG, "mOnClickListener - " + e);
                    }

                    break;
                default :
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener mOnItemClickListener_1 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

            try {
                JSONObject jsonObject = (JSONObject) adapterView.getAdapter().getItem(position);

                String ground_id = jsonObject.get("ground_id")==null?"":jsonObject.get("ground_id").toString();

                Log.e(TAG, ground_id + "");

                boolean curChecked = mSearchGroundType1ListViewAdapter.getChecked(position);

                if("".equals(ground_id)) {
                    mSearchGroundType1ListViewAdapter.setAllChecked(!curChecked);
                }else {
                    mSearchGroundType1ListViewAdapter.setChecked(position);
                    if(mSearchGroundType1ListViewAdapter.isTotalChecked()) {
                        mSearchGroundType1ListViewAdapter.setChecked(0, true);
                    }else {
                        mSearchGroundType1ListViewAdapter.setChecked(0, false);
                    }
                }
                mSearchGroundType1ListViewAdapter.notifyDataSetChanged();

                search_grounds.clear();

                boolean[] isCheckedAll = mSearchGroundType1ListViewAdapter.getCheckedAll();

                for(int i = 1 ; i < isCheckedAll.length ; i++) {
                    if(isCheckedAll[i]) {
                        mSearchGroundType1ListViewAdapter.getItem(i);
                        search_grounds.add((JSONObject) mSearchGroundType1ListViewAdapter.getItem(i));
                    }
                }

                Log.e(TAG, search_grounds + "");


            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener - " + e);
            }

        }

    };

    AdapterView.OnItemClickListener mOnItemClickListener_3_1 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

            try {
                JSONArray mJSONArray = new JSONArray();
                mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                lv_search_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                JSONObject jsonObject = (JSONObject) adapterView.getAdapter().getItem(position);

                area_group_code = jsonObject.get("area_group_code")==null?"":jsonObject.get("area_group_code").toString();
                mService.searchAreaList(searchAreaList_Listener, area_group_code);
            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener - " + e);
            }

        }

    };

    AdapterView.OnItemClickListener mOnItemClickListener_3_2 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

            try {
                JSONObject jsonObject = (JSONObject) adapterView.getAdapter().getItem(position);

                String area_code = jsonObject.get("area_code")==null?"":jsonObject.get("area_code").toString();

                if("".equals(area_code)) {
                    search_type_code = "T";
                    search_loc_lat = "";
                    search_loc_lon = "";
                    search_area_code = area_code;
                    search_area_group_code = jsonObject.get("area_group_code")==null?"":jsonObject.get("area_group_code").toString();
                }else if("P".equals(area_code)) {
                    search_type_code = "P";
                    search_loc_lat = "";
                    search_loc_lon = "";
                    search_area_code = area_code;
                    search_area_group_code = jsonObject.get("area_group_code")==null?"":jsonObject.get("area_group_code").toString();
                }else {
                    search_type_code = "L";
                    search_loc_lat = "";
                    search_loc_lon = "";
                    search_area_code = area_code;
                    search_area_group_code = "";
                }

                mService.searchGroundList(searchGroundList3_Listener, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener - " + e);
            }

        }

    };

    /**
     * 구장 선택 종류 탭 초기화
     * Created by maloman72 on 2018-11-04
     * */
    private void setButtonGroundType() {
        bt_search_ground_type_1.setSelected(false);
        bt_search_ground_type_2.setSelected(false);
        bt_search_ground_type_3.setSelected(false);
    }

    ResponseListener searchGroundList1_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType1ListViewAdapter = new SearchGroundType1ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_1.setAdapter(mSearchGroundType1ListViewAdapter);

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

    ResponseListener searchGroundList2_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType2ListViewAdapter = new SearchGroundType2ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_2.setAdapter(mSearchGroundType2ListViewAdapter);

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

    ResponseListener searchGroundList3_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                } else if(mContext.getString(R.string.service_nothing).equals(mJSONObject.get(getString(R.string.result_code)))){
                    JSONArray mJSONArray = new JSONArray();

                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
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

    ResponseListener searchAreaGroupList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_1ListViewAdapter = new SearchGroundType3_1ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_1.setAdapter(mSearchGroundType3_1ListViewAdapter);

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

    ResponseListener searchAreaList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_2ListViewAdapter = new SearchGroundType3_2ListViewAdapter(mContext, mJSONArray);
                    lv_search_ground_type_3_2.setAdapter(mSearchGroundType3_2ListViewAdapter);

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchAreaList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
