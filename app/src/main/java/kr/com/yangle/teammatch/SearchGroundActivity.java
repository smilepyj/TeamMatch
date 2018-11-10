package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
    LinearLayout ll_search_ground_type_1, ll_search_ground_type_2, ll_search_ground_type_3, ll_search_ground_result_1, ll_search_ground_result_2;
    HorizontalScrollView hs_search_ground_result;

    SearchGroundType1ListViewAdapter mSearchGroundType1ListViewAdapter;
    SearchGroundType2ListViewAdapter mSearchGroundType2ListViewAdapter;
    SearchGroundType3_1ListViewAdapter mSearchGroundType3_1ListViewAdapter;
    SearchGroundType3_2ListViewAdapter mSearchGroundType3_2ListViewAdapter;
    SearchGroundType3_3ListViewAdapter mSearchGroundType3_3ListViewAdapter;

    List<JSONObject> search_area_groups = new ArrayList<>();
    List<JSONObject> search_areas = new ArrayList<>();
    List<JSONObject> search_grounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ground);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        ll_search_ground_result_1 = findViewById(R.id.ll_search_ground_result_1);
        ll_search_ground_result_2 = findViewById(R.id.ll_search_ground_result_2);

        hs_search_ground_result = findViewById(R.id.hs_search_ground_result);

        bt_search_ground_type_1.setOnClickListener(mOnClickListener);
        bt_search_ground_type_2.setOnClickListener(mOnClickListener);
        bt_search_ground_type_3.setOnClickListener(mOnClickListener);
        bt_search_ground_select_end.setOnClickListener(mOnClickListener);

        lv_search_ground_type_1.setOnItemClickListener(mOnItemClickListener_1);
        lv_search_ground_type_2.setOnItemClickListener(mOnItemClickListener_2);
        lv_search_ground_type_3_1.setOnItemClickListener(mOnItemClickListener_3_1);
        lv_search_ground_type_3_2.setOnItemClickListener(mOnItemClickListener_3_2);
        lv_search_ground_type_3_3.setOnItemClickListener(mOnItemClickListener_3_3);


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

                    setSelectedGroundResult(0);

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

                    setSelectedGroundResult(0);

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

                    setSelectedGroundResult(0);

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
                        ArrayList<String> search_ground_ids = new ArrayList<>();
                        ArrayList<String> search_ground_names = new ArrayList<>();
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

                if("F".equals(ground_id)) {
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

                setSelectedGroundResult(1);

            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener_1 - " + e);
            }

        }

    };

    AdapterView.OnItemClickListener mOnItemClickListener_2 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

            try {
                JSONObject jsonObject = (JSONObject) adapterView.getAdapter().getItem(position);

                String ground_id = jsonObject.get("ground_id")==null?"":jsonObject.get("ground_id").toString();

                Log.e(TAG, ground_id + "");

                boolean curChecked = mSearchGroundType2ListViewAdapter.getChecked(position);

                if("N".equals(ground_id)) {
                    mSearchGroundType2ListViewAdapter.setAllChecked(!curChecked);
                }else {
                    mSearchGroundType2ListViewAdapter.setChecked(position);
                }
                mSearchGroundType2ListViewAdapter.notifyDataSetChanged();

                setSelectedGroundResult(2);

            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener_2 - " + e);
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

    AdapterView.OnItemClickListener mOnItemClickListener_3_3 = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

            try {
                JSONObject jsonObject = (JSONObject) adapterView.getAdapter().getItem(position);

                String ground_id = jsonObject.get("ground_id")==null?"":jsonObject.get("ground_id").toString();

                Log.e(TAG, ground_id + "");

                boolean curChecked = mSearchGroundType3_3ListViewAdapter.getChecked(position);

                if("L".equals(ground_id) || "T".equals(ground_id) || "P".equals(ground_id)) {
                    mSearchGroundType3_3ListViewAdapter.setAllChecked(!curChecked);
                }else {
                    mSearchGroundType3_3ListViewAdapter.setChecked(position);
                    if(mSearchGroundType3_3ListViewAdapter.isTotalChecked()) {
                        mSearchGroundType3_3ListViewAdapter.setChecked(0, true);
                    }else {
                        mSearchGroundType3_3ListViewAdapter.setChecked(0, false);
                    }
                }
                mSearchGroundType3_3ListViewAdapter.notifyDataSetChanged();

                setSelectedGroundResult(3);

            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnItemClickListener_2 - " + e);
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

                    String ground_id = ((JSONObject)mJSONArray.get(0)).get("ground_id").toString();
                    String area_group_code = ((JSONObject)mJSONArray.get(0)).get("area_group_code").toString();

                    if("T".equals(ground_id)) {
                        for(int i = 0 ; i < search_area_groups.size() ; i++) {
                            if(area_group_code.equals(search_area_groups.get(i).get("area_group_code"))) {
                                mSearchGroundType3_3ListViewAdapter.setAllChecked(true);
                                break;
                            }
                        }

                        if(!mSearchGroundType3_3ListViewAdapter.isTotalChecked()) {
                            for(int i = 0 ; i < search_grounds.size() ; i++) {
                                for(int j = 1 ; j < mJSONArray.length() ; i++) {
                                    JSONObject jsonObject = ((JSONObject)mJSONArray.get(j));
                                    if(search_grounds.get(i).equals(jsonObject.get("ground_id"))) {
                                        mSearchGroundType3_3ListViewAdapter.setChecked(j, true);
                                    }
                                }
                            }
                        }
                    }else if("P".equals(ground_id)) {
                        for(int i = 0 ; i < search_area_groups.size() ; i++) {
                            if(area_group_code.equals(search_area_groups.get(i).get("area_group_code"))) {
                                mSearchGroundType3_3ListViewAdapter.setAllChecked(true);
                                break;
                            }
                        }

                        if(!mSearchGroundType3_3ListViewAdapter.isTotalChecked()) {
                            for(int i = 0 ; i < search_grounds.size() ; i++) {
                                for(int j = 1 ; j < mJSONArray.length() ; i++) {
                                    JSONObject jsonObject = ((JSONObject)mJSONArray.get(j));
                                    if(search_grounds.get(i).equals(jsonObject.get("ground_id"))) {
                                        mSearchGroundType3_3ListViewAdapter.setChecked(j, true);
                                    }
                                }
                            }
                        }
                    }else if("L".equals(ground_id)) {
                        for(int i = 0 ; i < search_area_groups.size() ; i++) {
                            if(area_group_code.equals(search_area_groups.get(i).get("area_group_code"))) {
                                mSearchGroundType3_3ListViewAdapter.setAllChecked(true);
                                break;
                            }
                        }

                        for(int i = 0 ; i < search_areas.size() ; i++) {
                            if(area_group_code.equals(search_areas.get(i).get("area_code"))) {
                                mSearchGroundType3_3ListViewAdapter.setAllChecked(true);
                                break;
                            }
                        }

                        if(!mSearchGroundType3_3ListViewAdapter.isTotalChecked()) {
                            for(int i = 0 ; i < search_grounds.size() ; i++) {
                                for(int j = 1 ; j < mJSONArray.length() ; j++) {
                                    JSONObject jsonObject = ((JSONObject)mJSONArray.get(j));
                                    if(search_grounds.get(i).equals(jsonObject.get("ground_id"))) {
                                        mSearchGroundType3_3ListViewAdapter.setChecked(j, true);
                                    }
                                }
                            }
                        }
                    }

                    mSearchGroundType3_3ListViewAdapter.notifyDataSetChanged();

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


    private void setSelectedGroundResult(int type) {
        try {
            if(type == 1) {
                search_area_groups.clear();
                search_areas.clear();
                search_grounds.clear();
                ll_search_ground_result_1.removeAllViews();

                boolean[] isCheckedAll = mSearchGroundType1ListViewAdapter.getCheckedAll();

                for (int i = 1; i < isCheckedAll.length; i++) {
                    if (isCheckedAll[i]) {
                        JSONObject search_ground = (JSONObject) mSearchGroundType1ListViewAdapter.getItem(i);

                        if(search_ground != null) {
                            search_grounds.add(search_ground);
                        }
                    }
                }
            }else if(type == 2) {
                search_area_groups.clear();
                search_areas.clear();
                search_grounds.clear();
                ll_search_ground_result_1.removeAllViews();

                boolean[] isCheckedAll = mSearchGroundType2ListViewAdapter.getCheckedAll();

                for (int i = 0; i < isCheckedAll.length; i++) {
                    if (isCheckedAll[i]) {
                        JSONObject search_ground = (JSONObject) mSearchGroundType2ListViewAdapter.getItem(i);

                        if(search_ground != null) {
                            search_grounds.add(search_ground);
                        }
                    }
                }
            }else if(type == 3) {
                ll_search_ground_result_1.removeAllViews();

                boolean[] isCheckedAll = mSearchGroundType3_3ListViewAdapter.getCheckedAll();
                boolean isTotalChecked = mSearchGroundType3_3ListViewAdapter.getChecked(0);

                JSONObject jsonObject = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(0);

                String ground_id = jsonObject.get("ground_id").toString();
                String area_group_code = jsonObject.get("area_group_code").toString();

                Log.e(TAG, ground_id);

                if("T".equals(ground_id)) {
                    JSONObject areaGroupCodeObject = new JSONObject();
                    areaGroupCodeObject.put("area_group_code", jsonObject.get("area_group_code"));
                    areaGroupCodeObject.put("ground_name", jsonObject.get("ground_name"));

                    /* 로직
                     * 1. 전체 선택인지 아닌지 체크
                     * 2. 전체 선택일 경우 search_area_groups에 추가, search_areas, search_grounds에 선택되었던 데이터 삭제
                     * 3. 아닐 경우 search_area_groups에 삭제, search_grounds에 선택한 데이터 추가
                      */
                    if (isTotalChecked) {
                        search_area_groups.add(areaGroupCodeObject);

                        Log.e(TAG, search_areas + "");

                        for (int i = 0; i < search_areas.size(); i++) {
                            if(area_group_code.equals(search_areas.get(i).get("area_group_code"))) {
                                search_areas.remove(search_areas.get(i));
                            }
                        }

                        Log.e(TAG, search_grounds + "");

                        for (int i = 0; i < search_grounds.size(); i++) {
                            if(area_group_code.equals(search_grounds.get(i).get("area_group_code"))) {
                                search_grounds.remove(search_grounds.get(i));
                            }
                        }

                        /*for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.remove(search_ground);
                                }
                            }
                        }*/
                    }else {
                        search_area_groups.remove(areaGroupCodeObject);

                        for (int i = 1; i < mSearchGroundType3_3ListViewAdapter.getCount(); i++) {
                            JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                            if (search_ground != null) {
                                search_grounds.remove(search_ground);
                            }
                        }

                        for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.add(search_ground);
                                }
                            }
                        }
                    }
                }else if("P".equals(ground_id)) {
                    JSONObject areaGroupCodeObject = new JSONObject();
                    areaGroupCodeObject.put("area_group_code", jsonObject.get("area_group_code"));
                    areaGroupCodeObject.put("ground_name", jsonObject.get("ground_name"));

                    /* 로직
                     * 1. 전체 선택인지 아닌지 체크
                     * 2. 전체 선택일 경우 search_area_groups에 추가, search_grounds에 선택되었던 데이터 삭제
                     * 3. 아닐 경우 search_area_groups에 삭제, search_grounds에 선택한 데이터 추가
                     */
                    if (isTotalChecked) {
                        search_area_groups.add(areaGroupCodeObject);

                        Log.e(TAG, search_areas + "");

                        for (int i = 0; i < search_areas.size(); i++) {
                            if(area_group_code.equals(search_areas.get(i).get("area_group_code"))) {
                                search_areas.remove(search_areas.get(i));
                            }
                        }

                        Log.e(TAG, search_grounds + "");

                        for (int i = 0; i < search_grounds.size(); i++) {
                            if(area_group_code.equals(search_grounds.get(i).get("area_group_code"))) {
                                search_grounds.remove(search_grounds.get(i));
                            }
                        }

                        /*for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.remove(search_ground);
                                }
                            }
                        }*/
                    }else {
                        search_area_groups.remove(areaGroupCodeObject);

                        for (int i = 1; i < mSearchGroundType3_3ListViewAdapter.getCount(); i++) {
                            JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                            if (search_ground != null) {
                                search_grounds.remove(search_ground);
                            }
                        }

                        for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.add(search_ground);
                                }
                            }
                        }
                    }
                }else if("L".equals(ground_id)) {
                    JSONObject areaGroupCodeObject = new JSONObject();
                    areaGroupCodeObject.put("area_group_code", ((JSONObject)mSearchGroundType3_2ListViewAdapter.getItem(0)).get("area_group_code").toString());
                    areaGroupCodeObject.put("ground_name", ((JSONObject)mSearchGroundType3_2ListViewAdapter.getItem(0)).get("gugun_name").toString());

                    /* 로직
                     * 1. 전체 선택인지 아닌지 체크
                     * 2. 전체 선택일 경우
                     * 2-1. search_areas에 추가, search_grounds에 선택되었던 데이터 삭제
                     * 2-2. 해당 그룹의 모든 값이 다 선택인지 체크하여 모두 선택일 경우 search_area_groups에 추가, 아닐 경우 삭제
                     * 3. 아닐 경우 search_area_groups에 해당 그룹 삭제, search_areas에 삭제, search_grounds에 선택한 데이터 추가
                     */
                    if (isTotalChecked) {
                        search_areas.add(jsonObject);

                        Log.e(TAG, search_grounds + "");

                        for (int i = 0; i < search_grounds.size(); i++) {
                            if(jsonObject.get("area_code").equals(search_grounds.get(i).get("area_code"))) {
                                search_grounds.remove(search_grounds.get(i));
                            }
                        }

                        /*for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.remove(search_ground);
                                }
                            }
                        }*/

                        boolean isSelectedAreaGroup = false;

                        for(int i = 2; i < mSearchGroundType3_2ListViewAdapter.getCount() ; i++) {
                            String area_code = ((JSONObject)mSearchGroundType3_2ListViewAdapter.getItem(i)).get("area_code").toString();

                            for(int j = 0 ; j < search_areas.size() ; j++) {
                                if(area_code.equals(search_areas.get(j).get("area_code"))) {
                                    break;
                                }
                                isSelectedAreaGroup = true;
                            }
                        }

                        if(isSelectedAreaGroup) {
                            search_area_groups.add(areaGroupCodeObject);
                        }else {
                            search_area_groups.remove(areaGroupCodeObject);
                        }

                    }else {
                        for(int i = 0; i < search_area_groups.size() ; i++) {
                            JSONObject area_group = search_area_groups.get(i);
                            if(area_group_code.equals(area_group.get("area_group_code"))) {
                                search_area_groups.remove(area_group);
                                break;
                            }
                        }

                        search_areas.remove(jsonObject);

                        Log.e(TAG, search_grounds + "");

                        for (int i = 0; i < search_grounds.size(); i++) {
                            if(jsonObject.get("area_code").equals(search_grounds.get(i).get("area_code"))) {
                                search_grounds.remove(search_grounds.get(i));
                            }
                        }

                        /*for (int i = 1; i < mSearchGroundType3_3ListViewAdapter.getCount(); i++) {
                            JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                            if (search_ground != null) {
                                search_grounds.remove(search_ground);
                            }
                        }*/

                        for (int i = 1; i < isCheckedAll.length; i++) {
                            if (isCheckedAll[i]) {
                                JSONObject search_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                                if (search_ground != null) {
                                    search_grounds.add(search_ground);
                                }
                            }
                        }
                    }
                }
            }else {
                search_area_groups.clear();
                search_areas.clear();
                search_grounds.clear();
                ll_search_ground_result_1.removeAllViews();
                ll_search_ground_result_2.setVisibility(View.VISIBLE);
                hs_search_ground_result.setVisibility(View.GONE);
                return;
            }



            if (search_area_groups.size() > 0 || search_areas.size() > 0 || search_grounds.size() > 0) {
                ll_search_ground_result_2.setVisibility(View.GONE);
                hs_search_ground_result.setVisibility(View.VISIBLE);
            }else {
                ll_search_ground_result_2.setVisibility(View.VISIBLE);
                hs_search_ground_result.setVisibility(View.GONE);
            }

            for(int i = 0 ; i < search_area_groups.size() ; i++) {
                Button btn = new Button(mContext);
                btn.setText(search_area_groups.get(i).get("ground_name").toString());
                btn.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                btn.setBackground(getResources().getDrawable(R.drawable.ic_button_select_ground));
                btn.setTextColor(getResources().getColor(R.color.color_userinfo_button_enable_text));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Typeface mTypeface = getResources().getFont(R.font.notosanskr_medium);
                    btn.setTypeface(mTypeface);
                    btn.setIncludeFontPadding(false);
                }
                btn.setMinHeight(0);
                btn.setPadding(0, 0, 10, 0);
                btn.setTextSize(getResources().getDimensionPixelSize(R.dimen.font_size_5));
                Drawable icon = getResources().getDrawable(R.drawable.ic_close);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                btn.setCompoundDrawables(null, null, icon, null);
                btn.setCompoundDrawablePadding(10);

                ll_search_ground_result_1.addView(btn);
            }

            for(int i = 0 ; i < search_areas.size() ; i++) {

                Button btn = new Button(mContext);
                btn.setText(search_areas.get(i).get("ground_name").toString());
                btn.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                btn.setBackground(getResources().getDrawable(R.drawable.ic_button_select_ground));
                btn.setTextColor(getResources().getColor(R.color.color_userinfo_button_enable_text));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Typeface mTypeface = getResources().getFont(R.font.notosanskr_medium);
                    btn.setTypeface(mTypeface);
                    btn.setIncludeFontPadding(false);
                }
                btn.setMinHeight(0);
                btn.setPadding(0, 0, 10, 0);
                btn.setTextSize(getResources().getDimensionPixelSize(R.dimen.font_size_5));
                Drawable icon = getApplicationContext().getResources().getDrawable(R.drawable.ic_close);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                btn.setCompoundDrawables(null, null, icon, null);
                btn.setCompoundDrawablePadding(10);

                ll_search_ground_result_1.addView(btn);
            }

            for(int i = 0 ; i < search_grounds.size() ; i++) {

                Button btn = new Button(mContext);
                btn.setText(search_grounds.get(i).get("ground_name").toString());
                btn.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                btn.setBackground(getResources().getDrawable(R.drawable.ic_button_select_ground));
                btn.setTextColor(getResources().getColor(R.color.color_userinfo_button_enable_text));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Typeface mTypeface = getResources().getFont(R.font.notosanskr_medium);
                    btn.setTypeface(mTypeface);
                    btn.setIncludeFontPadding(false);
                }
                btn.setMinHeight(0);
                btn.setPadding(0, 0, 10, 0);
                btn.setTextSize(getResources().getDimensionPixelSize(R.dimen.font_size_5));
                Drawable icon = getApplicationContext().getResources().getDrawable(R.drawable.ic_close);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                btn.setCompoundDrawables(null, null, icon, null);
                btn.setCompoundDrawablePadding(10);

                ll_search_ground_result_1.addView(btn);
            }

        }catch(Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "setSelectedGroundResult - " + e);
        }
    }

}
