package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class UserInfoActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    ArrayList<String> hope_ground;
    ArrayList<String> hope_ground_name;

    String mActivityType;

    String search_user_team_id;

    EditText et_user_info_user_name, et_user_info_phone_number, et_user_info_team_name;

    Button bt_user_info_hope_grounds_1, bt_user_info_hope_grounds_2, bt_user_info_hope_grounds_3, bt_user_info_hope_grounds_4, bt_user_info_age, bt_user_info_level, bt_user_info_level_test, bt_user_info_save;

    TextView tv_user_info_add_grounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        et_user_info_user_name = findViewById(R.id.et_user_info_user_name);
        et_user_info_phone_number = findViewById(R.id.et_user_info_phone_number);
        et_user_info_team_name = findViewById(R.id.et_user_info_team_name);
        bt_user_info_hope_grounds_1 = findViewById(R.id.bt_user_info_hope_grounds_1);
        bt_user_info_hope_grounds_2 = findViewById(R.id.bt_user_info_hope_grounds_2);
        bt_user_info_hope_grounds_3 = findViewById(R.id.bt_user_info_hope_grounds_3);
        bt_user_info_hope_grounds_4 = findViewById(R.id.bt_user_info_hope_grounds_4);
        bt_user_info_age = findViewById(R.id.bt_user_info_age);
        bt_user_info_level = findViewById(R.id.bt_user_info_level);
        bt_user_info_level_test = findViewById(R.id.bt_user_info_level_test);
        tv_user_info_add_grounds = findViewById(R.id.tv_user_info_add_grounds);
        bt_user_info_save = findViewById(R.id.bt_user_info_save);

        bt_user_info_age.setOnClickListener(mOnClickListener);
        bt_user_info_level.setOnClickListener(mOnClickListener);
        bt_user_info_level_test.setOnClickListener(mOnClickListener);
        tv_user_info_add_grounds.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_1.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_2.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_3.setOnClickListener(mOnClickListener);
        bt_user_info_hope_grounds_4.setOnClickListener(mOnClickListener);
        bt_user_info_save.setOnClickListener(mOnClickListener);

        mActivityType = getIntent().getStringExtra(getString(R.string.user_info_intent_extra));

        if(getString(R.string.user_info_type_update).equals(mActivityType)) {
            bt_user_info_save.setText(getString(R.string.user_info_update));

            mService.searchUserInfo(searchUserInfo_Listener, mApplicationTM.getUserEmail());
        } else {
            et_user_info_user_name.setText(mApplicationTM.getUserName());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    hope_ground = data.getStringArrayListExtra("search_ground");
                    hope_ground_name = data.getStringArrayListExtra("search_ground_name");

                    if (hope_ground_name != null && hope_ground_name.size() > 0) {
                        if(hope_ground_name.size() >= 1) {
                            bt_user_info_hope_grounds_1.setText(hope_ground_name.get(0));
                            bt_user_info_hope_grounds_1.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_1.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 2) {
                            bt_user_info_hope_grounds_2.setText(hope_ground_name.get(1));
                            bt_user_info_hope_grounds_2.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_2.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 3) {
                            bt_user_info_hope_grounds_3.setText(hope_ground_name.get(2));
                            bt_user_info_hope_grounds_3.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_3.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 4) {
                            bt_user_info_hope_grounds_4.setText(hope_ground_name.get(3));
                            bt_user_info_hope_grounds_4.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_4.setVisibility(View.GONE);
                        }

                    }else {
                        bt_user_info_hope_grounds_1.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_2.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_3.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_4.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.bt_user_info_age:
                        TeamAge_AlertDialog();
                        break;
                    case R.id.bt_user_info_hope_grounds_1:
                        removeSelectedHopeGround(0);
                        break;
                    case R.id.bt_user_info_hope_grounds_2:
                        removeSelectedHopeGround(1);
                        break;
                    case R.id.bt_user_info_hope_grounds_3:
                        removeSelectedHopeGround(2);
                        break;
                    case R.id.bt_user_info_hope_grounds_4:
                        removeSelectedHopeGround(3);
                        break;
                    case R.id.tv_user_info_add_grounds:
                        Intent mIntent = new Intent(mContext, SearchGroundActivity.class);
                        mIntent.putExtra("callActivityFlag", 3);
                        startActivityForResult(mIntent, 1);
                        break;
                    case R.id.bt_user_info_level:
                        TeamLevle_AlertDialog();
                        break;
                    case R.id.bt_user_info_save:
                        if (getString(R.string.user_info_type_input).equals(mActivityType)) {
                            Check_insertUserInfo();
                        } else if (getString(R.string.user_info_type_update).equals(mActivityType)) {
                            Check_updateUserInfo();
                        }
                        break;
                    default:
                        break;
                }
            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "mOnClickListener - " + e);
                e.printStackTrace();
            }
        }
    };

    /**
     * 팀연령 Spinner AlertDialog
     * Created by maloman72 on 2018-11-05
     * */
    private void TeamAge_AlertDialog() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(getString(R.string.user_info_team_age_dialog_title));

        final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.select_dialog_item);

        for(int i = 0; i < getResources().getStringArray(R.array.C001_data).length; i++) {
            mArrayAdapter.add(getResources().getStringArray(R.array.C001_data)[i]);
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
                bt_user_info_age.setText(mArrayAdapter.getItem(i));
                dialogInterface.dismiss();
            }
        });

        mAlertDialogBuilder.show();
    }

    /**
     * 팀레벨 Spinner AlertDialog
     * Created by maloman72 on 2018-11-05
     * */
    private void TeamLevle_AlertDialog() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(getString(R.string.user_info_team_level_dialog_title));

        final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.select_dialog_item);

        for(int i = 0; i < getResources().getStringArray(R.array.C002_data).length; i++) {
            mArrayAdapter.add(getResources().getStringArray(R.array.C002_data)[i]);
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
                bt_user_info_level.setText(mArrayAdapter.getItem(i));
                dialogInterface.dismiss();
            }
        });

        mAlertDialogBuilder.show();
    }

    /**
     * 회원 정보 등록 데이터 체크
     * Created by maloman72 on 2018-11-01
     * */
    private void Check_insertUserInfo() {
        String user_name = et_user_info_user_name.getText().toString();
        String user_telnum = et_user_info_phone_number.getText().toString();
        String team_name = et_user_info_team_name.getText().toString();
//        ArrayList<String> hope_grounds = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.default_team_code)));
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

        if(getString(R.string.user_info_select).equals(bt_user_info_age.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_age) + getString(R.string.user_info_check_select_not));
            return;
        } else {
            team_age_code = mApplicationTM.getKeybyMap(mApplicationTM.getC001(), bt_user_info_age.getText().toString());
        }

        if(hope_ground.size() <= 0) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_hope_field) + getString(R.string.user_info_check_select_not));
            return;
        }

