package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.BackPressCloseHandler;

public class UserInfoActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    String mActivityType;

    EditText et_user_info_user_name, et_user_info_phone_number, et_user_info_team_name;

    Button bt_user_info_hope_grounds_1, bt_user_info_hope_grounds_2, bt_user_info_hope_grounds_3, bt_user_info_hope_grounds_4;

    Button bt_user_info_age_10, bt_user_info_age_20, bt_user_info_age_30, bt_user_info_age_40, bt_user_info_age_50, bt_user_info_level_challenger, bt_user_info_level_diamond, bt_user_info_level_platinum,
            bt_user_info_level_gold, bt_user_info_level_silver, bt_user_info_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.user_info_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        et_user_info_user_name = findViewById(R.id.et_user_info_user_name);
        et_user_info_phone_number = findViewById(R.id.et_user_info_phone_number);
        et_user_info_team_name = findViewById(R.id.et_user_info_team_name);
        bt_user_info_hope_grounds_1 = findViewById(R.id.bt_user_info_hope_grounds_1);
        bt_user_info_hope_grounds_2 = findViewById(R.id.bt_user_info_hope_grounds_2);
        bt_user_info_hope_grounds_3 = findViewById(R.id.bt_user_info_hope_grounds_3);
        bt_user_info_hope_grounds_4 = findViewById(R.id.bt_user_info_hope_grounds_4);
        bt_user_info_age_10 = findViewById(R.id.bt_user_info_age_10);
        bt_user_info_age_20 = findViewById(R.id.bt_user_info_age_20);
        bt_user_info_age_30 = findViewById(R.id.bt_user_info_age_30);
        bt_user_info_age_40 = findViewById(R.id.bt_user_info_age_40);
        bt_user_info_age_50 = findViewById(R.id.bt_user_info_age_50);
        bt_user_info_level_challenger = findViewById(R.id.bt_user_info_level_challenger);
        bt_user_info_level_diamond = findViewById(R.id.bt_user_info_level_diamond);
        bt_user_info_level_platinum = findViewById(R.id.bt_user_info_level_platinum);
        bt_user_info_level_gold = findViewById(R.id.bt_user_info_level_gold);
        bt_user_info_level_silver = findViewById(R.id.bt_user_info_level_silver);
        bt_user_info_save = findViewById(R.id.bt_user_info_save);

        bt_user_info_age_10.setOnClickListener(mOnClickListener);
        bt_user_info_age_20.setOnClickListener(mOnClickListener);
        bt_user_info_age_30.setOnClickListener(mOnClickListener);
        bt_user_info_age_40.setOnClickListener(mOnClickListener);
        bt_user_info_age_50.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_1.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_2.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_3.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_4.setOnClickListener(mOnClickListener);
        bt_user_info_level_challenger.setOnClickListener(mOnClickListener);
        bt_user_info_level_diamond.setOnClickListener(mOnClickListener);
        bt_user_info_level_platinum.setOnClickListener(mOnClickListener);
        bt_user_info_level_gold.setOnClickListener(mOnClickListener);
        bt_user_info_level_silver.setOnClickListener(mOnClickListener);
        bt_user_info_save.setOnClickListener(mOnClickListener);

        mActivityType = getIntent().getStringExtra(getString(R.string.user_info_intent_extra));

        if(getString(R.string.user_info_type_update).equals(mActivityType)) {
            bt_user_info_save.setText(getString(R.string.user_info_update));

            mService.searchUserInfo(searchUserInfo_Listener, mApplicationTM.getUserEmail());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                if(getString(R.string.user_info_type_input).equals(mActivityType)) {
                    mApplicationTM.UserInfoExitDialog(this, mContext, getString(R.string.aloer_dialog_title), getString(R.string.user_info_input_exit));
                } else {
                    finish();
                }
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Back Button Listener
     * 첫 로그인 입력과 조회 진입 시 처리 구분
     * Created by maloman72 on 2018-10-05
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(getString(R.string.user_info_type_input).equals(mActivityType)) {
                    mApplicationTM.UserInfoExitDialog(this, mContext, getString(R.string.aloer_dialog_title), getString(R.string.user_info_input_exit));
                } else {
                    finish();
                }

                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_user_info_age_10 :
                    setDefaultButtonAge();
                    bt_user_info_age_10.setSelected(true);
                    break;
                case R.id.bt_user_info_age_20 :
                    setDefaultButtonAge();
                    bt_user_info_age_20.setSelected(true);
                    break;
                case R.id.bt_user_info_age_30 :
                    setDefaultButtonAge();
                    bt_user_info_age_30.setSelected(true);
                    break;
                case R.id.bt_user_info_age_40 :
                    setDefaultButtonAge();
                    bt_user_info_age_40.setSelected(true);
                    break;
                case R.id.bt_user_info_age_50 :
                    setDefaultButtonAge();
                    bt_user_info_age_50.setSelected(true);
                    break;
                case R.id.bt_user_info_hope_grounds_1 :
                case R.id.bt_user_info_hope_grounds_2 :
                case R.id.bt_user_info_hope_grounds_3 :
                case R.id.bt_user_info_hope_grounds_4 :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message_search_grounds));
                    break;
                case R.id.bt_user_info_level_challenger :
                    setDefaultButtonLevel();
                    bt_user_info_level_challenger.setSelected(true);
                    break;
                case R.id.bt_user_info_level_diamond :
                    setDefaultButtonLevel();
                    bt_user_info_level_diamond.setSelected(true);
                    break;
                case R.id.bt_user_info_level_platinum :
                    setDefaultButtonLevel();
                    bt_user_info_level_platinum.setSelected(true);
                    break;
                case R.id.bt_user_info_level_gold :
                    setDefaultButtonLevel();
                    bt_user_info_level_gold.setSelected(true);
                    break;
                case R.id.bt_user_info_level_silver :
                    setDefaultButtonLevel();
                    bt_user_info_level_silver.setSelected(true);
                    break;
                case R.id.bt_user_info_save :
                    if(getString(R.string.user_info_type_input).equals(mActivityType)) {
                        Check_insertUserInfo();
                    } else if (getString(R.string.user_info_type_update).equals(mActivityType)) {
                        mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    }
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 팀 연령 버튼 선택 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setDefaultButtonAge() {
        bt_user_info_age_10.setSelected(false);
        bt_user_info_age_20.setSelected(false);
        bt_user_info_age_30.setSelected(false);
        bt_user_info_age_40.setSelected(false);
        bt_user_info_age_50.setSelected(false);
    }

    /**
     * 팀 레벨 버튼 선택 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setDefaultButtonLevel() {
        bt_user_info_level_challenger.setSelected(false);
        bt_user_info_level_diamond.setSelected(false);
        bt_user_info_level_platinum.setSelected(false);
        bt_user_info_level_gold.setSelected(false);
        bt_user_info_level_silver.setSelected(false);
    }

    /**
     * 회원 정보 등록 데이터 체크
     * Created by maloman72 on 2018-11-01
     * */
    private void Check_insertUserInfo() {
        String user_name = et_user_info_user_name.getText().toString();
        String user_telnum = et_user_info_phone_number.getText().toString();
        String team_name = et_user_info_team_name.getText().toString();
        ArrayList<String> hope_grounds = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.default_team_code)));
        String team_level_code = "", team_age_code = "";

        if("".equals(user_name)) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_user_name) + getString(R.string.user_info_check_input_space));
            return;
        }

        if("".equals(user_telnum)) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_phone_number) + getString(R.string.user_info_check_input_space));
            return;
        }

        if("".equals(team_name)) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_name) + getString(R.string.user_info_check_input_space));
            return;
        }

        if(!bt_user_info_age_10.isSelected() && !bt_user_info_age_20.isSelected() && !bt_user_info_age_30.isSelected() && !bt_user_info_age_40.isSelected() && !bt_user_info_age_50.isSelected()) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_age) + getString(R.string.user_info_check_select_not));
            return;
        } else {
            if(bt_user_info_age_10.isSelected()) {
                team_age_code = getResources().getStringArray(R.array.C001_code)[0];
            } else if(bt_user_info_age_20.isSelected()) {
                team_age_code = getResources().getStringArray(R.array.C001_code)[1];
            } else if(bt_user_info_age_30.isSelected()) {
                team_age_code = getResources().getStringArray(R.array.C001_code)[2];
            } else if(bt_user_info_age_40.isSelected()) {
                team_age_code = getResources().getStringArray(R.array.C001_code)[3];
            } else if(bt_user_info_age_50.isSelected()) {
                team_age_code = getResources().getStringArray(R.array.C001_code)[4];
            }
        }

        if(!bt_user_info_level_challenger.isSelected() && !bt_user_info_level_diamond.isSelected() && !bt_user_info_level_platinum.isSelected() && !bt_user_info_level_gold.isSelected() && !bt_user_info_level_silver.isSelected()) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_level) + getString(R.string.user_info_check_select_not));
            return;
        } else {
            if(bt_user_info_level_challenger.isSelected()) {
                team_level_code = getResources().getStringArray(R.array.C002_code)[0];
            } else if(bt_user_info_level_diamond.isSelected()) {
                team_level_code = getResources().getStringArray(R.array.C002_code)[1];
            } else if(bt_user_info_level_platinum.isSelected()) {
                team_level_code = getResources().getStringArray(R.array.C002_code)[2];
            } else if(bt_user_info_level_gold.isSelected()) {
                team_level_code = getResources().getStringArray(R.array.C002_code)[3];
            } else if(bt_user_info_level_silver.isSelected()) {
                team_level_code = getResources().getStringArray(R.array.C002_code)[4];
            }
        }

        mService.insertUserInfo(insertUserInfo_Listener, mApplicationTM.getUserEmail(), user_name, user_telnum, team_name, hope_grounds, team_level_code, team_age_code);
    }

    /**
     * insertUserInfo Service Listener
     * Created by maloman72 on 2018-11-02
     * */
    ResponseListener insertUserInfo_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                if(responseEvent.getResultData() != null) {
                    JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                    Log.e(TAG, mJSONObject.toString());

                    if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                        mApplicationTM.makeToast(mContext, getString(R.string.user_info_service_input_sucess));

                        Intent mInent = new Intent(mContext, MainActivity.class);
                        startActivity(mInent);
                        finish();
                    } else {
                        mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                    }
                } else {
                    mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "insertUserInfo_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };

    /**
     * searchUserInfo Service Listenr
     * Created by maloman72 on 2018-11-02
     * */
    ResponseListener searchUserInfo_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));
                    JSONObject mResult = mJSONArray.getJSONObject(0);

                    et_user_info_user_name.setText(mResult.get("user_name").toString());
                    et_user_info_phone_number.setText(mResult.get("user_telnum").toString());
                    et_user_info_team_name.setText(mResult.get("team_name").toString());

                    mApplicationTM.setTeamAge(mResult.get("team_age_code").toString());
                    if(mResult.get("team_age_code").equals(getResources().getStringArray(R.array.C001_code)[0])) {
                        bt_user_info_age_10.setSelected(true);
                    } else if(mResult.get("team_age_code").equals(getResources().getStringArray(R.array.C001_code)[1])) {
                        bt_user_info_age_20.setSelected(true);
                    } else if(mResult.get("team_age_code").equals(getResources().getStringArray(R.array.C001_code)[2])) {
                        bt_user_info_age_30.setSelected(true);
                    } else if(mResult.get("team_age_code").equals(getResources().getStringArray(R.array.C001_code)[3])) {
                        bt_user_info_age_40.setSelected(true);
                    } else if(mResult.get("team_age_code").equals(getResources().getStringArray(R.array.C001_code)[4])) {
                        bt_user_info_age_50.setSelected(true);
                    }

                    mApplicationTM.setTeamAge(mResult.get("team_level_code").toString());
                    if(mResult.get("team_level_code").equals(getResources().getStringArray(R.array.C002_code)[0])) {
                        bt_user_info_level_challenger.setSelected(true);
                    } else if(mResult.get("team_level_code").equals(getResources().getStringArray(R.array.C002_code)[1])) {
                        bt_user_info_level_diamond.setSelected(true);
                    } else if(mResult.get("team_level_code").equals(getResources().getStringArray(R.array.C002_code)[2])) {
                        bt_user_info_level_platinum.setSelected(true);
                    } else if(mResult.get("team_level_code").equals(getResources().getStringArray(R.array.C002_code)[3])) {
                        bt_user_info_level_gold.setSelected(true);
                    } else if(mResult.get("team_level_code").equals(getResources().getStringArray(R.array.C002_code)[4])) {
                        bt_user_info_level_silver.setSelected(true);
                    }

                    mApplicationTM.setHopeGrounds(mResult.getJSONArray("hope_grounds"));
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchUserInfo_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };

}
