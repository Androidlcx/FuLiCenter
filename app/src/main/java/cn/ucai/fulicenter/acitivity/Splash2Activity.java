package cn.ucai.fulicenter.acitivity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

public class Splash2Activity extends AppCompatActivity {
    //闪屏的时间
 private final long sleepTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }

    @Override
    protected void onStart() {
        super.onStart();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               MFGT.gotoMainActivity(Splash2Activity.this);
               finish();
           }
       },sleepTime);
    }
}
