package com.pax.alarm_sample.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Description:
 * @Author: yangkesheng@paxsz.com
 * @CreateDate: 2022/11/14 17:58
 * @Version: 1.0
 */
public class ToastUtil {
    // 短时间显示Toast信息
    public static void showShort(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    // 长时间显示Toast信息
    public static void showLong(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }
}
