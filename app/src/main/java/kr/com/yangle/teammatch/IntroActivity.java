package kr.com.yangle.teammatch;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    Context mContext;
    ApplicationTM mApplicationTM;

    TextView tv_intro_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        tv_intro_version = findViewById(R.id.tv_intro_version);

        tv_intro_version.setText(mApplicationTM.getVersion());

        Handler mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1500);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Intent mIntent = new Intent(mContext, LoginActivity.class);
            startActivity(mIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    /**
     * Back Button Listener
     * Intro 화면 Back Button 사용 차단
     * Created by maloman72 on 2018-10-05
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
