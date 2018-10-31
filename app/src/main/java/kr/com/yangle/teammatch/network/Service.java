package kr.com.yangle.teammatch.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

/**
 * Service 실행부
 * Created by maloman72 on 2018-10-31
 */

public class Service {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private ApplicationTM mApplicationTM;

    private DefinitionNetwork mDefinitionNetwork;

    /**
     * 선언부
     * Created by maloman72 on 2018-10-31
     * */
    public Service(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mDefinitionNetwork = new DefinitionNetwork(mContext);
    }

    private void Offer(String serviceURL, JSONObject dataObject, ResponseListener responseListener) {
        try {
            String mData = mDefinitionNetwork.requsetParser(dataObject);

            if(mData != null) {
                mApplicationTM.startProgress(mContext, "");

                mDefinitionNetwork.Networking(serviceURL, mData, responseListener);
            } else {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
            }
        } catch (Exception e) {
            Log.e(TAG, "Offer - " + e);
        }
    }

    /**
     * 로그인 서비스
     * Created by maloman72 on 2018-10-31
     * */
    public void userLogin(ResponseListener responseListener, String email_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "login/userLogin";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "userLogin - " + e);
        }
    }

    public void insertUserInfo(ResponseListener responseListener, String email_id, String user_name, String user_telnum, String team_name, ArrayList<String> hope_grounds, String team_level_code, String team_age_code) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "user/insertUserInfo";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);
            mJSONObject.put("user_name", user_name);
            mJSONObject.put("user_telnum", user_telnum);
            mJSONObject.put("team_name", team_name);
            mJSONObject.put("hope_grounds", mApplicationTM.ArrayListToStringParser(hope_grounds));
            mJSONObject.put("team_level_code", team_level_code);
            mJSONObject.put("team_age_code", team_age_code);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "insertUserInfo - " + e);
        }
    }

    public void searchUserInfo(ResponseListener responseListener, String email_id) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "user/searchUserInfo";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", email_id);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "insertUserInfo - " + e);
        }
    }
}