package com.pax.alarm_sample.receiver;

/**
 * @Description:
 * @Author: yangkesheng@paxsz.com
 * @CreateDate: 2022/11/14 18:27
 * @Version: 1.0
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.pax.alarm_sample.service.AlarmService;
import com.pax.alarm_sample.utils.DateTimeUtil;
import com.pax.alarm_sample.utils.ToastUtil;

import java.util.Calendar;

/**
 * 开机重新启动服务AlarmService
 *
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    // 模拟的task id
    private static int mTaskId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("定时服务", "开机启动");
        ToastUtil.showShort(context, "定时服务开机启动");
        Intent i = new Intent(context, AlarmService.class);
        // 获取3分钟之后的日期时间字符串
        i.putExtra("startTime",
                DateTimeUtil.getNLaterDateTimeString(Calendar.MINUTE, 3));
        i.putExtra("requestCode", 1001);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        } else {
            context.startService(i);
        }
    }
}
