package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class DialogReportActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    EditText et_report_contents;

    Button bt_alert_dialog_cancel, bt_alert_dialog_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        et_report_contents = findViewById(R.id.et_report_contents);

        bt_alert_dialog_cancel = findViewById(R.id.bt_alert_dialog_cancel);
        bt_alert_dialog_ok = findViewById(R.id.bt_alert_dialog_ok);

        bt_alert_dialog_cancel.setOnClickListener(mOnClickListener);
        bt_alert_dialog_ok.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_alert_dialog_cancel :
                    finish();
                    break;
                case R.id.bt_alert_dialog_ok :
                    mService.insertInconventReport(insertInconventReport_Listener, et_report_contents.getText().toString());
                    break;
                default :
                    break;
            }
        }
    };

    ResponseListener insertInconventReport_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    mApplicationTM.makeToast(mContext, getString(R.string.dialog_report_send_success));
                    finish();
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "insertInconventReport_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
