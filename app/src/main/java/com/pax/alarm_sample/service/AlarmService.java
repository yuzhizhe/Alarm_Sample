package com.pax.alarm_sample.service;

import android.app.IntentService;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.pax.alarm_sample.R;
import com.pax.alarm_sample.receiver.AlarmReceiver;
import com.pax.alarm_sample.utils.AlarmManagerUtil;
import com.pax.alarm_sample.utils.DateTimeUtil;

import java.util.Date;

/**
 * @Description:
 * @Author: yangkesheng@paxsz.com
 * @CreateDate: 2022/11/14 18:24
 * @Version: 1.0
 */
public class AlarmService extends IntentService {
    // 从其他地方通过Intent传递过来的提醒时间
    private String alarmDateTime;
    private MyHandler handler;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new MyHandler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        alarmDateTime = intent.getStringExtra("startTime");
        // taskId用于区分不同的任务
        int taskId = intent.getIntExtra("requestCode", 0);

        Log.d("AlarmService", "executed at " + new Date().toString()
                + " @Thread id：" + Thread.currentThread().getId());

        long alarmDateTimeMillis = DateTimeUtil.stringToMillis(alarmDateTime);

//        AlarmManagerUtil.sendRepeatAlarmBroadcast(this, taskId,
//                AlarmManager.RTC_WAKEUP, alarmDateTimeMillis, 60 * 1000,
//                AlarmReceiver.class);

        AlarmManagerUtil.sendExactAndAllowWhileIdleBroadcast(this, taskId,
                AlarmManager.RTC_WAKEUP, alarmDateTimeMillis, AlarmReceiver.class);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(startId);
                }
            }.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = getString(R.string.app_name);
//         用户可以看到的通知渠道的描述
        String description = getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true); mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Message") .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CHANNEL_ID)
                .build();
        startForeground(1,notification);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopSelf(msg.what);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "Alarm Service Destroy");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }
    }
}
