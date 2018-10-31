package kr.com.yangle.teammatch.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

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
    public void userLogin(ResponseListener responseListener, String emailID) {
        try {
            String mURL = mContext.getString(R.string.service_url) + "login/userLogin";

            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("email_id", emailID);

            Offer(mURL, mJSONObject, responseListener);
        } catch (Exception e) {
            Log.e(TAG, "userLogin - " + e);
        }
    }
}
