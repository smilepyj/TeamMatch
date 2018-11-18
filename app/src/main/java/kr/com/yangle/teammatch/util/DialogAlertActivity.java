package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.MatchProcActivity;
import kr.com.yangle.teammatch.R;

public class DialogAlertActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    TextView tv_alert_dialog_title, tv_alert_dialog_contents_header, tv_alert_dialog_contents;
    Button bt_alert_dialog_cancel, bt_alert_dialog_ok, bt_alert_dialog_confirm;

    LinearLayout ll_alert_aialog_two_btn, ll_alert_aialog_one_btn;

    int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_alert);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        tv_alert_dialog_title = findViewById(R.id.tv_alert_dialog_title);
        tv_alert_dialog_contents_header = findViewById(R.id.tv_alert_dialog_contents_header);
        tv_alert_dialog_contents = findViewById(R.id.tv_alert_dialog_contents);

        bt_alert_dialog_cancel = findViewById(R.id.bt_alert_dialog_cancel);
        bt_alert_dialog_ok = findViewById(R.id.bt_alert_dialog_ok);
        bt_alert_dialog_confirm = findViewById(R.id.bt_alert_dialog_confirm);

        ll_alert_aialog_two_btn = findViewById(R.id.ll_alert_aialog_two_btn);
        ll_alert_aialog_one_btn = findViewById(R.id.ll_alert_aialog_one_btn);

        bt_alert_dialog_cancel.setOnClickListener(mOnClickListener);
        bt_alert_dialog_ok.setOnClickListener(mOnClickListener);
        bt_alert_dialog_confirm.setOnClickListener(mOnClickListener);

        tv_alert_dialog_title.setText(getIntent().getStringExtra(getString(R.string.alert_dialog_title)));
        tv_alert_dialog_contents_header.setText(getIntent().getStringExtra(getString(R.string.alert_dialog_contents_header)));
        tv_alert_dialog_contents.setText(getIntent().getStringExtra(getString(R.string.alert_dialog_contents)));

        bt_alert_dialog_cancel.setText(getIntent().getStringExtra(getString(R.string.alert_dialog_cancel_text)));
        bt_alert_dialog_ok.setText(getIntent().getStringExtra(getString(R.string.alert_dialog_ok_text)));
        bt_alert_dialog_confirm.setText("확  인");

        mType = getIntent().getIntExtra(getString(R.string.alert_dialog_type), 0);

        if("4".equals(mType)) {
            ll_alert_aialog_two_btn.setVisibility(View.GONE);
            ll_alert_aialog_one_btn.setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.bt_alert_dialog_cancel :
                    finish();
                    break;
                case R.id.bt_alert_dialog_ok :
                    switch (mType) {
                        case 0 :
                            finish();
                            break;
                        case 1 :
                            mIntent = new Intent(mContext, MatchProcActivity.class);
                            startActivity(mIntent);
                            finish();
                            break;
                        case 2 :
                            mIntent = new Intent(mContext, MatchProcActivity.class);
                            startActivity(mIntent);
                            finish();
                            break;
                        case 3 :
                            mIntent = new Intent(mContext, MatchProcActivity.class);
                            startActivity(mIntent);
                            finish();
                            break;
                        default :
                            break;
                    }
                    break;
                case R.id.bt_alert_dialog_confirm :
                    finish();
                    break;
                default :
                    break;
            }
        }
    };
}
