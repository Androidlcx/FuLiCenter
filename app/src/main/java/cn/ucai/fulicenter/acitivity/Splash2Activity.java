package cn.ucai.fulicenter.acitivity;

import android.content.Intent;
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
       new Thread(){
           @Override
           public void run() {
               long start = System.currentTimeMillis();
               //浪费的时间
               long costTime = System.currentTimeMillis() - start;
               //
               if (sleepTime-costTime>0){
                   SystemClock.sleep(sleepTime-costTime);
               }
               //调用utils封装类中的MFGT闪屏实现方法
               MFGT.gotoMainActivity(Splash2Activity.this);
               MFGT.finish(Splash2Activity.this);//结束闪屏
           }
       }.start();
    }
}
