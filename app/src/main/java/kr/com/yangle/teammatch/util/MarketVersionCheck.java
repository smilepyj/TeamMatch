package kr.com.yangle.teammatch.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class MarketVersionCheck {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private ApplicationTM mApplicationTM;

    public MarketVersionCheck(Context context) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        new MVC_AsyncTask().execute();
    }

    public class MVC_AsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String marketVersion = mApplicationTM.getVersion();

            try {
                Document mDocument = Jsoup.connect("https://play.google.com/store/apps/details?id=kr.com.yangle.teammatch").get();

                Elements mVersion = mDocument.select(".htlgb");

                for(int i = 0; i < mVersion.size(); i++) {
                   marketVersion = mVersion.get(i).text();
                   if(Pattern.matches("^[0-9]{1}.[0-99]{1}.[0-999]{1}$", marketVersion))
                       break;
                }
            } catch (Exception e) {
                Log.e(TAG, "MVC_AsyncTask - " + e);
            }


            return marketVersion;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!mApplicationTM.getVersion().equals(result)) {
                String[] mAppVersion = mApplicationTM.getVersion().split("\\.");
                String[] mMarketVersion = result.split("\\.");

                if(mAppVersion.length == 3 && mMarketVersion.length == 3) {
                    if(Integer.valueOf(mAppVersion[0]) < Integer.valueOf(mMarketVersion[0]) || Integer.valueOf(mAppVersion[1]) < Integer.valueOf(mMarketVersion[1])) {
                        UpdateDialog(false);
                    } else if(Integer.valueOf(mAppVersion[2]) < Integer.valueOf(mMarketVersion[2])) {
                        UpdateDialog(true);
                    }
                } else {
                    Log.e(TAG, "MVC_AsyncTask - System Error!!!");
                }
            } else {
                Intent mIntent = new Intent("UpdateCheck");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
            }

            super.onPostExecute(result);
        }
    }

    /**
     * 앱 업데이트 알림
     * checker - true : 일반, false : 강제 업데이트
     * Created by maloman72 on 2019-02-07
     * */
    private void UpdateDialog(boolean checker) {
        final AlertDialog.Builder mBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            mBuilder = new AlertDialog.Builder(mContext);
        }

        mBuilder.setTitle(mContext.getString(R.string.update_title));
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(mContext.getString(R.string.alert_dialog_update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GoMarket();
            }
        });

        if(checker) {
            mBuilder.setMessage(mContext.getString(R.string.update_message));
            mBuilder.setNegativeButton(mContext.getString(R.string.alert_dailog_next), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mIntent = new Intent("UpdateCheck");
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
                }
            });
        } else {
            mBuilder.setMessage(mContext.getString(R.string.compulsion_update_message));
        }

        mBuilder.show();
    }

    private void GoMarket() {
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=kr.com.yangle.teammatch"));
        mContext.startActivity(mIntent);
    }
}
