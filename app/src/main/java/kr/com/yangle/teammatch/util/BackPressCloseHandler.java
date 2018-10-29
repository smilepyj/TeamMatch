package kr.com.yangle.teammatch.util;

import android.app.Activity;

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.R;

/**
 * 단말기 Back 버튼 두번 클릭 시 앱 종료
 * Created by maloman72 on 2018-10-05.
 */

public class BackPressCloseHandler {
    private final String TAG = this.getClass().getSimpleName();

    private Activity mActivity;
    private ApplicationTM mApplicationTM;

    private long mBackKeyPressedTime = 0;

    public BackPressCloseHandler(Activity activity) {
        mActivity = activity;
        mApplicationTM = (ApplicationTM) mActivity.getApplication();
    }

    /**
     * 단말기 Back 버튼 2초안에 두번 클릭 시 어플리케이션 종료
     * Created by maloman72 on 2018-10-05.
     * */
    public void onBackPressed() {
        if(System.currentTimeMillis() > mBackKeyPressedTime + 2000) {
            mBackKeyPressedTime = System.currentTimeMillis();
            mApplicationTM.makeToast(mActivity, mActivity.getString(R.string.exit_message ));
            return;
        }

        if(System.currentTimeMillis() <= mBackKeyPressedTime + 2000) {
            mApplicationTM.closeToast();
            mActivity.finishAffinity();
        }
    }
}
