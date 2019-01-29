package kr.com.yangle.teammatch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.DialogMatchTimePickerActivity;
import kr.com.yangle.teammatch.util.DialogStartTimePickerActivity;
import kr.com.yangle.teammatch.util.DialogAlertActivity;

public class RegistMatchActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    ArrayList<String> regist_ground;
    ArrayList<String> regist_ground_name;
    String regist_date = "-", regist_start_time = "-", regist_end_time = "-", regist_team_member = "-", regist_team_lvl = "-", regist_pre_payment_at = "N";

    LinearLayout ll_regist_match_field, ll_regist_match_day, ll_regist_match_time;

    TextView tv_regist_match_field, tv_regist_match_day, tv_regist_match_time;

    Button bt_regist_match_time_nothing, bt_regist_match_number_nothing, bt_regist_match_number_5, bt_regist_match_number_6, bt_regist_match_level_challenger, bt_regist_match_level_diamond,
            bt_regist_match_level_platinum, bt_regist_match_level_gold, bt_regist_match_level_silver, bt_regist_match_level_nothing, bt_regist_match_insert;

    String intent_regist_area, intent_regist_ground;
    int intent_regist_ground_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_match);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        ll_regist_match_field = findViewById(R.id.ll_regist_match_field);
        tv_regist_match_field = findViewById(R.id.tv_regist_match_field);
        ll_regist_match_day = findViewById(R.id.ll_regist_match_day);
        tv_regist_match_day = findViewById(R.id.tv_regist_match_day);
        ll_regist_match_time = findViewById(R.id.ll_regist_match_time);
        tv_regist_match_time = findViewById(R.id.tv_regist_match_time);

        bt_regist_match_time_nothing = findViewById(R.id.bt_regist_match_time_nothing);
        bt_regist_match_number_nothing = findViewById(R.id.bt_regist_match_number_nothing);
        bt_regist_match_number_5 = findViewById(R.id.bt_regist_match_number_5);
        bt_regist_match_number_6 = findViewById(R.id.bt_regist_match_number_6);
        bt_regist_match_level_challenger = findViewById(R.id.bt_regist_match_level_challenger);
        bt_regist_match_level_diamond = findViewById(R.id.bt_regist_match_level_diamond);
        bt_regist_match_level_platinum = findViewById(R.id.bt_regist_match_level_platinum);
        bt_regist_match_level_gold = findViewById(R.id.bt_regist_match_level_gold);
        bt_regist_match_level_silver = findViewById(R.id.bt_regist_match_level_silver);
        bt_regist_match_level_nothing = findViewById(R.id.bt_regist_match_level_nothing);
        bt_regist_match_insert = findViewById(R.id.bt_regist_match_insert);

        ll_regist_match_field.setOnClickListener(mOnClickListener);
        ll_regist_match_day.setOnClickListener(mOnClickListener);
        ll_regist_match_time.setOnClickListener(mOnClickListener);
        bt_regist_match_time_nothing.setOnClickListener(mOnClickListener);
        bt_regist_match_number_nothing.setOnClickListener(mOnClickListener);
        bt_regist_match_number_5.setOnClickListener(mOnClickListener);
        bt_regist_match_number_6.setOnClickListener(mOnClickListener);
        bt_regist_match_level_challenger.setOnClickListener(mOnClickListener);
        bt_regist_match_level_diamond.setOnClickListener(mOnClickListener);
        bt_regist_match_level_platinum.setOnClickListener(mOnClickListener);
        bt_regist_match_level_gold.setOnClickListener(mOnClickListener);
        bt_regist_match_level_silver.setOnClickListener(mOnClickListener);
        bt_regist_match_level_nothing.setOnClickListener(mOnClickListener);
        bt_regist_match_insert.setOnClickListener(mOnClickListener);


        regist_ground = getIntent().getStringArrayListExtra(getString(R.string.searchmatchlist_param_search_ground));
        regist_ground_name = getIntent().getStringArrayListExtra("search_ground_name");

        if (regist_ground_name != null) {

            String ground_text = "";

            for (int i = 0; i < regist_ground_name.size(); i++) {
                if (i != 0)
                    ground_text += ", ";
                ground_text += regist_ground_name.get(i);
            }

            Log.e(TAG, ground_text);

            tv_regist_match_field.setText(ground_text);
        }

        setLoadTeamLevel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_regist_match_field:
                    Intent mIntent = new Intent(mContext, SearchRegistGroundActivity.class);
                    mIntent.putExtra("callActivityFlag", 2);
                    startActivityForResult(mIntent, 1);
                    break;
                case R.id.ll_regist_match_day:
                    setMatchDayDatePickerDialog();
                    break;
                case R.id.ll_regist_match_time:
                    setMatchTimePickerDialog();
