package kr.com.yangle.teammatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import kr.com.yangle.teammatch.adapter.GroundDetailAutoScrollAdapter;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;
import kr.com.yangle.teammatch.util.DialogGroundLocationActivity;
import kr.com.yangle.teammatch.util.DialogMatchApplyActivity;

public class GroundDetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Activity mActivity;
    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    LinearLayout ll_ground_detail_no_photo, ll_ground_detail_cost, ll_ground_detail_no_cost;

    ImageView iv_ground_detail_photo, iv_ground_detail_inout, iv_ground_detail_park, iv_ground_detail_shower, iv_ground_detail_light, iv_ground_detail_shop, iv_ground_detail_shoes, iv_ground_detail_socks,
            iv_ground_detail_film;
    TextView tv_ground_detail_name, tv_ground_detail_location, tv_ground_detail_operation, tv_ground_detail_field_count, tv_ground_detail_hour_1, tv_ground_detail_cost_1, tv_ground_detail_hour_2, tv_ground_detail_cost_2, tv_ground_detail_inout,
            tv_ground_detail_park, tv_ground_detail_shower, tv_ground_detail_light, tv_ground_detail_shop, tv_ground_detail_shoes, tv_ground_detail_socks, tv_ground_detail_film;
    Button bt_ground_detail_map, bt_ground_detail_call;

    String mGround_Phone_Num = null;
    String mGround_Name;
    String mGround_Ground_Addr;
    double mGround_Loc_Lat, mGround_Loc_Lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_detail);

        mActivity = this;
        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ll_ground_detail_no_photo = findViewById(R.id.ll_ground_detail_no_photo);
        ll_ground_detail_cost = findViewById(R.id.ll_ground_detail_cost);
        ll_ground_detail_no_cost = findViewById(R.id.ll_ground_detail_no_cost);

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
        tv_ground_detail_field_count = findViewById(R.id.tv_ground_detail_field_count);
        tv_ground_detail_hour_1 = findViewById(R.id.tv_ground_detail_hour_1);
        tv_ground_detail_cost_1 = findViewById(R.id.tv_ground_detail_cost_1);
        tv_ground_detail_hour_2 = findViewById(R.id.tv_ground_detail_hour_2);
        tv_ground_detail_cost_2 = findViewById(R.id.tv_ground_detail_cost_2);
        tv_ground_detail_inout = findViewById(R.id.tv_ground_detail_inout);
        tv_ground_detail_park = findViewById(R.id.tv_ground_detail_park);
        tv_ground_detail_shower = findViewById(R.id.tv_ground_detail_shower);
        tv_ground_detail_light = findViewById(R.id.tv_ground_detail_light);
        tv_ground_detail_shop = findViewById(R.id.tv_ground_detail_shop);
        tv_ground_detail_shoes = findViewById(R.id.tv_ground_detail_shoes);
        tv_ground_detail_socks = findViewById(R.id.tv_ground_detail_socks);
        tv_ground_detail_film = findViewById(R.id.tv_ground_detail_film);

        bt_ground_detail_map = findViewById(R.id.bt_ground_detail_map);
        bt_ground_detail_call = findViewById(R.id.bt_ground_detail_call);

        bt_ground_detail_map.setOnClickListener(mOnClickListener);
        bt_ground_detail_call.setOnClickListener(mOnClickListener);

        mService.searchGroundDetail(searchGroundDetail_Listener, getIntent().getStringExtra(getString(R.string.ground_detail_ground_id)));
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
            Intent mIntent;

            switch (v.getId()) {
                case R.id.bt_ground_detail_map :
                    mIntent = new Intent(mContext, DialogGroundLocationActivity.class);
                    mIntent.putExtra("ground_name", mGround_Name);
                    mIntent.putExtra("ground_loc_lat", mGround_Loc_Lat);
                    mIntent.putExtra("ground_loc_lon", mGround_Loc_Lon);
                    mIntent.putExtra("ground_ground_addr", mGround_Ground_Addr);
                    startActivity(mIntent);

                    /*LocationUtil location = new LocationUtil(mContext);

                    final KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(
                            Location.newBuilder(mGround_Name, mGround_Loc_Lon, mGround_Loc_Lat).build())
                            .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).setStartX(location.getLastLongitude()).setStartY(location.getLastLatitude()).build());
                    KakaoNaviService.getInstance().navigate(mContext, builder.build());*/

                    break;
                case R.id.bt_ground_detail_call :
                    if(mGround_Phone_Num != null) {
                        mIntent = new Intent(Intent.ACTION_DIAL);
                        mIntent.setData(Uri.parse(mApplicationTM.setCallingPhoneNumber(mGround_Phone_Num)));
                        startActivity(mIntent);
                    } else {
                        mApplicationTM.makeToast(mContext, getString(R.string.ground_detail_no_phone_num));
                    }
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

                    mGround_Loc_Lat = mResult.getDouble(getString(R.string.ground_detail_result_ground_loc_lat));
                    mGround_Loc_Lon = mResult.getDouble(getString(R.string.ground_detail_result_ground_loc_lon));

                    mGround_Phone_Num = mResult.get(getString(R.string.ground_detail_result_ground_tel)).toString();
                    mGround_Name = mResult.get(getString(R.string.ground_detail_result_ground_name)).toString();
                    tv_ground_detail_name.setText(mGround_Name);
                    mGround_Ground_Addr = mResult.get(getString(R.string.ground_detail_result_ground_addr)).toString();
                    tv_ground_detail_location.setText(mResult.get(getString(R.string.ground_detail_result_ground_addr)).toString());
                    Date mTime = new SimpleDateFormat(getString(R.string.ground_detail_time_format), Locale.getDefault()).parse(mResult.get(getString(R.string.ground_detail_result_open_time)).toString());
                    String mStartTime = new SimpleDateFormat(getString(R.string.ground_detail_time_view), Locale.getDefault()).format(mTime);
                    mTime = new SimpleDateFormat(getString(R.string.ground_detail_time_format), Locale.getDefault()).parse(mResult.get(getString(R.string.ground_detail_result_close_time)).toString());
                    String mEndTime = new SimpleDateFormat(getString(R.string.ground_detail_time_view), Locale.getDefault()).format(mTime);
                    tv_ground_detail_operation.setText(mStartTime + getString(R.string.ground_detail_operation_hyphen) + mEndTime);
                    tv_ground_detail_field_count.setText("경기장 " + mResult.get(getString(R.string.ground_detail_result_field_count)).toString() + "개");

                    if(getString(R.string.ground_detail_option_out).equals(mResult.get(getString(R.string.ground_detail_result_in_out_gbn)))) {
                        iv_ground_detail_inout.setBackgroundResource(R.drawable.ic_inout_on);
                        tv_ground_detail_inout.setText(getString(R.string.ground_detail_inout_o));
                        tv_ground_detail_inout.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_parking_gbn)))) {
                        iv_ground_detail_park.setBackgroundResource(R.drawable.ic_park_on);
                        tv_ground_detail_park.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_shower_room_gbn)))) {
                        iv_ground_detail_shower.setBackgroundResource(R.drawable.ic_shower_on);
                        tv_ground_detail_shower.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_night_light_gbn)))) {
                        iv_ground_detail_light.setBackgroundResource(R.drawable.ic_light_on);
                        tv_ground_detail_light.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_store_gbn)))) {
                        iv_ground_detail_shop.setBackgroundResource(R.drawable.ic_shop_on);
                        tv_ground_detail_shop.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_shoes_rental_gbn)))) {
                        iv_ground_detail_shoes.setBackgroundResource(R.drawable.ic_shoes_on);
                        tv_ground_detail_shoes.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_socks_cell_gbn)))) {
                        iv_ground_detail_socks.setBackgroundResource(R.drawable.ic_socks_on);
                        tv_ground_detail_socks.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }
                    if(getString(R.string.ground_detail_option_yes).equals(mResult.get(getString(R.string.ground_detail_result_video_record_gbn)))) {
                        iv_ground_detail_film.setBackgroundResource(R.drawable.ic_film_on);
                        tv_ground_detail_film.setTextColor(getResources().getColor(R.color.color_00AE55));
                    }

                    JSONArray ground_cost_info = mResult.getJSONArray(getString(R.string.ground_detail_result_ground_cost_info));

                    if(ground_cost_info.length() > 0) {
                        for(int i = 0; i < ground_cost_info.length(); i++) {
                            JSONObject mCost = ground_cost_info.getJSONObject(i);
                            DecimalFormat mDecimalFormat = new DecimalFormat(getString(R.string.ground_detail_cost_view));

                            if(i == 0) {
                                tv_ground_detail_hour_1.setText(mApplicationTM.getC005().get(mCost.get(getString(R.string.ground_detail_result_ground_cost_gbn))));
                                tv_ground_detail_cost_1.setText(mDecimalFormat.format(Integer.valueOf(mCost.get(getString(R.string.ground_detail_result_ground_cost)).toString())) + getString(R.string.ground_detail_won));
                            } else if(i == 1) {
                                tv_ground_detail_hour_2.setText(mApplicationTM.getC005().get(mCost.get(getString(R.string.ground_detail_result_ground_cost_gbn))));
                                tv_ground_detail_cost_2.setText(mDecimalFormat.format(Integer.valueOf(mCost.get(getString(R.string.ground_detail_result_ground_cost)).toString())) + getString(R.string.ground_detail_won));
                            }
                        }
                    } else {
                     ll_ground_detail_cost.setVisibility(View.GONE);
                     ll_ground_detail_no_cost.setVisibility(View.VISIBLE);
                    }

                    JSONArray ground_image = mResult.getJSONArray(getString(R.string.ground_detail_result_ground_img));

                    if(ground_image.length() > 0) {
                        //setGroundPhoto(ground_image);
                        ArrayList<String> ground_images = new ArrayList<String>();
                        for(int i = 0 ; i < ground_image.length() ; i++) {
                            String ground_image_url = ((JSONObject)ground_image.get(i)).getString(getString(R.string.ground_detail_result_thumb_img_url));
                            ground_images.add(ground_image_url);
                        }

                        AutoScrollViewPager autoScrollViewPager = findViewById(R.id.vp_ground_detail_photo);
                        GroundDetailAutoScrollAdapter scrollAdapter = new GroundDetailAutoScrollAdapter(mContext, ground_images);
                        autoScrollViewPager.setAdapter(scrollAdapter);
                        autoScrollViewPager.setInterval(5000);
                        autoScrollViewPager.setScrollDurationFactor(3);
                        autoScrollViewPager.startAutoScroll();

                    } else {
                        AutoScrollViewPager autoScrollViewPager = findViewById(R.id.vp_ground_detail_photo);
                        autoScrollViewPager.setVisibility(View.GONE);
                        ll_ground_detail_no_photo.setVisibility(View.VISIBLE);
                    }
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchGroundDetail_Listener - " + e);
                e.printStackTrace();
            } finally {
                mApplicationTM.stopCustomProgressDialog();
            }
        }
    };

    /**
     * URL 구장 이미지 세팅
     * Created by maloman72 on 2018-11-14
     * */
    private void setGroundPhoto(JSONArray ground_image) {
        try {
            JSONObject mImage = ground_image.getJSONObject(0);

            new LoadImageAsyncTask(iv_ground_detail_photo, mContext).execute(mImage.get(getString(R.string.ground_detail_result_thumb_img_url)).toString());
        } catch(Exception e) {
            Log.e(TAG, "setGroundPhoto - " + e);
        }
    }

    private class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        Context mContext;
        ApplicationTM mApplicationTM;

        ImageView mImageView;


        public LoadImageAsyncTask(ImageView imageView, Context context) {
            this.mImageView = imageView;
            this.mContext = context;
            this.mApplicationTM = (ApplicationTM) context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mApplicationTM.startCustomProgressDialog(mActivity, "");
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String mURL = urls[0];
            Bitmap mBitmap = null;

            try {
                InputStream mInputStream = new java.net.URL(mURL).openStream();
                mBitmap = BitmapFactory.decodeStream(mInputStream);
            } catch (Exception e) {
                Log.e(TAG, "LoadImageAsyncTask - " + e);
//                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(bitmap == null) {
                iv_ground_detail_photo.setVisibility(View.GONE);
                ll_ground_detail_no_photo.setVisibility(View.VISIBLE);
            }else {
                mImageView.setImageBitmap(bitmap);
            }
            mApplicationTM.stopCustomProgressDialog();
        }
    }
}
