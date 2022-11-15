package com.pax.alarm_sample.service;

import android.app.IntentService;
import android.app.AlarmManager;
import android.content.Intent;
import android.util.Log;

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

    public AlarmService() {
        super("AlarmService");
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
    public void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "Alarm Service Destroy");
    }
}
