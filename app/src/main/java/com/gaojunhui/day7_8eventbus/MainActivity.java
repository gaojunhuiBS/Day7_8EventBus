package com.gaojunhui.day7_8eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private Button bt_post;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册是
        EventBus.getDefault().register(this);
        bt_post= (Button) findViewById(R.id.bt_post);
        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            count++;
                            MyEventMsg msg = new MyEventMsg();
                            msg.setCount(count);
                            EventBus.getDefault().post(msg);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgRecivier(MyEventMsg msg){
        //默认的代表该方法在发送的线程中
        bt_post.setText("接收消息："+msg.getCount());
    }
}
