package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;
import kr.com.yangle.teammatch.network.ResponseEvent;
import kr.com.yangle.teammatch.network.ResponseListener;
import kr.com.yangle.teammatch.network.Service;

public class DialogRatingActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    TextView tv_rating_team_name, tv_rating_team_lvl, tv_rating_team_member, tv_rating_match_time, tv_rating_match_ground;
    EditText et_rating_score;
    Button bt_rating_complete;

    String mType, match_id, team_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_rating);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        tv_rating_team_name = findViewById(R.id.tv_rating_team_name);
        tv_rating_team_lvl = findViewById(R.id.tv_rating_team_lvl);
        tv_rating_team_member = findViewById(R.id.tv_rating_team_member);
        tv_rating_match_time = findViewById(R.id.tv_rating_match_time);
        tv_rating_match_ground = findViewById(R.id.tv_rating_match_ground);

        et_rating_score = findViewById(R.id.et_rating_score);

        bt_rating_complete = findViewById(R.id.bt_rating_complete);

        bt_rating_complete.setOnClickListener(mOnClickListener);

        setData();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.bt_rating_complete:

                        String rating_str = et_rating_score.getText().toString();

                        if("".equals(rating_str)) {
                            mApplicationTM.makeToast(mContext, "점수를 입력해 주십시요.");
                            return;
                        }

                        int rating = Integer.parseInt(et_rating_score.getText().toString());

                        if (rating < 0 || rating > 100) {
                            mApplicationTM.makeToast(mContext, "점수는 0 ~ 100 점 사이로 입력해 주십시요.");
                            return;
                        }

                        mService.evalMatch(evalMatch_Listener, match_id, team_id, rating + "");
                        finish();
                        break;
                    default:
                        break;
                }
            }catch(Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "mOnClickListener - " + e);
            }
        }
    };

    /**
     * Dialog Data Set
     * Created by maloman72 on 2018-11-14
     * */
    private void setData() {
        mType = getIntent().getStringExtra("TYPE");
        match_id = getIntent().getStringExtra("MATCH_ID");
        team_id = getIntent().getStringExtra("TEAM_ID");
        String team_name = getIntent().getStringExtra("TEAM_NAME");
        String team_lvl = getIntent().getStringExtra("TEAM_LVL");
        String team_point = "".equals(getIntent().getStringExtra("TEAM_POINT"))?"0":getIntent().getStringExtra("TEAM_POINT");
        String ground_name = getIntent().getStringExtra("GROUND_NAME");
        String match_time = getIntent().getStringExtra("MATCH_TIME");

        tv_rating_team_name.setText(team_name);
        tv_rating_team_lvl.setText(team_lvl);
        tv_rating_team_member.setText(team_point + "점");
        tv_rating_match_time.setText(ground_name);
        tv_rating_match_ground.setText(match_time);

    }

    ResponseListener evalMatch_Listener = new ResponseListener() {
        @Override
        public void receive(ResponseEvent responseEvent) {
            try {
                JSONObject mJSONObject = new JSONObject(responseEvent.getResultData());

                if(mContext.getString(R.string.service_sucess).equals(mJSONObject.get(mContext.getString(R.string.result_code)))) {
                    mApplicationTM.makeToast(mContext, "매치 평가가 입력되었습니다.");
                } else {
                    mApplicationTM.makeToast(mContext, mJSONObject.get(mContext.getString(R.string.result_message)).toString());
                }
            } catch (Exception e) {
                mApplicationTM.makeToast(mContext, mContext.getString(R.string.error_network));
                Log.e(TAG, "searchMatchList_Listener - " + e);
            } finally {
                mApplicationTM.stopProgress();
                finish();
            }
        }
    };
}
