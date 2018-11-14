package kr.com.yangle.teammatch.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

public class DialogRatingActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    TextView tv_rating_team_name, tv_rating_team_lvl, tv_rating_team_member, tv_rating_match_time, tv_rating_match_ground;
    EditText et_rating_score;
    Button bt_rating_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_rating);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        tv_rating_team_name = findViewById(R.id.tv_rating_team_name);
        tv_rating_team_lvl = findViewById(R.id.tv_rating_team_lvl);
        tv_rating_team_member = findViewById(R.id.tv_rating_team_member);
        tv_rating_match_time = findViewById(R.id.tv_rating_match_time);
        tv_rating_match_ground = findViewById(R.id.tv_rating_match_ground);

        et_rating_score = findViewById(R.id.et_rating_score);

        bt_rating_complete = findViewById(R.id.bt_rating_complete);

        bt_rating_complete.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_rating_complete :
                    break;
                default :
                    break;
            }
        }
    };
}