//        if(!bt_user_info_age_10.isSelected() && !bt_user_info_age_20.isSelected() && !bt_user_info_age_30.isSelected() && !bt_user_info_age_40.isSelected() && !bt_user_info_age_50.isSelected()) {
//            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_age) + getString(R.string.user_info_check_select_not));
//            return;
//        } else {
//            if(bt_user_info_age_10.isSelected()) {
//                team_age_code = getResources().getStringArray(R.array.C001_code)[0];
//            } else if(bt_user_info_age_20.isSelected()) {
//                team_age_code = getResources().getStringArray(R.array.C001_code)[1];
//            } else if(bt_user_info_age_30.isSelected()) {
//                team_age_code = getResources().getStringArray(R.array.C001_code)[2];
//            } else if(bt_user_info_age_40.isSelected()) {
//                team_age_code = getResources().getStringArray(R.array.C001_code)[3];
//            } else if(bt_user_info_age_50.isSelected()) {
//                team_age_code = getResources().getStringArray(R.array.C001_code)[4];
//            }
//        }

        if(getString(R.string.user_info_select).equals(bt_user_info_level.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_level) + getString(R.string.user_info_check_select_not));
            return;
        } else {
            team_level_code = mApplicationTM.getKeybyMap(mApplicationTM.getC002(), bt_user_info_level.getText().toString());
        }

