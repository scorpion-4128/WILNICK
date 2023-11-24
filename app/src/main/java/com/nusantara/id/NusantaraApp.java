package com.nusantara.id;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.nusantara.id.preference.SettingsPreference;

import com.nusantara.xyz.logger.SkStatus;
import android.content.Context;
import android.content.pm.PackageInfo;
import com.nusantara.id.util.Utils;
import android.os.Build;
import android.content.pm.PackageManager;
import com.nusantara.xyz.NusantaraCore;
import com.nusantara.xyz.config.Settings;
import android.util.Log;
import com.google.android.gms.ads.MobileAds;
import android.content.res.Configuration;
import com.nusantara.id.preference.LocaleHelper;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.Toast;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.widget.RelativeLayout;
import android.graphics.Color;
import android.view.ViewGroup;
import android.graphics.drawable.GradientDrawable.Orientation;
import com.nusantara.id.R;

/**
* App
*/
public class NusantaraApp extends Application
{
	private static final String TAG = NusantaraApp.class.getSimpleName();
	public static final String PREFS_GERAL = "SocksHttpGERAL";
	
	public static final String ADS_UNITID_INTERSTITIAL_MAIN = "ca-app-pub-9870690854198040/5887367482";
	public static final String ADS_UNITID_BANNER_MAIN = "ca-app-pub-9870690854198040/5887367482";
	public static final String ADS_UNITID_BANNER_SOBRE = "ca-app-pub-9870690854198040/5887367482";
	public static final String ADS_UNITID_BANNER_TEST = "ca-app-pub-9870690854198040/5887367482";
	public static final String APP_FLURRY_KEY = "RQQ8J9Q2N4RH827G32X9";
	
	private static NusantaraApp mApp;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Utils.overrideFont(getApplicationContext(), "SERIF", "font.ttf");
		mApp = this;
		
		// captura dados para análise
		/*new FlurryAgent.Builder()
			.withCaptureUncaughtExceptions(true)
            .withIncludeBackgroundSessionsInMetrics(true)
            .withLogLevel(Log.VERBOSE)
            .withPerformanceMetrics(FlurryPerformance.ALL)
			.build(this, APP_FLURRY_KEY);*/
			
		// inicia
		NusantaraCore.init(this);
		
		// protege o app
		
		
		// Initialize the Mobile Ads SDK.
        MobileAds.initialize(this);
		
		
	
	}
	
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//LocaleHelper.setLocale(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//LocaleHelper.setLocale(this);
	}
	
	
	
	
	public static NusantaraApp getApp() {
		return mApp;
	}
	
	public static void toast(Context contxt, String string, int gravity){
		LayoutInflater inflater = (LayoutInflater) contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.toast, (ViewGroup) null );
        LinearLayout ll1 = new LinearLayout(contxt);
		Toast llIl = Toast.makeText(contxt,Html.fromHtml(""),Toast.LENGTH_LONG);
		final TextView text1 = (TextView)inflate.findViewById(R.id.textqt);
        final RelativeLayout toastlayout = (RelativeLayout)inflate.findViewById(R.id.toastlayout);
		GradientDrawable var1 = new GradientDrawable();
		final Animation e = AnimationUtils.loadAnimation(contxt,R.anim.grow);
        var1.setColor(contxt.getResources().getColor(R.color.colorPrimary));
        var1.setCornerRadius((float)50);
        var1.setOrientation(Orientation.RIGHT_LEFT);
        var1.setStroke(2, Color.parseColor("#ffffff"));
		text1.setText(Html.fromHtml(string));
        ll1.setBackgroundDrawable(var1);
        ll1.addView(inflate);
		toastlayout.setAnimation(e);
		llIl.setView(ll1);
		llIl.setGravity(gravity, 0,30);
		llIl.show();
	}
	
}
