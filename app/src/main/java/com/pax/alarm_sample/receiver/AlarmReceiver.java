package com.pax.alarm_sample.receiver;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pax.alarm_sample.utils.AlarmManagerUtil;
import com.pax.alarm_sample.utils.DateTimeUtil;
import com.pax.alarm_sample.utils.ToastUtil;

/**
 * @Description:
 * @Author: yangkesheng@paxsz.com
 * @CreateDate: 2022/11/14 18:26
 * @Version: 1.0
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManagerUtil.sendExactAndAllowWhileIdleBroadcast(context, 1001,
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120*1000, AlarmReceiver.class);
        ToastUtil.showShort(context,
                "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
        Log.d("Alarm", "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
    }

}
