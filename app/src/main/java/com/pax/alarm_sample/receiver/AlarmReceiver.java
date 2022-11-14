package com.pax.alarm_sample.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
        ToastUtil.showShort(context,
                "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
        Log.d("Alarm", "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
    }

}
