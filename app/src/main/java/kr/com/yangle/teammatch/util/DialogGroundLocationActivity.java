package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.GroundLocationActivity;
import kr.com.yangle.teammatch.MatchProcActivity;
import kr.com.yangle.teammatch.R;

public class DialogGroundLocationActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    LinearLayout ll_alert_ground_location_1, ll_alert_ground_location_2, ll_alert_ground_location_3, ll_alert_ground_location_4;

    String mGround_Name;
    double mGround_Loc_Lat, mGround_Loc_Lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_ground_location);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        ll_alert_ground_location_1 = findViewById(R.id.ll_alert_ground_location_1);
        ll_alert_ground_location_2 = findViewById(R.id.ll_alert_ground_location_2);
        ll_alert_ground_location_3 = findViewById(R.id.ll_alert_ground_location_3);
        ll_alert_ground_location_4 = findViewById(R.id.ll_alert_ground_location_4);

        ll_alert_ground_location_1.setOnClickListener(mOnClickListener);
        ll_alert_ground_location_2.setOnClickListener(mOnClickListener);
        ll_alert_ground_location_3.setOnClickListener(mOnClickListener);
        ll_alert_ground_location_4.setOnClickListener(mOnClickListener);

        mGround_Name = getIntent().getStringExtra("ground_name");
        mGround_Loc_Lat = getIntent().getDoubleExtra("ground_loc_lat", 0.0);
        mGround_Loc_Lon = getIntent().getDoubleExtra("ground_loc_lon", 0.0);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent;

            switch (v.getId()) {
                case R.id.ll_alert_ground_location_1 :
                    mIntent = new Intent(mContext, GroundLocationActivity.class);
                    mIntent.putExtra("ground_name", mGround_Name);
                    mIntent.putExtra("ground_loc_lat", mGround_Loc_Lat);
                    mIntent.putExtra("ground_loc_lon", mGround_Loc_Lon);
                    mContext.startActivity(mIntent);
                    finish();
                    break;
                case R.id.ll_alert_ground_location_2 :
                    LocationUtil location = new LocationUtil(mContext);

                    final KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(
                            Location.newBuilder(mGround_Name, mGround_Loc_Lon, mGround_Loc_Lat).build())
                            .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).setStartX(location.getLastLongitude()).setStartY(location.getLastLatitude()).build());
                    KakaoNaviService.getInstance().navigate(mContext, builder.build());
                    finish();
                    break;
                case R.id.ll_alert_ground_location_3 :
                    finish();
                    break;
                case R.id.ll_alert_ground_location_4 :
                    finish();
                    break;
                default :
                    break;
            }
        }
    };
}
