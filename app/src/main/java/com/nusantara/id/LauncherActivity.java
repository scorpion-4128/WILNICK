package com.nusantara.id;

import android.content.Intent;
import android.os.Bundle;

import android.view.animation.AnimationUtils;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.nusantara.id.activities.BaseActivity;

/**
 * @author anuragdhunna
 */
public class LauncherActivity extends BaseActivity
{

	private Handler handler;


	private LinearLayout j;

	private Animation e;

	private Handler tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		j=(LinearLayout)findViewById(R.id.text_view);
		e = AnimationUtils.loadAnimation(this,R.anim.grow);
		tt = new Handler();
		tt.postDelayed(new Runnable(){
				@Override
				public void run(){
					j.setVisibility(View.VISIBLE);
					j.startAnimation(e);
				}
			},2000);
		
		handler = new Handler();
		handler.postDelayed(new Runnable(){
				@Override
				public void run(){
					Intent intent = new Intent(getApplicationContext(), NusantaraActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					finish();
				}
			},3000);
		}
	
}
