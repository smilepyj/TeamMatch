package kr.com.yangle.teammatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplicationTM extends Application {
    private final String TAG = this.getClass().getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    Context mContext;

    Map<String, String> C001, C002, C003, C004;

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences(getString(R.string.application), Service.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        mContext = this;
        setMapbyCode();
    }

    /**
     * Code 정보 초기화
     * Created by maloman72 on 2018-11-01
     * */
    private void setMapbyCode() {
        String[] C001_code = getResources().getStringArray(R.array.C001_code);
        String[] C001_data = getResources().getStringArray(R.array.C001_data);

        for(int i = 0; i < C001_code.length; i++) {
            C001.put(C001_code[i], C001_data[i]);
        }

        String[] C002_code = getResources().getStringArray(R.array.C002_code);
        String[] C002_data = getResources().getStringArray(R.array.C002_data);

        for(int i = 0; i < C002_code.length; i++) {
            C002.put(C002_code[i], C002_data[i]);
        }

        String[] C003_code = getResources().getStringArray(R.array.C003_code);
        String[] C003_data = getResources().getStringArray(R.array.C003_data);

        for(int i = 0; i < C003_code.length; i++) {
            C003.put(C003_code[i], C003_data[i]);
        }

        String[] C004_code = getResources().getStringArray(R.array.C004_code);
        String[] C004_data = getResources().getStringArray(R.array.C004_data);

        for(int i = 0; i < C004_code.length; i++) {
            C004.put(C004_code[i], C004_data[i]);
        }
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
     * get Code Map
     * Created by maloman72 on 2018-11-02
     * */
    public Map<String, String> getC001() {
        return C001;
    }

    public Map<String, String> getC002() {
        return C002;
    }

    public Map<String, String> getC003() {
        return C003;
    }

    public Map<String, String> getC004() {
        return C004;
    }

    /**
     * Data 선언 - User Email
     * Created by maloman72 on 2018-10-31
     * */
    public void setUserEmail(String email) {
        mEditor.putString("USER_EMAIL", email);
        mEditor.apply();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString("USER_EMAIL", "maloman72@winitech.com");
    }

    /**
     * Data 선언 - User Hope Grounds
     * Created by maloman72 on 2018-11-01
     * */
    public void setHopeGrounds(JSONArray jsonArray) {
        mEditor.putString("HOPE_GROUNDS", jsonArray.toString());
        mEditor.apply();
    }

    public Map<String, String> getHopeGrounds() {
        Map<String, String> mMap = new HashMap<>();

        try {
            JSONArray mJSONArray = new JSONArray(mSharedPreferences.getString("HOPE_GROUNDS", ""));

            for(int i = 0; i < mJSONArray.length(); i++) {
                JSONObject mJSONobject = mJSONArray.getJSONObject(i);
                String mKey = mJSONobject.names().getString(0);
                mMap.put(mKey, mJSONobject.getString(mKey));
            }
        } catch (Exception e) {
            Log.e(TAG, "getHopeGrounds - " + e);
        }

        return mMap;
    }

    /**
     * Data 선언 - User Team Age
     * Createc by maloman72 on 2018-11-01
     * */
    public void setTeamAge(String teamAge) {
        mEditor.putString("TEAM_AGE", teamAge);
        mEditor.apply();
    }

    public String getTeamAge() {
        return mSharedPreferences.getString("TEAM_AGE", "");
    }

    /**
     * Data 선언 - User Team Level
     * Created by maloman72 on 2018-11-01
     * */
    public void setTeamLevel(String teamLevel) {
        mEditor.putString("TEAM_LEVEL", teamLevel);
        mEditor.apply();
    }

    public String getTeamLevel() {
        return mSharedPreferences.getString("TEAM_LEVEL", "");
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

/**
 * Alert Dialogs
 * Created by maloman72 on 2017-02-17
 * */

    /** Common */
    public void commonAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder mBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            mBuilder = new AlertDialog.Builder(context);
        }

        mBuilder.setTitle(title);
        mBuilder.setMessage(message);
        mBuilder.setCancelable(false);
        mBuilder.setNegativeButton(context.getString(R.string.alert_dialog_close), null);
        mBuilder.show();
    }

    /** UserInfoActivity_Input_Exit */
    public void UserInfoExitDialog(final Activity activity, Context context, String title, String message) {
        AlertDialog.Builder mBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            mBuilder = new AlertDialog.Builder(context);
        }

        mBuilder.setTitle(title);
        mBuilder.setMessage(message);
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(context.getString(R.string.alert_dialog_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        mBuilder.setNegativeButton(context.getString(R.string.alert_dialog_cancel), null);
        mBuilder.show();
    }


    /**
     * ArrayList to String
     * Created by maloman72 on 2018-11-01
     * */
    public String ArrayListToStringParser(ArrayList<String> arrayList) {
        String returnData = "";

        for(int i = 0; i < arrayList.size(); i++) {
            returnData += arrayList.get(i);

            if(i != arrayList.size() - 1) {
                returnData += "|";
            }
        }

        return returnData;
    }
}