//        if(!bt_user_info_level_challenger.isSelected() && !bt_user_info_level_diamond.isSelected() && !bt_user_info_level_platinum.isSelected() && !bt_user_info_level_gold.isSelected() && !bt_user_info_level_silver.isSelected()) {
//            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_level) + getString(R.string.user_info_check_select_not));
//            return;
//        } else {
//            if(bt_user_info_level_challenger.isSelected()) {
//                team_level_code = getResources().getStringArray(R.array.C002_code)[0];
//            } else if(bt_user_info_level_diamond.isSelected()) {
//                team_level_code = getResources().getStringArray(R.array.C002_code)[1];
//            } else if(bt_user_info_level_platinum.isSelected()) {
//                team_level_code = getResources().getStringArray(R.array.C002_code)[2];
//            } else if(bt_user_info_level_gold.isSelected()) {
//                team_level_code = getResources().getStringArray(R.array.C002_code)[3];
//            } else if(bt_user_info_level_silver.isSelected()) {
//                team_level_code = getResources().getStringArray(R.array.C002_code)[4];
//            }
//        }

        mService.insertUserInfo(insertUserInfo_Listener, mApplicationTM.getUserEmail(), user_name, user_telnum, team_name, hope_ground, team_level_code, team_age_code);
    }

    /**
     * 회원 정보 등록 데이터 체크
     * Created by maloman72 on 2018-11-01
     * */
    private void Check_updateUserInfo() {
        String user_name = et_user_info_user_name.getText().toString();
        String user_telnum = et_user_info_phone_number.getText().toString();
        String team_name = et_user_info_team_name.getText().toString();
//        ArrayList<String> hope_grounds = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.default_team_code)));
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

        if(getString(R.string.user_info_select).equals(bt_user_info_age.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_age) + getString(R.string.user_info_check_select_not));
        } else {
            team_age_code = mApplicationTM.getKeybyMap(mApplicationTM.getC001(), bt_user_info_age.getText().toString());
        }

        if(getString(R.string.user_info_select).equals(bt_user_info_level.getText().toString())) {
            mApplicationTM.makeToast(mContext, getString(R.string.user_info_team_level) + getString(R.string.user_info_check_select_not));
        } else {
            team_level_code = mApplicationTM.getKeybyMap(mApplicationTM.getC002(), bt_user_info_level.getText().toString());
        }

        mService.updateUserInfo(insertUserInfo_Listener, mApplicationTM.getUserEmail(), user_name, user_telnum, search_user_team_id, team_name, hope_ground, team_level_code, team_age_code);
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

                    search_user_team_id = mResult.get(mContext.getString(R.string.searchuserinfo_result_team_id)).toString();

                    et_user_info_user_name.setText(mResult.get(mContext.getString(R.string.searchuserinfo_result_user_name)).toString());
                    et_user_info_phone_number.setText(mResult.get(mContext.getString(R.string.searchuserinfo_result_user_telnum)).toString());
                    et_user_info_team_name.setText(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_name)).toString());

                    mApplicationTM.setTeamAge(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).toString());
                    if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).equals(getResources().getStringArray(R.array.C001_code)[0])) {
                        bt_user_info_age.setText(getResources().getStringArray(R.array.C001_data)[0]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).equals(getResources().getStringArray(R.array.C001_code)[1])) {
                        bt_user_info_age.setText(getResources().getStringArray(R.array.C001_data)[1]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).equals(getResources().getStringArray(R.array.C001_code)[2])) {
                        bt_user_info_age.setText(getResources().getStringArray(R.array.C001_data)[2]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).equals(getResources().getStringArray(R.array.C001_code)[3])) {
                        bt_user_info_age.setText(getResources().getStringArray(R.array.C001_data)[3]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_age_code)).equals(getResources().getStringArray(R.array.C001_code)[4])) {
                        bt_user_info_age.setText(getResources().getStringArray(R.array.C001_data)[4]);
                    }

                    mApplicationTM.setTeamAge(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).toString());
                    if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).equals(getResources().getStringArray(R.array.C002_code)[0])) {
                        bt_user_info_level.setText(getResources().getStringArray(R.array.C002_data)[0]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).equals(getResources().getStringArray(R.array.C002_code)[1])) {
                        bt_user_info_level.setText(getResources().getStringArray(R.array.C002_data)[1]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).equals(getResources().getStringArray(R.array.C002_code)[2])) {
                        bt_user_info_level.setText(getResources().getStringArray(R.array.C002_data)[2]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).equals(getResources().getStringArray(R.array.C002_code)[3])) {
                        bt_user_info_level.setText(getResources().getStringArray(R.array.C002_data)[3]);
                    } else if(mResult.get(mContext.getString(R.string.searchuserinfo_result_team_level_code)).equals(getResources().getStringArray(R.array.C002_code)[4])) {
                        bt_user_info_level.setText(getResources().getStringArray(R.array.C002_data)[4]);
                    }

                    mApplicationTM.setHopeGrounds(mResult.getJSONArray(mContext.getString(R.string.searchuserinfo_result_hope_grounds)));

                    JSONArray hope_grounds = mResult.getJSONArray(mContext.getString(R.string.searchuserinfo_result_hope_grounds));

                    hope_ground = new ArrayList<>();
                    hope_ground_name = new ArrayList<>();

                    for(int i = 0; i < hope_grounds.length() ; i++) {
                        hope_ground.add(((JSONObject)hope_grounds.get(i)).getString("ground_id"));
                        hope_ground_name.add(((JSONObject)hope_grounds.get(i)).getString("ground_name"));
                    }

                    if (hope_ground_name.size() > 0) {

                        if(hope_ground_name.size() >= 1) {
                            bt_user_info_hope_grounds_1.setText(hope_ground_name.get(0));
                            bt_user_info_hope_grounds_1.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_1.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 2) {
                            bt_user_info_hope_grounds_2.setText(hope_ground_name.get(1));
                            bt_user_info_hope_grounds_2.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_2.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 3) {
                            bt_user_info_hope_grounds_3.setText(hope_ground_name.get(2));
                            bt_user_info_hope_grounds_3.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_3.setVisibility(View.GONE);
                        }

                        if(hope_ground_name.size() >= 4) {
                            bt_user_info_hope_grounds_4.setText(hope_ground_name.get(3));
                            bt_user_info_hope_grounds_4.setVisibility(View.VISIBLE);
                        }else {
                            bt_user_info_hope_grounds_4.setVisibility(View.GONE);
                        }

                    }else {
                        bt_user_info_hope_grounds_1.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_2.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_3.setVisibility(View.GONE);
                        bt_user_info_hope_grounds_4.setVisibility(View.GONE);
                    }
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

    public void removeSelectedHopeGround(int index) throws Exception {
        hope_ground.remove(index);
        hope_ground_name.remove(index);

        if (hope_ground_name.size() >= 1) {
            bt_user_info_hope_grounds_1.setText(hope_ground_name.get(0));
            bt_user_info_hope_grounds_1.setVisibility(View.VISIBLE);
        } else {
            bt_user_info_hope_grounds_1.setVisibility(View.GONE);
        }

        if (hope_ground_name.size() >= 2) {
            bt_user_info_hope_grounds_2.setText(hope_ground_name.get(1));
            bt_user_info_hope_grounds_2.setVisibility(View.VISIBLE);
        } else {
            bt_user_info_hope_grounds_2.setVisibility(View.GONE);
        }

        if (hope_ground_name.size() >= 3) {
            bt_user_info_hope_grounds_3.setText(hope_ground_name.get(2));
            bt_user_info_hope_grounds_3.setVisibility(View.VISIBLE);
        } else {
            bt_user_info_hope_grounds_3.setVisibility(View.GONE);
        }

        if (hope_ground_name.size() >= 4) {
            bt_user_info_hope_grounds_4.setText(hope_ground_name.get(3));
            bt_user_info_hope_grounds_4.setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "111");
            bt_user_info_hope_grounds_4.setVisibility(View.GONE);
        }
    }

}
