package kr.com.yangle.teammatch.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogStartTimePickerActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    LinearLayout ll_dialog_time_picker_time_up, ll_dialog_time_picker_time_down;
    ImageButton ib_dialog_time_picker_close, ib_dialog_time_picker_time_up, ib_dialog_time_picker_time_down;
    TextView tv_dialog_time_picker_time;
    Button bt_dialog_time_picker_ok;

    int mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_start_time_picker);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        ll_dialog_time_picker_time_up = findViewById(R.id.ll_dialog_time_picker_time_up);
        ll_dialog_time_picker_time_down = findViewById(R.id.ll_dialog_time_picker_time_down);
        ib_dialog_time_picker_close = findViewById(R.id.ib_dialog_time_picker_close);
        ib_dialog_time_picker_time_up = findViewById(R.id.ib_dialog_time_picker_time_up);
        ib_dialog_time_picker_time_down = findViewById(R.id.ib_dialog_time_picker_time_down);
        tv_dialog_time_picker_time = findViewById(R.id.tv_dialog_time_picker_time);
        bt_dialog_time_picker_ok = findViewById(R.id.bt_dialog_time_picker_ok);

        ll_dialog_time_picker_time_up.setOnClickListener(mOnClickListener);
        ll_dialog_time_picker_time_down.setOnClickListener(mOnClickListener);
        ib_dialog_time_picker_close.setOnClickListener(mOnClickListener);
        ib_dialog_time_picker_time_up.setOnClickListener(mOnClickListener);
        ib_dialog_time_picker_time_down.setOnClickListener(mOnClickListener);
        bt_dialog_time_picker_ok.setOnClickListener(mOnClickListener);

        setTime();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_dialog_time_picker_time_up :
                    changeTime(true);
                    break;
                case R.id.ll_dialog_time_picker_time_down :
                    changeTime(false);
                    break;
                case R.id.ib_dialog_time_picker_close :
                    finish();
                    break;
                case R.id.ib_dialog_time_picker_time_up :
                    changeTime(true);
                    break;
                case R.id.ib_dialog_time_picker_time_down :
                    changeTime(false);
                    break;
                case R.id.bt_dialog_time_picker_ok :
                    Close();
                    break;
                default :
                    break;
            }
        }
    };

    /**
     * 초기 현재 시간 세팅
     * Created by maloman72 on 2018-11-11
     * */
    private void setTime() {
        final Calendar mCalendar = Calendar.getInstance();

        mTime = mCalendar.get(Calendar.HOUR_OF_DAY) + 1;
        tv_dialog_time_picker_time.setText(String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_hour_text));
    }

    /**
     * 시간 변경
     * Created by maloman72 on 2018-11-11
     * */
    private void changeTime(Boolean type) {
        if(type) {
            if(mTime < 24) {
                mTime += 1;
                tv_dialog_time_picker_time.setText(String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_hour_text));
            } else {
                mTime = 1;
                tv_dialog_time_picker_time.setText(String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_hour_text));
            }
        } else {
            if(mTime > 1) {
                mTime -= 1;
                tv_dialog_time_picker_time.setText(String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_hour_text));
            } else {
                mTime = 24;
                tv_dialog_time_picker_time.setText(String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_hour_text));
            }
        }
    }

    /**
     * 시간값 전달 후 종료
     * Created by maloman72 on 2018-11-11
     * */
    private void Close() {
        Intent mIntent = new Intent();
        String resultTime;

        mIntent.putExtra(getString(R.string.start_time_picker_dialog_hour), String.valueOf(mTime));

        if(String.valueOf(mTime).length() == 1) {
            resultTime = "0" + String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_base_time);
        } else {
            resultTime = String.valueOf(mTime) + getString(R.string.start_time_picker_dialog_base_time);
        }

        mIntent.putExtra(getString(R.string.start_time_picker_dialog_set_time), resultTime);
        setResult(RESULT_OK, mIntent);
        finish();
    }
}
