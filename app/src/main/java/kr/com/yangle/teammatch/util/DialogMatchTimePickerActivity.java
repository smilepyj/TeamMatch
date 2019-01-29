package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogMatchTimePickerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    LinearLayout ll_dialog_match_time_picker_time_up_start, ll_dialog_match_time_picker_time_down_start, ll_dialog_match_time_picker_time_up_end, ll_dialog_match_time_picker_time_down_end;
    ImageButton ib_dialog_time_picker_close, ib_dialog_match_time_picker_time_up_start, ib_dialog_match_time_picker_time_down_start, ib_dialog_match_time_picker_time_up_end, ib_dialog_match_time_picker_time_down_end;
    TextView tv_dialog_match_time_picker_time_start, tv_dialog_match_time_picker_time_end;
    CheckBox cb_dialog_match_time_picker_check_pay;
    Button bt_dialog_match_time_picker_ok;

    int mStartTime, mEndTime, mCurTime;
    String mRegistDate, mCurDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_match_time_picker);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        ll_dialog_match_time_picker_time_up_start = findViewById(R.id.ll_dialog_match_time_picker_time_up_start);
        ll_dialog_match_time_picker_time_down_start = findViewById(R.id.ll_dialog_match_time_picker_time_down_start);
        ll_dialog_match_time_picker_time_up_end = findViewById(R.id.ll_dialog_match_time_picker_time_up_end);
        ll_dialog_match_time_picker_time_down_end = findViewById(R.id.ll_dialog_match_time_picker_time_down_end);

        ib_dialog_time_picker_close = findViewById(R.id.ib_dialog_time_picker_close);
        ib_dialog_match_time_picker_time_up_start = findViewById(R.id.ib_dialog_match_time_picker_time_up_start);
        ib_dialog_match_time_picker_time_down_start = findViewById(R.id.ib_dialog_match_time_picker_time_down_start);
        ib_dialog_match_time_picker_time_up_end = findViewById(R.id.ib_dialog_match_time_picker_time_up_end);
        ib_dialog_match_time_picker_time_down_end = findViewById(R.id.ib_dialog_match_time_picker_time_down_end);

        tv_dialog_match_time_picker_time_start = findViewById(R.id.tv_dialog_match_time_picker_time_start);
        tv_dialog_match_time_picker_time_end = findViewById(R.id.tv_dialog_match_time_picker_time_end);

        cb_dialog_match_time_picker_check_pay = findViewById(R.id.cb_dialog_match_time_picker_check_pay);

        bt_dialog_match_time_picker_ok = findViewById(R.id.bt_dialog_match_time_picker_ok);

        ll_dialog_match_time_picker_time_up_start.setOnClickListener(mOnClickListener);
        ll_dialog_match_time_picker_time_down_start.setOnClickListener(mOnClickListener);
        ll_dialog_match_time_picker_time_up_end.setOnClickListener(mOnClickListener);
        ll_dialog_match_time_picker_time_down_end.setOnClickListener(mOnClickListener);

        ib_dialog_time_picker_close.setOnClickListener(mOnClickListener);
        ib_dialog_match_time_picker_time_up_start.setOnClickListener(mOnClickListener);
        ib_dialog_match_time_picker_time_down_start.setOnClickListener(mOnClickListener);
        ib_dialog_match_time_picker_time_up_end.setOnClickListener(mOnClickListener);
        ib_dialog_match_time_picker_time_down_end.setOnClickListener(mOnClickListener);

        cb_dialog_match_time_picker_check_pay.setOnClickListener(mOnClickListener);

        bt_dialog_match_time_picker_ok.setOnClickListener(mOnClickListener);

        Intent mIntent = getIntent();
        mRegistDate = mIntent.getStringExtra("registDate");

        setTime();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_dialog_match_time_picker_time_up_start :
                    changeStartTime(true);
                    break;
                case R.id.ll_dialog_match_time_picker_time_down_start :
                    changeStartTime(false);
                    break;
                case R.id.ll_dialog_match_time_picker_time_up_end :
                    changeEndTime(true);
                    break;
                case R.id.ll_dialog_match_time_picker_time_down_end :
                    changeEndTime(false);
                    break;
                case R.id.ib_dialog_time_picker_close :
                    finish();
                    break;
                case R.id.ib_dialog_match_time_picker_time_up_start :
                    changeStartTime(true);
                    break;
                case R.id.ib_dialog_match_time_picker_time_down_start :
                    changeStartTime(false);
                    break;
                case R.id.ib_dialog_match_time_picker_time_up_end :
                    changeEndTime(true);
                    break;
                case R.id.ib_dialog_match_time_picker_time_down_end :
                    changeEndTime(false);
                    break;
                case R.id.cb_dialog_match_time_picker_check_pay :
                    break;
                case R.id.bt_dialog_match_time_picker_ok :
                    Close();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 초기 현재 시간 세팅
     * Created by maloman72 on 2018-11-14
     * */
    private void setTime() {
        final Calendar mCalendar = Calendar.getInstance();

        mCurDate = mCalendar.get(Calendar.YEAR) + (mCalendar.get(Calendar.MONTH) + 1) + mCalendar.get(Calendar.DATE) + "";
        mCurTime = mCalendar.get(Calendar.HOUR_OF_DAY);
        mStartTime = mCurTime + 1;
        mEndTime = mStartTime + 1;

        tv_dialog_match_time_picker_time_start.setText(String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_hour_text));
        tv_dialog_match_time_picker_time_end.setText(String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_hour_text));
    }

    /**
     * 시간 변경
     * Created by maloman72 on 2018-11-14
     * */
    private void changeStartTime(Boolean type) {
        if(type) {
            if(mStartTime < 24) {
                mStartTime += 1;
                tv_dialog_match_time_picker_time_start.setText(String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_hour_text));
            } else {
                mStartTime = 1;
                tv_dialog_match_time_picker_time_start.setText(String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_hour_text));
            }
        } else {
            if(mStartTime > 1) {
                mStartTime -= 1;
                tv_dialog_match_time_picker_time_start.setText(String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_hour_text));
            } else {
                mStartTime = 24;
                tv_dialog_match_time_picker_time_start.setText(String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_hour_text));
            }
        }
    }

    private void changeEndTime(Boolean type) {
        if(type) {
            if(mEndTime < 24) {
                mEndTime += 1;
                tv_dialog_match_time_picker_time_end.setText(String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_hour_text));
            } else {
                mEndTime = 1;
                tv_dialog_match_time_picker_time_end.setText(String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_hour_text));
            }
        } else {
            if(mEndTime > 1) {
                mEndTime -= 1;
                tv_dialog_match_time_picker_time_end.setText(String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_hour_text));
            } else {
                mEndTime = 24;
                tv_dialog_match_time_picker_time_end.setText(String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_hour_text));
            }
        }
    }

    /**
     * 시간값 전달 후 종료
     * Created by maloman72 on 2018-11-14
     * */
    private void Close() {
        if(mCurTime + 1 > mStartTime && mRegistDate == mCurDate) {
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_time_picker_dialog_check_time_2));
            return;
        }

        if(mEndTime - mStartTime < 4 && mEndTime - mStartTime > 0) {
            Intent mIntent = new Intent();
            String resultStartTime, resultEndTime;

            mIntent.putExtra(getString(R.string.match_time_picker_dialog_start_hour), String.valueOf(mStartTime));
            mIntent.putExtra(getString(R.string.match_time_picker_dialog_end_hour), String.valueOf(mEndTime));

            if(String.valueOf(mStartTime).length() == 1) {
                resultStartTime = "0" + String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_base_time);
            } else {
                resultStartTime = String.valueOf(mStartTime) + getString(R.string.match_time_picker_dialog_base_time);
            }

            if(String.valueOf(mEndTime).length() == 1) {
                resultEndTime = "0" + String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_base_time);
            } else {
                resultEndTime = String.valueOf(mEndTime) + getString(R.string.match_time_picker_dialog_base_time);
            }

            mIntent.putExtra(getString(R.string.match_time_picker_dialog_start_time), resultStartTime);
            mIntent.putExtra(getString(R.string.match_time_picker_dialog_end_time), resultEndTime);

            if(cb_dialog_match_time_picker_check_pay.isChecked()) {
                mIntent.putExtra(getString(R.string.match_time_picker_dialog_check_pay), getString(R.string.match_time_picker_dialog_check_pay_yes));
            } else {
                mIntent.putExtra(getString(R.string.match_time_picker_dialog_check_pay), getString(R.string.match_time_picker_dialog_check_pay_no));
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_time_picker_dialog_ckeck_pay));
                return;
            }

            setResult(RESULT_OK, mIntent);
            finish();
        } else {
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.match_time_picker_dialog_check_time));
        }

    }
}
