package kr.com.yangle.teammatch.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class GroundDetailAutoScrollAdapter extends PagerAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private ArrayList<String> data;

    private Context mContext;
    private ApplicationTM mApplicationTM;

    public GroundDetailAutoScrollAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        mApplicationTM = (ApplicationTM) mContext.getApplicationContext();

        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.ground_detail_viewpager, null);
        ImageView image_container =  v.findViewById(R.id.iv_ground_detail_photo);
        Glide.with(mContext).load(data.get(position)).into(image_container);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