//                    setMatchTimeTimePickerDialog();
                    break;
                case R.id.bt_regist_match_time_nothing:
                    tv_regist_match_time.setText(getString(R.string.regist_match_time_nothing));
                    regist_start_time = "";
                    regist_end_time = "";
                    break;
                case R.id.bt_regist_match_number_nothing:
                    setDefaultButtonMatchNumber();
                    bt_regist_match_number_nothing.setSelected(true);
                    regist_team_member = getResources().getStringArray(R.array.C003_code)[2];
                    break;
                case R.id.bt_regist_match_number_5:
                    setDefaultButtonMatchNumber();
                    bt_regist_match_number_5.setSelected(true);
                    regist_team_member = getResources().getStringArray(R.array.C003_code)[0];
                    break;
                case R.id.bt_regist_match_number_6:
                    setDefaultButtonMatchNumber();
                    bt_regist_match_number_6.setSelected(true);
                    regist_team_member = getResources().getStringArray(R.array.C003_code)[1];
                    break;
                case R.id.bt_regist_match_level_challenger:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_challenger.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[0];
                    break;
                case R.id.bt_regist_match_level_diamond:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_diamond.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[1];
                    break;
                case R.id.bt_regist_match_level_platinum:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_platinum.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[2];
                    break;
                case R.id.bt_regist_match_level_gold:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_gold.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[3];
                    break;
                case R.id.bt_regist_match_level_silver:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_silver.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[4];
                    break;
                case R.id.bt_regist_match_level_nothing:
                    setDefaultButtonMatchLevel();
                    bt_regist_match_level_nothing.setSelected(true);
                    regist_team_lvl = getResources().getStringArray(R.array.C002_code)[5];
                    break;
                case R.id.bt_regist_match_insert:
                    Check_insertMatchList();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    regist_ground = data.getStringArrayListExtra("search_regist_ground");
                    regist_ground_name = data.getStringArrayListExtra("search_regist_ground_name");

                    List<String> regist_ground_texts = new ArrayList<String>();

                    if(regist_ground_name != null) {
                        for(int i = 0; i < regist_ground_name.size(); i++) {
                            regist_ground_texts.add(regist_ground_name.get(i));
                        }
                    }

                    if(regist_ground_texts.size() > 0) {

                        String regist_text = "";

                        for (int i = 0; i < regist_ground_texts.size(); i++) {
                            if (i != 0)
                                regist_text += ", ";
                            regist_text += regist_ground_texts.get(i);
                        }

                        Log.e(TAG, regist_text);

                        tv_regist_match_field.setText(regist_text);
                    }

                    Log.e(TAG, data + "");
                    break;
                case 2 :
                    regist_start_time = data.getStringExtra(getString(R.string.match_time_picker_dialog_start_time));
                    regist_end_time = data.getStringExtra(getString(R.string.match_time_picker_dialog_end_time));
                    regist_pre_payment_at = data.getStringExtra(getString(R.string.match_time_picker_dialog_check_pay));

                    tv_regist_match_time.setText(data.getStringExtra(getString(R.string.match_time_picker_dialog_start_hour)) + getString(R.string.regist_match_time_format_hour) +
                            getString(R.string.regist_match_time_hyphen) + data.getStringExtra(getString(R.string.match_time_picker_dialog_end_hour)) + getString(R.string.regist_match_time_format_hour));
                    break;
                default :
                    break;
            }
        }
    }

    /**
     * 화면 진입 시 팀 레벨 회원 정보 로딩
     * Created by maloman72
     */
    private void setLoadTeamLevel() {
        if (mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[0])) {
            bt_regist_match_level_challenger.setSelected(true);
        } else if (mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[1])) {
            bt_regist_match_level_diamond.setSelected(true);
        } else if (mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[2])) {
            bt_regist_match_level_platinum.setSelected(true);
        } else if (mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[3])) {
            bt_regist_match_level_gold.setSelected(true);
        } else if (mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[4])) {
            bt_regist_match_level_silver.setSelected(true);
        }
        regist_team_lvl = mApplicationTM.getTeamLevel();
    }

    /**
     * 매치 인원 선택 초기화
     * Created by maloman72 on 2018-11-01
     */
    private void setDefaultButtonMatchNumber() {
        bt_regist_match_number_5.setSelected(false);
        bt_regist_match_number_6.setSelected(false);
        bt_regist_match_number_nothing.setSelected(false);
    }

    /**
     * 매치 레벨 선택 초기화
     * Created by maloman72 on 2018-11-01
     */
    private void setDefaultButtonMatchLevel() {
        bt_regist_match_level_challenger.setSelected(false);
        bt_regist_match_level_diamond.setSelected(false);
        bt_regist_match_level_platinum.setSelected(false);
        bt_regist_match_level_gold.setSelected(false);
        bt_regist_match_level_silver.setSelected(false);
        bt_regist_match_level_nothing.setSelected(false);
    }

    /**
     * 날짜 선택 Dialog
     * Created by maloman72 on 2018-11-01
     */
    private void setMatchDayDatePickerDialog() {
        final Calendar mCalendar = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mDateData = String.format(getString(R.string.regist_match_date_format_param), year, String.format("%02d", month + 1), String.format("%02d", dayOfMonth));
                regist_date = mDateData;

                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                String mWeekDay = mSimpleDateFormat.format(mCalendar.getTime());

                tv_regist_match_day.setText(String.format(getString(R.string.regist_match_date_format_view), year, month + 1, dayOfMonth, mWeekDay));

            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));
        mDatePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
        mDatePickerDialog.show();
    }

    /**
     * 시간 선택 Dialog
     * Created by maloman72 on 2018-11-01
     * */
    private void setMatchTimePickerDialog() {

        if("-".equals(regist_date)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_date));
            return;
        }

        Intent mIntent = new Intent(this, DialogMatchTimePickerActivity.class);
        mIntent.putExtra("registDate", regist_date);
        startActivityForResult(mIntent, 2);
    }

