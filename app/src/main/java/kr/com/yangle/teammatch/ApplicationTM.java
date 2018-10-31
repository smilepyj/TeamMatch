package kr.com.yangle.teammatch;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationTM extends Application {
    private final String TAG = this.getClass().getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(getString(R.string.application), Service.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    /**
     * 어플리케이션 버전 정보 가져오기
     * Created by maloman72 on 2018-10-02
     */
    public String getVersion() {
        String mVersion = "0.0.0";

        try {
            PackageManager mPackageManager = this.getPackageManager();
            mVersion = mPackageManager.getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(TAG, "getVersion" + e);
        }

        return mVersion;
    }

    /**
     * Common Toast Message
     * Created by maloman72 on 2018-10-02
     */
    Toast mToast;

    public void makeToast(Context context, String message) {
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LinearLayout mLinearLayout = (LinearLayout) mToast.getView();
        TextView mTextView = (TextView) mLinearLayout.getChildAt(0);

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);

        mToast.show();
    }

    public void closeToast() {
        mToast.cancel();
    }

    /**
     * Progress Dialog
     * Created by maloman72 on 2018-10-31
     * */
    private ProgressDialog mProgressDialog;

    public void startProgress(Context context, String message) {
        if(context != null && !((Activity)context).isFinishing()) {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }

            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            if("".equals(message)) {
                mProgressDialog.setMessage("잠시만 기다려 주세요...");
            } else {
                mProgressDialog.setMessage(message);
            }

            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void stopProgress() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
