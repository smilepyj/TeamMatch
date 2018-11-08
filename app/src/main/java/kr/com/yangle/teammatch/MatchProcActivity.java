package kr.com.yangle.teammatch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.com.yangle.teammatch.adapter.MatchProcListViewAdapter;
import kr.com.yangle.teammatch.adapter.SearchResultListViewAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class MatchProcActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    ListView lv_match_hist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_proc);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.match_proc_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        lv_match_hist = findViewById(R.id.lv_match_hist);

        mService.searchMatchProcList(searchMatchProcList_Listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_search_result_change_condition :
                    finish();
                    break;
                default :
                    break;
            }
        }
    };

    ResponseListener searchMatchProcList_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));

                    MatchProcListViewAdapter mMatchProcListViewAdapter = new MatchProcListViewAdapter(mContext, mJSONArray);
                    lv_match_hist.setAdapter(mMatchProcListViewAdapter);

                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, getString(R.string.error_network));
                Log.e(TAG, "searchMatchProcList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
