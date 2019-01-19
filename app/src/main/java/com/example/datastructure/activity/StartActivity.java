package com.example.datastructure.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.datastructure.R;
import com.example.datastructure.base.BaseActivity;
import com.example.datastructure.view.SnowView;

/**
 * Describe :
 * Created by Knight on 2019/1/19
 * 点滴之行,看世界
 **/
public class StartActivity extends BaseActivity {

    private Runnable runnableRain;
    private SnowView snowView;

    private View mRootView;

      @Override
      protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_start);
          snowView = (SnowView) findViewById(R.id.snow_view);
          mRootView = findViewById(R.id.root_view);
          startSnow();
      }

    private void startSnow(){
        snowView.setVisibility(View.VISIBLE);
        snowView.startSnow(mRootView.getWidth());

        final Handler handlerRain = new Handler();
        runnableRain = new Runnable() {
            @Override
            public void run() {
                snowView.addSnows(15, mRootView.getWidth());
                handlerRain.postDelayed(runnableRain, 2000);
                if(snowView.getSnowNumber() > 70)
                {
                    handlerRain.removeCallbacks(runnableRain);
                }
            }
        };

        handlerRain.postDelayed(runnableRain, 0);
    }

}
