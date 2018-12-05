package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
import java.util.Iterator;
import java.util.List;

import kr.com.yangle.teammatch.adapter.SearchGroundType1ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType2ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_1ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_2ListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchGroundType3_3ListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.LocationUtil;

public class SearchRegistGroundActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    String search_page_code, search_type_code, search_loc_lat, search_loc_lon, search_area_code, area_group_code, search_area_group_code;
    Button bt_search_regist_ground_type_1, bt_search_regist_ground_type_2, bt_search_regist_ground_type_3, bt_search_regist_ground_type_3_3_close, bt_search_regist_ground_select_end;
    ListView lv_search_regist_ground_type_1, lv_search_regist_ground_type_2, lv_search_regist_ground_type_3_1, lv_search_regist_ground_type_3_2, lv_search_regist_ground_type_3_3;
    LinearLayout ll_search_regist_ground_type_1, ll_search_regist_ground_type_2, ll_search_regist_ground_type_3, ll_search_regist_ground_type_3_3, ll_search_regist_ground_result_1, ll_search_regist_ground_result_2;
    HorizontalScrollView hs_search_regist_ground_result;

    SearchGroundType1ListViewAdapter mSearchGroundType1ListViewAdapter;
    SearchGroundType2ListViewAdapter mSearchGroundType2ListViewAdapter;
    SearchGroundType3_1ListViewAdapter mSearchGroundType3_1ListViewAdapter;
    SearchGroundType3_2ListViewAdapter mSearchGroundType3_2ListViewAdapter;
    SearchGroundType3_3ListViewAdapter mSearchGroundType3_3ListViewAdapter;

    List<JSONObject> search_regist_grounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_regist_ground);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        search_page_code = "HOPE";
        search_type_code = "F";
        search_loc_lat = "";
        search_loc_lon = "";
        search_area_code = "";

        mService.searchGroundList(searchGroundList1_Listener, search_page_code, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);

        bt_search_regist_ground_type_1 = findViewById(R.id.bt_search_regist_ground_type_1);
        bt_search_regist_ground_type_2 = findViewById(R.id.bt_search_regist_ground_type_2);
        bt_search_regist_ground_type_3 = findViewById(R.id.bt_search_regist_ground_type_3);
        bt_search_regist_ground_type_3_3_close = findViewById(R.id.bt_search_regist_ground_type_3_3_close);
        bt_search_regist_ground_select_end = findViewById(R.id.bt_search_regist_ground_select_end);

        lv_search_regist_ground_type_1 = findViewById(R.id.lv_search_regist_ground_type_1);
        lv_search_regist_ground_type_2 = findViewById(R.id.lv_search_regist_ground_type_2);
        lv_search_regist_ground_type_3_1 = findViewById(R.id.lv_search_regist_ground_type_3_1);
        lv_search_regist_ground_type_3_2 = findViewById(R.id.lv_search_regist_ground_type_3_2);
        lv_search_regist_ground_type_3_3 = findViewById(R.id.lv_search_regist_ground_type_3_3);

        ll_search_regist_ground_type_1 = findViewById(R.id.ll_search_regist_ground_type_1);
        ll_search_regist_ground_type_2 = findViewById(R.id.ll_search_regist_ground_type_2);
        ll_search_regist_ground_type_3 = findViewById(R.id.ll_search_regist_ground_type_3);
        ll_search_regist_ground_type_3_3 = findViewById(R.id.ll_search_regist_ground_type_3_3);
        ll_search_regist_ground_result_1 = findViewById(R.id.ll_search_regist_ground_result_1);
        ll_search_regist_ground_result_2 = findViewById(R.id.ll_search_regist_ground_result_2);

        hs_search_regist_ground_result = findViewById(R.id.hs_search_regist_ground_result);

        bt_search_regist_ground_type_1.setOnClickListener(mOnClickListener);
        bt_search_regist_ground_type_2.setOnClickListener(mOnClickListener);
        bt_search_regist_ground_type_3.setOnClickListener(mOnClickListener);
        bt_search_regist_ground_type_3_3_close.setOnClickListener(mOnClickListener);
        bt_search_regist_ground_select_end.setOnClickListener(mOnClickListener);

        lv_search_regist_ground_type_1.setOnItemClickListener(mOnItemClickListener_1);
        lv_search_regist_ground_type_2.setOnItemClickListener(mOnItemClickListener_2);
        lv_search_regist_ground_type_3_1.setOnItemClickListener(mOnItemClickListener_3_1);
        lv_search_regist_ground_type_3_2.setOnItemClickListener(mOnItemClickListener_3_2);
        lv_search_regist_ground_type_3_3.setOnItemClickListener(mOnItemClickListener_3_3);

        bt_search_regist_ground_type_1.setSelected(true);

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
                case R.id.bt_search_regist_ground_type_1 :
                    setButtonGroundType();
                    bt_search_regist_ground_type_1.setSelected(true);

                    setSelectedGroundResult(0);

                    ll_search_regist_ground_type_1.setVisibility(View.VISIBLE);
                    ll_search_regist_ground_type_2.setVisibility(View.GONE);
                    ll_search_regist_ground_type_3.setVisibility(View.GONE);

                    search_type_code = "F";
                    search_loc_lat = "";
                    search_loc_lon = "";
                    search_area_code = "";
                    search_area_group_code = "";

                    mService.searchGroundList(searchGroundList1_Listener, search_page_code, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
                    break;
                case R.id.bt_search_regist_ground_type_2 :
                    setButtonGroundType();
                    bt_search_regist_ground_type_2.setSelected(true);

                    setSelectedGroundResult(0);

                    ll_search_regist_ground_type_1.setVisibility(View.GONE);
                    ll_search_regist_ground_type_2.setVisibility(View.VISIBLE);
                    ll_search_regist_ground_type_3.setVisibility(View.GONE);

                    LocationUtil location = new LocationUtil(mContext);

                    search_type_code = "N";
                    search_loc_lat = location.getLastLatitude() + "";
                    search_loc_lon = location.getLastLongitude() + "";
                    search_area_code = "";
                    search_area_group_code = "";

                    mService.searchGroundList(searchGroundList2_Listener, search_page_code, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
                    break;
                case R.id.bt_search_regist_ground_type_3 :
                    setButtonGroundType();
                    bt_search_regist_ground_type_3.setSelected(true);

                    setSelectedGroundResult(0);

                    JSONArray mJSONArray = new JSONArray();
                    mSearchGroundType3_2ListViewAdapter = new SearchGroundType3_2ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_2.setAdapter(mSearchGroundType3_2ListViewAdapter);
                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                    ll_search_regist_ground_type_1.setVisibility(View.GONE);
                    ll_search_regist_ground_type_2.setVisibility(View.GONE);
                    ll_search_regist_ground_type_3.setVisibility(View.VISIBLE);

                    lv_search_regist_ground_type_3_2.setVisibility(View.VISIBLE);
                    ll_search_regist_ground_type_3_3.setVisibility(View.GONE);

                    mService.searchAreaGroupList(searchAreaGroupList_Listener);
                    break;
                case R.id.bt_search_regist_ground_type_3_3_close :

                    lv_search_regist_ground_type_3_2.setVisibility(View.VISIBLE);
                    ll_search_regist_ground_type_3_3.setVisibility(View.GONE);

                    break;
                case R.id.bt_search_regist_ground_select_end :
                    try {
                        ArrayList<String> search_regist_ground_ids = new ArrayList<>();
                        ArrayList<String> search_regist_ground_names = new ArrayList<>();
                        for (JSONObject search_regist_ground : search_regist_grounds) {
                            search_regist_ground_ids.add(search_regist_ground.get("ground_id").toString());
                            search_regist_ground_names.add(search_regist_ground.get("ground_name").toString());
                        }

                        if(search_regist_grounds.size() > 1) {
                            mApplicationTM.makeToast(mContext, "매치 등록시 구장은 1개만 선택할 수 있습니다.");
                            return;
                        }

                        Intent mIntent = new Intent();
                        mIntent.putExtra("search_regist_ground", search_regist_ground_ids);
                        mIntent.putExtra("search_regist_ground_name", search_regist_ground_names);
                        setResult(RESULT_OK, mIntent);
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
                mSearchGroundType1ListViewAdapter.setChecked(position);
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
                mSearchGroundType2ListViewAdapter.setChecked(position);
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
                lv_search_regist_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

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

                mService.searchGroundList(searchGroundList3_Listener, search_page_code, search_type_code, search_loc_lat, search_loc_lon, search_area_code, search_area_group_code);
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
                mSearchGroundType3_3ListViewAdapter.setChecked(position);
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
        bt_search_regist_ground_type_1.setSelected(false);
        bt_search_regist_ground_type_2.setSelected(false);
        bt_search_regist_ground_type_3.setSelected(false);
    }

    ResponseListener searchGroundList1_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType1ListViewAdapter = new SearchGroundType1ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_1.setAdapter(mSearchGroundType1ListViewAdapter);

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

    ResponseListener searchGroundList2_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType2ListViewAdapter = new SearchGroundType2ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_2.setAdapter(mSearchGroundType2ListViewAdapter);

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

    ResponseListener searchGroundList3_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                    setCheckBox(3);

                    lv_search_regist_ground_type_3_2.setVisibility(View.GONE);
                    ll_search_regist_ground_type_3_3.setVisibility(View.VISIBLE);

                } else if(mContext.getString(R.string.service_nothing).equals(mJSONObject.get(getString(R.string.result_code)))){
                    JSONArray mJSONArray = new JSONArray();

                    mSearchGroundType3_3ListViewAdapter = new SearchGroundType3_3ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_3.setAdapter(mSearchGroundType3_3ListViewAdapter);

                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
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

    ResponseListener searchAreaGroupList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_1ListViewAdapter = new SearchGroundType3_1ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_1.setAdapter(mSearchGroundType3_1ListViewAdapter);

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

    ResponseListener searchAreaList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    mSearchGroundType3_2ListViewAdapter = new SearchGroundType3_2ListViewAdapter(mContext, mJSONArray);
                    lv_search_regist_ground_type_3_2.setAdapter(mSearchGroundType3_2ListViewAdapter);

                    lv_search_regist_ground_type_3_2.setVisibility(View.VISIBLE);
                    ll_search_regist_ground_type_3_3.setVisibility(View.GONE);

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchAreaList_Listener - " + e);
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };

    private void setCheckBox(int type) {
        try {

            if(type == 1) {

                mSearchGroundType1ListViewAdapter.setAllChecked(false);

                if(search_regist_grounds.size() == mSearchGroundType1ListViewAdapter.getCount()-1) {
                    mSearchGroundType1ListViewAdapter.setAllChecked(true);
                }else {
                    for (int i = 0; i < search_regist_grounds.size(); i++) {
                        for (int j = 1; j < mSearchGroundType1ListViewAdapter.getCount(); j++) {
                            JSONObject jsonObject = ((JSONObject) mSearchGroundType1ListViewAdapter.getItem(j));
                            if (search_regist_grounds.get(i).getString("ground_id").equals(jsonObject.getString("ground_id"))) {
                                mSearchGroundType1ListViewAdapter.setChecked(j, true);
                                break;
                            }
                        }
                    }
                }

                mSearchGroundType1ListViewAdapter.notifyDataSetChanged();

            }else if(type == 2) {

                mSearchGroundType2ListViewAdapter.setAllChecked(false);

                if(search_regist_grounds.size() == mSearchGroundType2ListViewAdapter.getCount()) {
                    mSearchGroundType2ListViewAdapter.setAllChecked(true);
                }else {
                    for (int i = 0; i < search_regist_grounds.size(); i++) {
                        for (int j = 0; j < mSearchGroundType2ListViewAdapter.getCount(); j++) {
                            JSONObject jsonObject = ((JSONObject) mSearchGroundType2ListViewAdapter.getItem(j));
                            if (search_regist_grounds.get(i).getString("ground_id").equals(jsonObject.getString("ground_id"))) {
                                mSearchGroundType2ListViewAdapter.setChecked(j, true);
                                break;
                            }
                        }
                    }
                }

                mSearchGroundType2ListViewAdapter.notifyDataSetChanged();

            }else if(type == 3) {
                mSearchGroundType3_3ListViewAdapter.setAllChecked(false);

                for (int i = 0; i < search_regist_grounds.size(); i++) {
                    for (int j = 0; j < mSearchGroundType3_3ListViewAdapter.getCount(); j++) {
                        JSONObject jsonObject = ((JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(j));
                        if (search_regist_grounds.get(i).equals(jsonObject.getString("ground_id"))) {
                            mSearchGroundType3_3ListViewAdapter.setChecked(j, true);
                            break;
                        }
                    }
                }

                mSearchGroundType3_3ListViewAdapter.notifyDataSetChanged();
            }
        }catch (Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "setCheckBox - " + e);
            e.printStackTrace();
        }
    }

    private void setSelectedGroundResult(int type) {
        try {
            if(type == 1) {
                search_regist_grounds.clear();
                ll_search_regist_ground_result_1.removeAllViews();

                boolean[] isCheckedAll = mSearchGroundType1ListViewAdapter.getCheckedAll();

                for (int i = 0; i < isCheckedAll.length; i++) {
                    if (isCheckedAll[i]) {
                        JSONObject search_regist_ground = (JSONObject) mSearchGroundType1ListViewAdapter.getItem(i);

                        if(search_regist_ground != null) {
                            search_regist_grounds.add(search_regist_ground);
                        }
                    }
                }
            }else if(type == 2) {
                search_regist_grounds.clear();
                ll_search_regist_ground_result_1.removeAllViews();

                boolean[] isCheckedAll = mSearchGroundType2ListViewAdapter.getCheckedAll();

                for (int i = 0; i < isCheckedAll.length; i++) {
                    if (isCheckedAll[i]) {
                        JSONObject search_regist_ground = (JSONObject) mSearchGroundType2ListViewAdapter.getItem(i);

                        if(search_regist_ground != null) {
                            search_regist_grounds.add(search_regist_ground);
                        }
                    }
                }
            }else if(type == 3) {
                ll_search_regist_ground_result_1.removeAllViews();

                for (int i = 0; i < mSearchGroundType3_3ListViewAdapter.getCount(); i++) {
                    JSONObject search_hope_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                    Iterator<JSONObject> iter = search_regist_grounds.iterator();
                    while (iter.hasNext()) {
                        JSONObject json = iter.next();

                        if(json.getString("ground_id").equals(search_hope_ground.getString("ground_id")))
                            iter.remove();
                    }
                }

                for (int i = 0; i < mSearchGroundType3_3ListViewAdapter.getCount(); i++) {
                    JSONObject search_hope_ground = (JSONObject) mSearchGroundType3_3ListViewAdapter.getItem(i);

                    if(mSearchGroundType3_3ListViewAdapter.getChecked(i)) {
                        search_regist_grounds.add(search_hope_ground);
                    }
                }
            }else {
                search_regist_grounds.clear();
                ll_search_regist_ground_result_1.removeAllViews();
                ll_search_regist_ground_result_2.setVisibility(View.VISIBLE);
                hs_search_regist_ground_result.setVisibility(View.GONE);
                return;
            }

            if (search_regist_grounds.size() > 0) {
                ll_search_regist_ground_result_2.setVisibility(View.GONE);
                hs_search_regist_ground_result.setVisibility(View.VISIBLE);
            }else {
                ll_search_regist_ground_result_1.removeAllViews();
                ll_search_regist_ground_result_2.setVisibility(View.VISIBLE);
                hs_search_regist_ground_result.setVisibility(View.GONE);
            }

            for(int i = 0 ; i < search_regist_grounds.size() ; i++) {
                DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
                int mMarginsSize = Math.round(5 * mDisplayMetrics.density);
                int mPaddingSize = Math.round(10 * mDisplayMetrics.density);

                Button btn = new Button(mContext);
                btn.setText(search_regist_grounds.get(i).get("ground_name").toString());
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                );
                mLayoutParams.setMargins(mMarginsSize, 0, mMarginsSize, 0);
                btn.setLayoutParams(mLayoutParams);
                btn.setBackground(getResources().getDrawable(R.drawable.ic_button_select_ground));
                btn.setTextColor(getResources().getColor(R.color.color_userinfo_button_enable_text));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Typeface mTypeface = getResources().getFont(R.font.notosanskr_medium);
                    btn.setTypeface(mTypeface);
                    btn.setIncludeFontPadding(false);
                }
                btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                btn.setMinHeight(0);
                btn.setPadding(mPaddingSize, 0, mPaddingSize, 0);

                Drawable icon = getApplicationContext().getResources().getDrawable(R.drawable.ic_close_white);
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                btn.setCompoundDrawables(null, null, icon, null);
                btn.setCompoundDrawablePadding(10);

                final String btn_id = search_regist_grounds.get(i).getString("ground_id");

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            for (int a = 0; a < search_regist_grounds.size(); a++) {
                                Log.e(TAG, search_regist_grounds.get(a).getString("ground_id"));
                                if (btn_id.equals(search_regist_grounds.get(a).getString("ground_id"))) {
                                    search_regist_grounds.remove(search_regist_grounds.get(a));
                                    break;
                                }
                            }

                            if(bt_search_regist_ground_type_1.isSelected()) {
                                setCheckBox(1);
                                setSelectedGroundResult(1);
                            }else if(bt_search_regist_ground_type_2.isSelected()) {
                                setCheckBox(2);
                                setSelectedGroundResult(2);
                            }else if(bt_search_regist_ground_type_3.isSelected()) {
                                setCheckBox(3);
                                setSelectedGroundResult(3);
                            }

                        }catch(Exception e) {
                            Log.e(TAG, "btn.setOnClickListener - " + e);
                        }
                    }
                });

                ll_search_regist_ground_result_1.addView(btn);
            }

        }catch(Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "setSelectedGroundResult - " + e);
            e.printStackTrace();
        }
    }

}
