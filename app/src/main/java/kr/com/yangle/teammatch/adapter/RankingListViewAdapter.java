package kr.com.yangle.teammatch.adapter;

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

public class RankingListViewAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private LayoutInflater mLayoutInflater = null;
    private JSONArray mDataJSONArray;
    private int mDataJSONArrayCnt;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    public  RankingListViewAdapter(Context context, JSONArray jsonArray) {
        mDataJSONArray = jsonArray;
        mDataJSONArrayCnt = mDataJSONArray.length();

        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();
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

            if(mContext.getString(R.string.ranking_fluctuation_up).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation)))) {
                tv_listview_ranking_change.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_ranking_up), null, null, null);
                tv_listview_ranking_change.setText(" ");
            } else if(mContext.getString(R.string.ranking_fluctuation_nothing).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation)))) {
                tv_listview_ranking_change.setText(mContext.getString(R.string.ranking_fluctuation_nothing_text));
            } else if(mContext.getString(R.string.ranking_fluctuation_down).equals(mJSONObject.get(mContext.getString(R.string.ranking_result_team_rank_fluctuation)))) {
                tv_listview_ranking_change.setCompoundDrawablesRelativeWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_ranking_dowm), null, null, null);
                tv_listview_ranking_change.setText(" ");
            }

        } catch (Exception e) {
            Log.e(TAG, "getView - " + e);
            mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
        }


        return convertView;
    }
}
