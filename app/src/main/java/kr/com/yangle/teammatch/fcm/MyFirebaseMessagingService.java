package kr.com.yangle.teammatch.fcm;

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

import kr.com.yangle.teammatch.ApplicationTM;
import kr.com.yangle.teammatch.MainActivity;
import kr.com.yangle.teammatch.R;
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

        if(openPushAlert(data)) {
            sendNotification(data);
        }
    }

    private void sendNotification(Map<String, String> data) {
        boolean bNotification = false;

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            mApplicationTM = (ApplicationTM) getApplication();

            String team_id = mApplicationTM.getTeamId();

            String match_alert_type = data.get("match_alert_type");

            intent.putExtra("MATCH_ALERT_TYPE", match_alert_type);

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
                    bNotification = true;

                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_title), "매치 신청");
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_contents), "상대편이 매치를 신청하였습니다.\n수락하시겠습니까?");
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_id), match_id);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_apply_id), match_apply_id);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_id), opponent_id);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_name), opponent_name);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_lvl), opponent_lvl);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_team_point), opponent_point);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_time), hope_match_time);
                    intent.putExtra(getApplicationContext().getString(R.string.match_apply_extra_match_ground), hope_match_ground);
                }
            }else if("2".equals(match_alert_type)) {
                String host_team_id = data.get("host_team_id");
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id) || team_id.equals(host_team_id) ){
                    bNotification = true;

                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 성사");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "매치가 성사 되었습니다.\n구장 이용여부 확인 중입니다.");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                }else {
                    String reject_team_ids = data.get("reject_team_ids");

                    if(!"".equals(reject_team_ids)) {
                        String[] reject_team_list = reject_team_ids.split("\\|");

                        for(String reject_team : reject_team_list) {
                            if(team_id.equals(reject_team)) {
                                bNotification = true;

                                intent.putExtra("REJECT_TEAM_IDS", reject_team_ids);
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                                intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);

                                break;
                            }
                        }
                    }
                }
            }else if("3".equals(match_alert_type)) {
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                }
            }else if("4".equals(match_alert_type)) {
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
                String guest_team_user_name = data.get("guest_team_user_name");
                String guest_team_user_tel = data.get("guest_team_user_tel");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");
                String hope_match_ground_tel = data.get("hope_match_ground_tel");
                String hope_match_ground_cost = data.get("hope_match_ground_cost");

                if(team_id.equals(host_team_id)) {
                    bNotification = true;

                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                    intent.putExtra("SUB_TITLE", getString(R.string.match_success_dialog_sub_title_regist));
                    intent.putExtra("SUB_TITLE_ETC", getString(R.string.match_success_dialog_sub_title_etc_proc));
                    intent.putExtra("MATCH_ID", match_id);
                    intent.putExtra("HOST_TEAM_NAME", host_team_name);
                    intent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    intent.putExtra("HOST_TEAM_POINT", host_team_point);
                    intent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                    intent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                    intent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    intent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    intent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    intent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                    intent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                    intent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                    intent.putExtra("MATCH_TIME", hope_match_time);
                    intent.putExtra("GROUND_TEL", hope_match_ground_tel);
                    intent.putExtra("GROUND_COST", hope_match_ground_cost);
                    intent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_proc));

                }else if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra("SUB_TITLE", getString(R.string.match_success_dialog_sub_title_proc));
                    intent.putExtra("MATCH_ID", match_id);
                    intent.putExtra("HOST_TEAM_NAME", host_team_name);
                    intent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    intent.putExtra("HOST_TEAM_POINT", host_team_point);
                    intent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                    intent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                    intent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    intent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    intent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    intent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                    intent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                    intent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                    intent.putExtra("MATCH_TIME", hope_match_time);
                    intent.putExtra("GROUND_TEL", hope_match_ground_tel);
                    intent.putExtra("GROUND_COST", hope_match_ground_cost);
                    intent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_regist));
                }
            }else if("5".equals(match_alert_type)) {
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 반려");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "해당 구장이 이미 예약되어 있습니다.\n다른 일정 또는 구장으로 매치를 다시 진행하시길 바랍니다.");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    intent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 4);
                }
            }else if("6".equals(match_alert_type)) {
                String match_id = data.get("match_id");
                String host_team_id = data.get("host_team_id");
                String host_team_name = data.get("host_team_name");
                String host_team_lvl = data.get("host_team_lvl");
                String host_team_point = data.get("host_team_point")==null?"0":data.get("host_team_point");
                String guest_team_id = data.get("guest_team_id");
                String guest_team_name = data.get("guest_team_name");
                String guest_team_lvl = data.get("guest_team_lvl");
                String guest_team_point = data.get("guest_team_point")==null?"0":data.get("guest_team_point");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");

                if(team_id.equals(host_team_id)){
                    bNotification = true;

                    intent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                    intent.putExtra("MATCH_ID", match_id);
                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra("HOST_TEAM_NAME", host_team_name);
                    intent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    intent.putExtra("HOST_TEAM_POINT", host_team_point);
                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    intent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    intent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    intent.putExtra("HOPE_MATCH_TIME", hope_match_time);
                    intent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);

                }else if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    intent.putExtra(getString(R.string.match_success_extra_type), "GUEST");
                    intent.putExtra("MATCH_ID", match_id);
                    intent.putExtra("HOST_TEAM_ID", host_team_id);
                    intent.putExtra("HOST_TEAM_NAME", host_team_name);
                    intent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    intent.putExtra("HOST_TEAM_POINT", host_team_point);
                    intent.putExtra("GUEST_TEAM_ID", guest_team_id);
                    intent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    intent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    intent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    intent.putExtra("HOPE_MATCH_TIME", hope_match_time);
                    intent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                }
            }
        }catch(Exception e) {
            bNotification = false;
            Log.e(TAG, "sendNotification - " + e);
        }

        if(bNotification) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    public boolean openPushAlert(Map<String, String> data) {
        boolean bNotification = false;

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            mApplicationTM = (ApplicationTM) getApplication();

            String team_id = mApplicationTM.getTeamId();

            String match_alert_type = data.get("match_alert_type");

            intent.putExtra("MATCH_ALERT_TYPE", match_alert_type);

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
                    bNotification = true;

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
                String host_team_id = data.get("host_team_id");
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id) || team_id.equals(host_team_id) ){
                    bNotification = true;

                    Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 성사");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "매치가 성사 되었습니다.\n구장 이용여부 확인 중입니다.");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                    startActivity(mIntent);

                }else {
                    String reject_team_ids = data.get("reject_team_ids");

                    if(!"".equals(reject_team_ids)) {
                        String[] reject_team_list = reject_team_ids.split("\\|");

                        for(String reject_team : reject_team_list) {
                            if(team_id.equals(reject_team)) {
                                bNotification = true;

                                Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                                mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                                startActivity(mIntent);

                                break;
                            }
                        }
                    }
                }
            }else if("3".equals(match_alert_type)) {
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 거절");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "상대편이 당신의 매치신청을 거절하였습니다.\n다른 매치를 신청해주세요.");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 3);
                    startActivity(mIntent);
                }
            }else if("4".equals(match_alert_type)) {
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
                String guest_team_user_name = data.get("guest_team_user_name");
                String guest_team_user_tel = data.get("guest_team_user_tel");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");
                String hope_match_ground_tel = data.get("hope_match_ground_tel");
                String hope_match_ground_cost = data.get("hope_match_ground_cost");

                if(team_id.equals(host_team_id)) {
                    bNotification = true;

                    Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                    mIntent.putExtra(getString(R.string.match_success_extra_type), "HOST");
                    mIntent.putExtra("SUB_TITLE", getString(R.string.match_success_dialog_sub_title_regist));
                    mIntent.putExtra("SUB_TITLE_ETC", getString(R.string.match_success_dialog_sub_title_etc_proc));
                    mIntent.putExtra("MATCH_ID", match_id);
                    mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                    mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                    mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                    mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                    mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                    mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                    mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                    mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                    mIntent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_proc));
                    startActivity(mIntent);

                }else if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
                    mIntent.putExtra("SUB_TITLE", getString(R.string.match_success_dialog_sub_title_proc));
                    mIntent.putExtra("MATCH_ID", match_id);
                    mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
                    mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
                    mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
                    mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
                    mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
                    mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
                    mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
                    mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
                    mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
                    mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
                    mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
                    mIntent.putExtra("MATCH_TIME", hope_match_time);
                    mIntent.putExtra("GROUND_TEL", hope_match_ground_tel);
                    mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
                    mIntent.putExtra("NOTICE", getString(R.string.match_success_dialog_contents_regist));
                    startActivity(mIntent);
                }
            }else if("5".equals(match_alert_type)) {
                String guest_team_id = data.get("guest_team_id");

                if(team_id.equals(guest_team_id)){
                    bNotification = true;

                    Intent mIntent = new Intent(getApplicationContext(), DialogAlertActivity.class);
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_title), "매치 반려");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents_header), "");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_contents), "해당 구장이 이미 예약되어 있습니다.\n다른 일정 또는 구장으로 매치를 다시 진행하시길 바랍니다.");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_cancel_text), "닫기");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_ok_text), "신청정보 확인");
                    mIntent.putExtra(getApplicationContext().getString(R.string.alert_dialog_type), 4);
                    startActivity(mIntent);
                }
            }else if("6".equals(match_alert_type)) {
                String match_id = data.get("match_id");
                String host_team_id = data.get("host_team_id");
                String host_team_name = data.get("host_team_name");
                String host_team_lvl = data.get("host_team_lvl");
                String host_team_point = data.get("host_team_point")==null?"0":data.get("host_team_point");
                String guest_team_id = data.get("guest_team_id");
                String guest_team_name = data.get("guest_team_name");
                String guest_team_lvl = data.get("guest_team_lvl");
                String guest_team_point = data.get("guest_team_point")==null?"0":data.get("guest_team_point");
                String hope_match_time = data.get("hope_match_time");
                String hope_match_ground = data.get("hope_match_ground");

                if(team_id.equals(host_team_id)){
                    bNotification = true;

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
                    bNotification = true;

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
            bNotification = false;
            Log.e(TAG, "sendNotification - " + e);
        }

        return bNotification;
    }
}
