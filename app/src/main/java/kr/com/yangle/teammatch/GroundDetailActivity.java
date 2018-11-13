package kr.com.yangle.teammatch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class GroundDetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    ImageView iv_ground_detail_photo, iv_ground_detail_inout, iv_ground_detail_park, iv_ground_detail_shower, iv_ground_detail_light, iv_ground_detail_shop, iv_ground_detail_shoes, iv_ground_detail_socks,
            iv_ground_detail_film;
    TextView tv_ground_detail_name, tv_ground_detail_location, tv_ground_detail_operation, tv_ground_detail_inout, tv_ground_detail_park, tv_ground_detail_shower, tv_ground_detail_light, tv_ground_detail_show,
            tv_ground_detail_shoes, tv_ground_detail_socks, tv_ground_detail_film;
    ListView lv_ground_detail;
    Button bt_ground_detail_map, bt_ground_detail_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_detail);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        iv_ground_detail_photo = findViewById(R.id.iv_ground_detail_photo);
        iv_ground_detail_inout = findViewById(R.id.iv_ground_detail_inout);
        iv_ground_detail_park = findViewById(R.id.iv_ground_detail_park);
        iv_ground_detail_shower = findViewById(R.id.iv_ground_detail_shower);
        iv_ground_detail_light = findViewById(R.id.iv_ground_detail_light);
        iv_ground_detail_shop = findViewById(R.id.iv_ground_detail_shop);
        iv_ground_detail_shoes = findViewById(R.id.iv_ground_detail_shoes);
        iv_ground_detail_socks = findViewById(R.id.iv_ground_detail_socks);
        iv_ground_detail_film = findViewById(R.id.iv_ground_detail_film);

        tv_ground_detail_name = findViewById(R.id.tv_ground_detail_name);
        tv_ground_detail_location = findViewById(R.id.tv_ground_detail_location);
        tv_ground_detail_operation = findViewById(R.id.tv_ground_detail_operation);
        tv_ground_detail_inout = findViewById(R.id.tv_ground_detail_inout);
        tv_ground_detail_park = findViewById(R.id.tv_ground_detail_park);
        tv_ground_detail_shower = findViewById(R.id.tv_ground_detail_shower);
        tv_ground_detail_light = findViewById(R.id.tv_ground_detail_light);
        tv_ground_detail_show = findViewById(R.id.tv_ground_detail_show);
        tv_ground_detail_shoes = findViewById(R.id.tv_ground_detail_shoes);
        tv_ground_detail_socks = findViewById(R.id.tv_ground_detail_socks);
        tv_ground_detail_film = findViewById(R.id.tv_ground_detail_film);

        lv_ground_detail = findViewById(R.id.lv_ground_detail);

        bt_ground_detail_map = findViewById(R.id.bt_ground_detail_map);
        bt_ground_detail_call = findViewById(R.id.bt_ground_detail_call);

        bt_ground_detail_map.setOnClickListener(mOnClickListener);
        bt_ground_detail_call.setOnClickListener(mOnClickListener);

        mService.searchGroundDetail(searchGroundDetail_Listener, getIntent().getStringExtra(getString(R.string.ground_detail_ground_id)));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_ground_detail_map :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                case R.id.bt_ground_detail_call :
                    mApplicationTM.makeToast(mContext, getString(R.string.cording_message));
                    break;
                default :
                    break;
            }
        }
    };

    ResponseListener searchGroundDetail_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                Log.e(TAG, mJSONObject.toString());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(mContext.getString(R.string.result_data));
                    JSONObject mResult = mJSONArray.getJSONObject(0);

//                    tv_ground_detail_name.setText(mJSONObject.get(getString(R.string.ground_detail_result_ground_name)).toString());
//                    tv_ground_detail_location.setText(mJSONObject.get(getString(R.string.ground_detail_result_ground_addr)).toString());
//                    tv_ground_detail_operation.setText(mJSONObject.get(getString(R.string.ground_detail_result_open_time)).toString() + getString(R.string.ground_detail_operation_hyphen) + mJSONObject.get(getString(R.string.ground_detail_result_close_time)));
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchGroundDetail_Listener - " + e);
                e.printStackTrace();
            } finally {
                mApplicationTM.stopProgress();
            }
        }
    };
}
