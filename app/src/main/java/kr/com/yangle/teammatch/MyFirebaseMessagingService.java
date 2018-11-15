package kr.com.yangle.teammatch;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import kr.com.yangle.teammatch.util.DialogAlertActivity;
import kr.com.yangle.teammatch.util.DialogMatchApplyActivity;
import kr.com.yangle.teammatch.util.DialogMatchSuccessActivity;
import kr.com.yangle.teammatch.util.DialogRatingActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    ApplicationTM mApplicationTM;

    // 메시지 수신
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived");

        Map<String, String> data = remoteMessage.getData();

        Log.e(TAG, data + "");

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), data);
    }

    private void sendNotification(String title, String message, Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        openPushAlert(data);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }

    private void openPushAlert(Map<String, String> data) {
        try {
            mApplicationTM = (ApplicationTM) getApplication();

            String team_id = mApplicationTM.getTeamId();

            String match_alert_type = data.get("match_alert_type");

            if("1".equals(match_alert_type)) {
                String match_id = data.get("match_id");
                String match_apply_id = data.get("match_apply_id");
                String host_team_id = data.get("host_team_id");
                String opponent_id = data.get("opponent_id");
                String opponent_name = data.get("opponent_name");
                String opponent_lvl = data.get("opponent_lvl");
                String opponent_point = data.get("opponent_point");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");

                if (team_id.equals(host_team_id)) {
                    // 닫기, 등록정보 확인
                    Intent mIntent = new Intent(getApplicationContext(), DialogMatchApplyActivity.class);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_title), "매치 신청");
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_contents), "상대편이 매치를 신청하였습니다.\n수락하시겠습니까?");
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_id), match_id);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_apply_id), match_apply_id);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_id), opponent_id);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_name), opponent_name);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_lvl), opponent_lvl);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_point), opponent_point);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_time), hope_match_time);
                    mIntent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_ground), hope_match_ground);
                    startActivity(mIntent);
                }
            }else if("2".equals(match_alert_type)) {
                String match_id = data.get("match_id");
                String host_team_id = data.get("host_team_id");
                String host_team_name = data.get("host_team_name");
                String host_team_lvl = data.get("host_team_lvl");
                String host_team_point = data.get("host_team_point");
                String host_team_user_name = data.get("host_team_user_name");
                String host_team_user_tel = data.get("host_team_user_tel");
                String guest_team_id = data.get("guest_team_id");
                String guest_team_name = data.get("guest_team_name");
                String guest_team_lvl = data.get("guest_team_lvl");
                String guest_team_point = data.get("guest_team_point");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");
                String hope_match_ground_tel = data.get("hope_match_ground_tel");
                String hope_match_ground_cost = data.get("hope_match_ground_cost");

                if(team_id.equals(host_team_id)) {
                    Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                    mIntent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                    mIntent.putExtra("SUB_TITLE", "매치 전에 구장에 연락해 구장이용료를\n결제하시길 바랍니다.");
                    mIntent.putExtra("SUB_TITLE_ETC", "(선 결제의 경우에는 동의 및 확인만 눌러주시면 됩니다.)");
                    mIntent.putExtra("GROUND_NAME", hope_match_ground);
                    mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                    mIntent.putExtra("NOTICE", "경기 시작 전/후 상대팀에게 구장이용료의\n절반을 받으시길 바랍니다.");
                    startActivity(mIntent);
                }else if(team_id.equals(guest_team_id)){
                    Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                    mIntent.putExtra(getString(R.string.match_success_extra_type), "GUEST");
                    mIntent.putExtra("SUB_TITLE", "매치가 성사 되었습니다.\n상대방이 결제중입니다.");
                    mIntent.putExtra("TEAM_NAME", host_team_name);
                    mIntent.putExtra("TEAM_LVL", host_team_lvl);
                    mIntent.putExtra("TEAM_POINT", host_team_point);
                    mIntent.putExtra("TEAM_USER_NAME", host_team_user_name);
                    mIntent.putExtra("TEAM_USER_TEL", host_team_user_tel);
                    mIntent.putExtra("GROUND_NAME", hope_match_ground);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                    mIntent.putExtra("NOTICE", "경기 시작 전/후 상대팀에게 구장이용료의\n절반을 정산해 주시길 바랍니다.");
                    startActivity(mIntent);
                }
            }else if("3".equals(match_alert_type)) {
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id)){
                    // 닫기, 등록정보 확인
                    Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                }
            }else if("6".equals(match_alert_type)) {
                String match_id = data.get("match_id");
                String host_team_id = data.get("host_team_id");
                String host_team_name = data.get("host_team_name");
                String host_team_lvl = data.get("host_team_lvl");
                String host_team_point = data.get("host_team_point");
                String guest_team_id = data.get("guest_team_id");
                String guest_team_name = data.get("guest_team_name");
                String guest_team_lvl = data.get("guest_team_lvl");
                String guest_team_point = data.get("guest_team_point");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");

                if(team_id.equals(host_team_id)){
                    Intent mIntent = new Intent(getApplicationContext(), DialogRatingActivity.class);
                    mIntent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                    mIntent.putExtra("MATCH_ID", match_id);
                    mIntent.putExtra("TEAM_ID", guest_team_id);
                    mIntent.putExtra("TEAM_NAME", guest_team_name);
                    mIntent.putExtra("TEAM_LVL", guest_team_lvl);
                    mIntent.putExtra("TEAM_POINT", guest_team_point);
                    mIntent.putExtra("GROUND_NAME", hope_match_ground);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    startActivity(mIntent);
                }else if(team_id.equals(guest_team_id)){
                    Intent mIntent = new Intent(getApplicationContext(), DialogRatingActivity.class);
                    mIntent.putExtra(getString(R.string.match_success_extra_type), "GUEST");
                    mIntent.putExtra("MATCH_ID", match_id);
                    mIntent.putExtra("TEAM_ID", host_team_id);
                    mIntent.putExtra("TEAM_NAME", host_team_name);
                    mIntent.putExtra("TEAM_LVL", host_team_lvl);
                    mIntent.putExtra("TEAM_POINT", host_team_point);
                    mIntent.putExtra("GROUND_NAME", hope_match_ground);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    startActivity(mIntent);
                }
            }
        }catch(Exception e) {
            Log.e(TAG, "openPushAlert - " + e);
        }
    }
}