//    private void setMatchTimeTimePickerDialog() {
//        final Calendar mCalendar = Calendar.getInstance();
//
//        TimePickerDialog mTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String mTimeData = String.format(getString(R.string.regist_match_time_format_param), String.format("%02d", hourOfDay), String.format("%02d", minute), String.format("%02d", 00));
//                regist_time = mTimeData;
//
//                tv_regist_match_time.setText(String.format(getString(R.string.regist_match_time_format_view), hourOfDay, minute));
//            }
//        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
//
//        mTimePickerDialog.show();
//    }

    /**
     * 매칭 검색 데이터 체크
     * Created by maloman72 on 2018-11-01
     */
    private void Check_insertMatchList() {
        if (regist_ground == null || regist_ground.size() <= 0) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_ground));
            return;
        }

        if ("-".equals(regist_date)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_date));
            return;
        }

        if ("-".equals(regist_start_time)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_start_time));
            return;
        }

        if ("-".equals(regist_end_time)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_end_time));
            return;
        }

        if ("-".equals(regist_team_member)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_member));
            return;
        }

        if ("-".equals(regist_team_lvl)) {
            mApplicationTM.makeToast(mContext, getString(R.string.regist_match_check_level));
            return;
        }

        try {
            mService.registMatch(registMatch_Listener, regist_ground.get(0), regist_date, regist_start_time, regist_end_time, regist_team_member, regist_team_lvl, regist_pre_payment_at);
        }catch(Exception e) {
            mApplicationTM.makeToast(mContext, getString(R.string.error_network));
            Log.e(TAG, "mOnItemClickListener - " + e);
        }
    }

    ResponseListener registMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {

                    // 닫기, 등록정보 확인
                    Intent mIntent = new Intent(mContext, DialogAlertActivity.class);
                    mIntent.putExtra(getString(R.string.alert_dialog_title), "매치 등록 완료");
                    mIntent.putExtra(getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(getString(R.string.alert_dialog_contents), "설정한 조건으로 매치가 등록되었습니다.\n진행 중 매치화면에서 이후로도 등록한 매치 정보를 확인할 수 있습니다.");
                    mIntent.putExtra(getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(getString(R.string.alert_dialog_ok_text), "등록정보 확인");
                    mIntent.putExtra(getString(R.string.alert_dialog_type), 1);

                    startActivity(mIntent);
                    finish();

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
}
