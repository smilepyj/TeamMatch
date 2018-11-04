package kr.com.yangle.teammatch;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class SearchingMatchActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    Toolbar toolbar;

    ArrayList<String> search_ground, search_area, search_team_lvl;
    String search_date = "-", search_time = "-", search_team_member = "-";

    LinearLayout ll_searching_match_field, ll_searching_match_day, ll_searching_match_time;

    TextView tv_searching_match_field, tv_searching_match_day, tv_searching_match_time;

    Button bt_searching_match_time_nothing, bt_searching_match_number_nothing, bt_searching_match_number_5, bt_searching_match_number_6, bt_searching_match_level_challenger, bt_searching_match_level_diamond,
            bt_searching_match_level_platinum, bt_searching_match_level_gold, bt_searching_match_level_silver, bt_searching_match_level_nothing, bt_searching_match_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_match);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        search_team_lvl = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.searching_match_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ll_searching_match_field = findViewById(R.id.ll_searching_match_field);
        tv_searching_match_field = findViewById(R.id.tv_searching_match_field);
        ll_searching_match_day = findViewById(R.id.ll_searching_match_day);
        tv_searching_match_day = findViewById(R.id.tv_searching_match_day);
        ll_searching_match_time = findViewById(R.id.ll_searching_match_time);
        tv_searching_match_time = findViewById(R.id.tv_searching_match_time);

        bt_searching_match_time_nothing = findViewById(R.id.bt_searching_match_time_nothing);
        bt_searching_match_number_nothing = findViewById(R.id.bt_searching_match_number_nothing);
        bt_searching_match_number_5 = findViewById(R.id.bt_searching_match_number_5);
        bt_searching_match_number_6 = findViewById(R.id.bt_searching_match_number_6);
        bt_searching_match_level_challenger = findViewById(R.id.bt_searching_match_level_challenger);
        bt_searching_match_level_diamond = findViewById(R.id.bt_searching_match_level_diamond);
        bt_searching_match_level_platinum = findViewById(R.id.bt_searching_match_level_platinum);
        bt_searching_match_level_gold = findViewById(R.id.bt_searching_match_level_gold);
        bt_searching_match_level_silver = findViewById(R.id.bt_searching_match_level_silver);
        bt_searching_match_level_nothing = findViewById(R.id.bt_searching_match_level_nothing);
        bt_searching_match_search = findViewById(R.id.bt_searching_match_search);

        ll_searching_match_field.setOnClickListener(mOnClickListener);
        ll_searching_match_day.setOnClickListener(mOnClickListener);
        ll_searching_match_time.setOnClickListener(mOnClickListener);
        bt_searching_match_time_nothing.setOnClickListener(mOnClickListener);
        bt_searching_match_number_nothing.setOnClickListener(mOnClickListener);
        bt_searching_match_number_5.setOnClickListener(mOnClickListener);
        bt_searching_match_number_6.setOnClickListener(mOnClickListener);
        bt_searching_match_level_challenger.setOnClickListener(mOnClickListener);
        bt_searching_match_level_diamond.setOnClickListener(mOnClickListener);
        bt_searching_match_level_platinum.setOnClickListener(mOnClickListener);
        bt_searching_match_level_gold.setOnClickListener(mOnClickListener);
        bt_searching_match_level_silver.setOnClickListener(mOnClickListener);
        bt_searching_match_level_nothing.setOnClickListener(mOnClickListener);
        bt_searching_match_search.setOnClickListener(mOnClickListener);

        setLoadTeamLevel();
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
                case R.id.ll_searching_match_field :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message_search_grounds));
                    tv_searching_match_field.setText(getString(R.string.searching_match_hope_grounds_1) + ", " + getString(R.string.searching_match_hope_grounds_2) + ", " + getString(R.string.searching_match_hope_grounds_3) + ", " + getString(R.string.searching_match_hope_grounds_4));
                    search_ground = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.default_team_code)));
                    break;
                case R.id.ll_searching_match_day :
                    setMatchDayDatePickerDialog();
                    break;
                case R.id.ll_searching_match_time :
                    setMatchTimeTimePickerDialog();
                    break;
                case R.id.bt_searching_match_time_nothing :
                    tv_searching_match_time.setText(getString(R.string.searching_match_time_nothing));
                    search_time = "";
                    break;
                case R.id.bt_searching_match_number_nothing :
                    setDefaultButtonMatchNumber();
                    bt_searching_match_number_nothing.setSelected(true);
                    search_team_member = "";
                    break;
                case R.id.bt_searching_match_number_5 :
                    setDefaultButtonMatchNumber();
                    bt_searching_match_number_5.setSelected(true);
                    search_team_member = getResources().getStringArray(R.array.C003_code)[0];
                    break;
                case R.id.bt_searching_match_number_6 :
                    setDefaultButtonMatchNumber();
                    bt_searching_match_number_6.setSelected(true);
                    search_team_member = getResources().getStringArray(R.array.C003_code)[1];
                    break;
                case R.id.bt_searching_match_level_challenger :
                    bt_searching_match_level_nothing.setSelected(false);
                    if(bt_searching_match_level_challenger.isSelected()) {
                        bt_searching_match_level_challenger.setSelected(false);
                        search_team_lvl.remove(getResources().getTextArray(R.array.C002_code)[0].toString());
                    } else {
                        bt_searching_match_level_challenger.setSelected(true);
                        search_team_lvl.add(getResources().getTextArray(R.array.C002_code)[0].toString());
                    }
                    break;
                case R.id.bt_searching_match_level_diamond :
                    bt_searching_match_level_nothing.setSelected(false);
                    if(bt_searching_match_level_diamond.isSelected()) {
                        bt_searching_match_level_diamond.setSelected(false);
                        search_team_lvl.remove(getResources().getTextArray(R.array.C002_code)[1].toString());
                    } else {
                        bt_searching_match_level_diamond.setSelected(true);
                        search_team_lvl.add(getResources().getTextArray(R.array.C002_code)[1].toString());
                    }
                    break;
                case R.id.bt_searching_match_level_platinum :
                    bt_searching_match_level_nothing.setSelected(false);
                    if(bt_searching_match_level_platinum.isSelected()) {
                        bt_searching_match_level_platinum.setSelected(false);
                        search_team_lvl.remove(getResources().getTextArray(R.array.C002_code)[2].toString());
                    } else {
                        bt_searching_match_level_platinum.setSelected(true);
                        search_team_lvl.add(getResources().getTextArray(R.array.C002_code)[2].toString());
                    }
                    break;
                case R.id.bt_searching_match_level_gold :
                    bt_searching_match_level_nothing.setSelected(false);
                    if(bt_searching_match_level_gold.isSelected()) {
                        bt_searching_match_level_gold.setSelected(false);
                        search_team_lvl.remove(getResources().getTextArray(R.array.C002_code)[3].toString());
                    } else {
                        bt_searching_match_level_gold.setSelected(true);
                        search_team_lvl.add(getResources().getTextArray(R.array.C002_code)[3].toString());
                    }
                    break;
                case R.id.bt_searching_match_level_silver :
                    bt_searching_match_level_nothing.setSelected(false);
                    if(bt_searching_match_level_silver.isSelected()) {
                        bt_searching_match_level_silver.setSelected(false);
                        search_team_lvl.remove(getResources().getTextArray(R.array.C002_code)[4].toString());
                    } else {
                        bt_searching_match_level_silver.setSelected(true);
                        search_team_lvl.add(getResources().getTextArray(R.array.C002_code)[4].toString());
                    }
                    break;
                case R.id.bt_searching_match_level_nothing :

                    search_team_lvl.clear();
                    setDefaultButtonMatchLevel();
                    if(bt_searching_match_level_nothing.isSelected()) {
                        bt_searching_match_level_nothing.setSelected(false);
                    } else {
                        bt_searching_match_level_nothing.setSelected(true);
                    }
                    break;
                case R.id.bt_searching_match_search :
                    Check_searchMatchList();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 화면 진입 시 팀 레벨 회원 정보 로딩
     * Created by maloman72
     * */
    private void setLoadTeamLevel() {
        if(mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[0])) {
            bt_searching_match_level_challenger.setSelected(true);
        } else if(mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[1])) {
            bt_searching_match_level_diamond.setSelected(true);
        } else if(mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[2])) {
            bt_searching_match_level_platinum.setSelected(true);
        } else if(mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[3])) {
            bt_searching_match_level_gold.setSelected(true);
        } else if(mApplicationTM.getTeamLevel().equals(getResources().getStringArray(R.array.C002_code)[4])) {
            bt_searching_match_level_silver.setSelected(true);
        }
    }

    /**
     * 매치 인원 선택 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setDefaultButtonMatchNumber() {
        bt_searching_match_number_5.setSelected(false);
        bt_searching_match_number_6.setSelected(false);
        bt_searching_match_number_nothing.setSelected(false);
    }

    /**
     * 매치 레벨 선택 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setDefaultButtonMatchLevel() {
        bt_searching_match_level_challenger.setSelected(false);
        bt_searching_match_level_diamond.setSelected(false);
        bt_searching_match_level_platinum.setSelected(false);
        bt_searching_match_level_gold.setSelected(false);
        bt_searching_match_level_silver.setSelected(false);
//        bt_searching_match_level_nothing.setSelected(false);
    }

    /**
     * 날짜 선택 Dialog
     * Created by maloman72 on 2018-11-01
     * */
    private void setMatchDayDatePickerDialog() {
        final Calendar mCalendar = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mDateData = String.format("%d%d%d", year, month + 1, dayOfMonth);
                search_date = mDateData;

                mCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                String mWeekDay = mSimpleDateFormat.format(mCalendar.getTime());

                tv_searching_match_day.setText(String.format("%d년 %d월 %d일 %s", year, month + 1, dayOfMonth, mWeekDay));

            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));
        mDatePickerDialog.show();
    }

    /**
     * 시간 선택 Dialog
     * Created by maloman72 on 2018-11-01
     * */
    private void setMatchTimeTimePickerDialog() {
        final Calendar mCalendar = Calendar.getInstance();

        TimePickerDialog mTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String mTimeData = String.format("%d%d%d", hourOfDay, minute, 00);
                search_time = mTimeData;

                tv_searching_match_time.setText(String.format("%d시 %d분", hourOfDay, minute));
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);

        mTimePickerDialog.show();
    }

    /**
     * 매칭 검색 데이터 체크
     * Created by maloman72 on 2018-11-01
     * */
    private void Check_searchMatchList() {
        if("-".equals(search_date)) {
            mApplicationTM.makeToast(mContext, getString(R.string.searching_match_check_date));
            return;
        }

        if("-".equals(search_time)) {
            mApplicationTM.makeToast(mContext, getString(R.string.searching_match_check_time));
            return;
        }

        if("-".equals(search_team_member)) {
            mApplicationTM.makeToast(mContext, getString(R.string.searching_match_check_member));
            return;
        }

        if(search_team_lvl == null && search_team_lvl.size() == 0 && !bt_searching_match_level_nothing.isSelected()) {
            mApplicationTM.makeToast(mContext, getString(R.string.searching_match_check_level));
            return;
        }

        Intent mIntent = new Intent(mContext, SearchResutActivity.class);
        mIntent.putExtra("search_date", search_date);
        mIntent.putExtra("search_time", search_time);
//        mIntent.putExtra("search_area", search_area);
        mIntent.putExtra("search_ground", mApplicationTM.ArrayListToStringParser(search_ground));
        mIntent.putExtra("search_ground_cnt", search_ground.size());
        mIntent.putExtra("search_team_member", search_team_member);
        mIntent.putExtra("search_team_lvl", mApplicationTM.ArrayListToStringParser(search_team_lvl));
        mIntent.putExtra("search_team_lvl_cnt", search_team_lvl.size());
        startActivity(mIntent);
    }
}
