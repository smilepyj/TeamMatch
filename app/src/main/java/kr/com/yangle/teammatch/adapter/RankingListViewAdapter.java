package kr.com.yangle.teammatch.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;
import kr.com.yangle.teammatch.network.Service;

public class RankingListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray;
    private int mDataJSONArrayCnt;

    private Activity mActivity;
    private Context mContext;
    private ApplicationTM mApplicationTM;

    private Service mService;

    public  RankingListViewAdapter(Activity activity) {
        mActivity = activity;
        mContext = activity;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        mService = new Service(mActivity);
    }

    public void setMDataJSONArray(JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();
    }

    @Override
    public int getCount() {
        return mDataJSONArrayCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int mPosition = position;

        if(convertView == null) {
            if(mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = mLayoutInflater.inflate(R.layout.listview_ranking, parent, false);
        }

        TextView tv_listview_ranking_rank = convertView.findViewById(R.id.tv_listview_ranking_rank);
        TextView tv_listview_ranking_team_name = convertView.findViewById(R.id.tv_listview_ranking_team_name);
        TextView tv_listview_ranking_point = convertView.findViewById(R.id.tv_listview_ranking_point);
        TextView tv_listview_ranking_change = convertView.findViewById(R.id.tv_listview_ranking_change);

        try {
            JSONObject mJSONObject = mDataJSONArray.getJSONObject(position);

            tv_listview_ranking_rank.setText(mJSONObject.get(mContext.getString(R.string.ranking_result_rank_no)).toString());
            tv_listview_ranking_team_name.setText(mJSONObject.get(mContext.getString(R.string.ranking_result_team_name)).toString());
            tv_listview_ranking_point.setText(mJSONObject.get(mContext.getString(R.string.ranking_result_team_point)).toString());

            if(mContext.getString(R.string.ranking_fluctuation_up).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation_code)))) {
                tv_listview_ranking_change.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_ranking_up), null, null, null);
                tv_listview_ranking_change.setCompoundDrawablePadding(mContext.getResources().getDimensionPixelSize(R.dimen.listview_ranking_drawable_left_padding));
                tv_listview_ranking_change.setTextColor(mContext.getResources().getColor(R.color.color_listview_ranking_up));
                tv_listview_ranking_change.setText(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation)).toString());
            } else if(mContext.getString(R.string.ranking_fluctuation_nothing).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation_code)))) {
                tv_listview_ranking_change.setText(mContext.getString(R.string.ranking_fluctuation_nothing_text));
            } else if(mContext.getString(R.string.ranking_fluctuation_down).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation_code)))) {
                tv_listview_ranking_change.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_ranking_dowm), null, null, null);
                tv_listview_ranking_change.setCompoundDrawablePadding(mContext.getResources().getDimensionPixelSize(R.dimen.listview_ranking_drawable_left_padding));
                tv_listview_ranking_change.setTextColor(mContext.getResources().getColor(R.color.color_listview_ranking_down));
                tv_listview_ranking_change.setText(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation)).toString());
            } else if(mContext.getString(R.string.ranking_fluctuation_new).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation_code)))) {
                tv_listview_ranking_change.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_new), null, null, null);
                tv_listview_ranking_change.setGravity(View.TEXT_ALIGNMENT_CENTER);
            }

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }


        return convertView;
    }
}
