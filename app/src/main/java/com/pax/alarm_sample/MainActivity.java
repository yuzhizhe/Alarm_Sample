package com.pax.alarm_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pax.alarm_sample.receiver.AlarmReceiver;
import com.pax.alarm_sample.service.AlarmService;
import com.pax.alarm_sample.utils.AlarmManagerUtil;
import com.pax.alarm_sample.utils.DateTimeUtil;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button startBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlarmService.class);
                // 获取20秒之后的日期时间字符串
                i.putExtra("startTime",
                        DateTimeUtil.getNLaterDateTimeString(Calendar.MINUTE, 3));
                i.putExtra("requestCode", 1001);
                startService(i);
            }
        });

        findViewById(R.id.main_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManagerUtil.cancelAlarmBroadcast(MainActivity.this, 1001,
                        AlarmReceiver.class);
            }
        });
    }


}