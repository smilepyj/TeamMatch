package kr.com.yangle.teammatch.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

/**
 * HttpURLConnection 및 통신 모듈 정의
 * Created by maloman72 on 2018-10-31.
 */

public class DefinitionNetwork {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private ApplicationTM mApplicationTM;

    /**
     * 선언부
     * Created by maloman72 on 2018-10-31
     * */
    public DefinitionNetwork(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();
    }

    /**
     * Service AsyncTask 호출부
     * Created by maloman72 on 2018-10-31
     * */
    public void Networking(String serviceURL, String serviceData, ResponseListener responseListener) {
        try {
            NetworkAsyncTask mNetworkAsyncTask = new NetworkAsyncTask(responseListener);
            mNetworkAsyncTask.execute(serviceURL, serviceData);
        } catch (Exception e) {
            Log.e(TAG, "Service - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }
    }

    /**
     * HttpURLConnection AsyncTask 정의
     * Created by maloman72 on 2018-10-31
     * */
    private class NetworkAsyncTask extends AsyncTask<String, Void, ResponseEvent> {
        private ResponseListener mResponseListener;

        public NetworkAsyncTask(ResponseListener responseListener) {
            mResponseListener = responseListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseEvent doInBackground(String... params) {
            String resultData = null, serviceUrl = params[0], serviceData = params[1];
            HttpURLConnection mHttpURLConnection = null;

            try {
                URL mURL = new URL(serviceUrl);
                mHttpURLConnection = (HttpURLConnection)mURL.openConnection();
                mHttpURLConnection.setConnectTimeout(10000);
                mHttpURLConnection.setReadTimeout(10000);
                mHttpURLConnection.setRequestMethod("POST");
                mHttpURLConnection.setDoInput(true);
                mHttpURLConnection.setDoOutput(true);
                mHttpURLConnection.setRequestProperty("Accept", "application/json");
                mHttpURLConnection.setRequestProperty("Content-Type", "application/json");
                mHttpURLConnection.setRequestProperty("Connection", "close");

                OutputStream mOutputStream = mHttpURLConnection.getOutputStream();
                mOutputStream.write(serviceData.getBytes("UTF-8"));
                mOutputStream.flush();

                int reponseCode = mHttpURLConnection.getResponseCode();

                if(reponseCode == HttpURLConnection.HTTP_OK) {
                    InputStream mInputStream = mHttpURLConnection.getInputStream();
                    ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] mByteBuffer = new byte[1024];
                    byte[] mByteData;
                    int mLength;

                    while ((mLength = mInputStream.read(mByteBuffer, 0, mByteBuffer.length)) != -1) {
                        mByteArrayOutputStream.write(mByteBuffer, 0, mLength);
                    }

                    mByteData = mByteArrayOutputStream.toByteArray();

                    resultData = new String(mByteData);
                }
            } catch (Exception e) {
                Log.e(TAG, "ServiceAsyncTask - " + e);
            } finally {
                if(mHttpURLConnection != null) {
                    mHttpURLConnection.disconnect();
                }
            }

            return new ResponseEvent(resultData);
        }

        @Override
        protected void onPostExecute(ResponseEvent responseEvent) {
            mResponseListener.receive(responseEvent);
        }
    }

    /**
     * requset parameter 변환(Map to JSON)
     * Created by maloman72 on 2018-10-31
     * */
    public String requsetParser(JSONObject requestObject) {
        String mString;

        mString = requestObject.toString();

        return mString;
    }

}
