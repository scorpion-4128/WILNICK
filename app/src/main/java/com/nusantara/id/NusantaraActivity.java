package com.nusantara.id;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nusantara.id.R;
import com.nusantara.id.NusantaraApp;
import com.nusantara.id.activities.BaseActivity;
import com.nusantara.id.activities.ConfigGeralActivity;
import com.nusantara.id.adapter.LogsAdapter;
import com.nusantara.id.adapter.SpinnerAdapter;
import com.nusantara.id.util.AESCrypt;
import com.nusantara.id.util.ConfigUpdate;
import com.nusantara.id.util.ConfigUtil;
import com.nusantara.id.util.Utils;
import com.nusantara.xyz.LaunchVpn;
import com.nusantara.xyz.NusantaraService;
import com.nusantara.xyz.StatisticGraphData;
import com.nusantara.xyz.StatisticGraphData.DataTransferStats;
import com.nusantara.xyz.config.ConfigParser;
import com.nusantara.xyz.config.Settings;
import com.nusantara.xyz.logger.ConnectionStatus;
import com.nusantara.xyz.logger.SkStatus;
import com.nusantara.xyz.tunnel.TunnelManagerHelper;
import com.nusantara.xyz.tunnel.TunnelUtils;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.Chronometer.OnChronometerTickListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;
import com.nusantara.id.adapter.PromoAdapter;
import android.content.ClipboardManager;
import android.content.ClipData;
import org.json.JSONArray;
import android.widget.CheckBox;
import android.util.TypedValue;
import androidx.preference.PreferenceManager;
import com.nusantara.id.activities.InjectorService;
import com.google.android.material.snackbar.Snackbar;
import android.view.View.OnClickListener;
//import com.nusantara.id.activities.CustomDNS;
import java.util.concurrent.TimeUnit;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.view.LayoutInflater;
import java.security.GeneralSecurityException;

import android.widget.AdapterView;
import android.widget.ImageView;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.StrictMode;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.pm.PackageManager;
import android.app.ProgressDialog;
import androidx.appcompat.app.ActionBarDrawerToggle;
import java.net.Proxy;
import java.net.InetSocketAddress;
import java.io.InputStream;
import net.sourceforge.jsocks.server.ServerAuthenticatorNone;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.pm.PackageInfo;

/**
 * Activity Principal
 * @author SlipkHunter
 */

public class NusantaraActivity extends BaseActivity
	implements OnClickListener, RadioGroup.OnCheckedChangeListener,
				CompoundButton.OnCheckedChangeListener, SkStatus.StateListener
{

    private TextView connectionStatus;

    private LogsAdapter mLogAdapter;

    private RecyclerView logList;

    private ViewPager vp;

    private TabLayout tabs;

	private TextView bytesIn;

	private TextView bytesOut;

	private CountDownTimer countDown;

	private TextView mTextViewCountDown;

	private Button mButtonSet;

	public static SharedPreferences sp;

	private DrawerLayout drawerlayout;
	private RelativeLayout showme;

	private Animation ogag;

	private String versionName;

	private LinearLayout logka;
	
	int l = 0;

    @Override
    public void onCheckedChanged(CompoundButton p1, boolean p2) {
    }

	private static final String TAG = NusantaraActivity.class.getSimpleName();
	private static final String UPDATE_VIEWS = "MainUpdate";
	public static final String OPEN_LOGS = "com.nusantara.id:openLogs";
	private static ArrayList modeList = new ArrayList();
	
	
	private Settings mConfig;
	private Toolbar toolbar_main;
	private Handler mHandler;
	private CountDownTimer mCountDownTimer;
	private boolean mTimerRunning;
	private long mStartTimeInMillis;
	private long mTimeLeftInMillis;
	private long mEndTime;

	private long saved_ads_time;
	private Chronometer chronometer;
	private long pauseOffset;
    private boolean running;
    private TextView onoff;
	private boolean mTimerEnabled;
	
	private LinearLayout mainLayout;
	private LinearLayout loginLayout;
	private LinearLayout proxyInputLayout;
	private TextView proxyText;
	private RadioGroup metodoConexaoRadio;
	private LinearLayout payloadLayout;
	private TextInputEditText payloadEdit;
	private SwitchCompat customPayloadSwitch;
	private Button starterButton;
	
	private ImageButton inputPwShowPass;
	private TextInputEditText inputPwUser;
	private TextInputEditText inputPwPass;
	
	private LinearLayout configMsgLayout;
	private TextView configMsgText;

	private AdView adsBannerView;

	private ConfigUtil config;
	public static int PICK_FILE = 1;
	private Spinner serverSpinner;
	private Spinner payloadSpinner;
    private static final String[] tabTitle = {"Home","Log"};
	private SpinnerAdapter serverAdapter;
	private PromoAdapter payloadAdapter;
	private ArrayList<JSONObject> serverList;
	private ArrayList<JSONObject> payloadList;
	private CheckBox startSSHCheckbox;
	private CheckBox dnsCheckBox;
    public static LogsAdapter mAdapter;
    private FloatingActionButton deleteLogs;
    private TextView app_info_text;

	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		ogag = AnimationUtils.loadAnimation(this,R.anim.grow);
		Thread.setDefaultUncaughtExceptionHandler(new CrashWindow(this));
		mHandler = new Handler();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		updateapp(true, true);
		mConfig = new Settings(this);
		SharedPreferences prefs = getSharedPreferences(NusantaraApp.PREFS_GERAL, Context.MODE_PRIVATE);
		boolean showFirstTime = prefs.getBoolean("connect_first_time", true);
		int lastVersion = prefs.getInt("last_version", 0);
		if (showFirstTime)
        {
            SharedPreferences.Editor pEdit = prefs.edit();
            pEdit.putBoolean("connect_first_time", false);
            pEdit.apply();
			Settings.setDefaultConfig(this);
			showBoasVindas();
        }

		try {
			int idAtual = ConfigParser.getBuildId(this);

			if (lastVersion < idAtual) {
				SharedPreferences.Editor pEdit = prefs.edit();
				pEdit.putInt("last_version", idAtual);
				pEdit.apply();

				if (!showFirstTime) {
					if (lastVersion <= 12) {
						Settings.setDefaultConfig(this);
						Settings.clearSettings(this);

						Toast.makeText(this, "As configurações foram limpas para evitar bugs",
							Toast.LENGTH_LONG).show();
					}
				}

			}
		} catch(IOException e) {}
		doLayout();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_VIEWS);
		filter.addAction(OPEN_LOGS);
		
		LocalBroadcastManager.getInstance(this)
			.registerReceiver(mActivityReceiver, filter);
			
		doUpdateLayout();
	}


	/**
	 * Layout
	 */
	 
	private void doLayout() {
		final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.activity_main_drawer);
		toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
		logka = (LinearLayout)findViewById(R.id.activity_mainLogsDrawerLinear);
		setSupportActionBar(toolbar_main);
		showme = (RelativeLayout) findViewById(R.id.edson2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mAdapter = new LogsAdapter(layoutManager, this);
		deleteLogs = (FloatingActionButton) findViewById(R.id.clearLog);
		logList = (RecyclerView) findViewById(R.id.recyclerLog);
		logList.setAdapter(mAdapter);
		logList.setLayoutManager(layoutManager);
		mAdapter.scrollToLastPosition();
		deleteLogs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				mAdapter.clearLog();
				// SkStatus.logInfo("<font color='green'>Log Cleared!</font>");
				// TODO: Implement this method
			}

		});
		mTextViewCountDown = (TextView)findViewById(R.id.timerTextView);
		mButtonSet = (Button) findViewById(R.id.btnAddTime);
		mButtonSet.setOnClickListener(this);	
		adsBannerView = (AdView) findViewById(R.id.adBannerMainView);
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
		if (TunnelUtils.isNetworkOnline(NusantaraActivity.this)) {
			adsBannerView.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					if (adsBannerView != null) {
						adsBannerView.setVisibility(View.VISIBLE);
					}
				}
			});
			adsBannerView.loadAd(new AdRequest.Builder()
				.build());
		}
		startSSHCheckbox = (CheckBox) findViewById(R.id.startSSHCheckbox);
		startSSHCheckbox.setText("Start SSH");
		startSSHCheckbox.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
		startSSHCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mPref.edit().putBoolean("startSSHCheck",isChecked).apply();
				}
			});
		dnsCheckBox=(CheckBox) findViewById(R.id.useDns);
		dnsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mPref.edit().putBoolean("startDns",isChecked).apply();
				}
			});
		dnsCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
		mainLayout = (LinearLayout) findViewById(R.id.activity_mainLinearLayout);
		loginLayout = (LinearLayout) findViewById(R.id.activity_mainInputPasswordLayout);
		starterButton = (Button) findViewById(R.id.activity_starterButtonMain);
		inputPwUser = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordUserEdit);
		inputPwPass = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordPassEdit);
		inputPwShowPass = (ImageButton) findViewById(R.id.activity_mainInputShowPassImageButton);

		((TextView) findViewById(R.id.activity_mainAutorText))
			.setOnClickListener(this);

		proxyInputLayout = (LinearLayout) findViewById(R.id.activity_mainInputProxyLayout);
		proxyText = (TextView) findViewById(R.id.activity_mainProxyText);
      
        final SharedPreferences prefs = mConfig.getPrefsPrivate();
        SharedPreferences.Editor edit = prefs.edit();
		SharedPreferences sPrefs = mConfig.getPrefsPrivate();
        sPrefs.edit().putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false).apply();
		sPrefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
        config = new ConfigUtil(this);
		serverSpinner = (Spinner) findViewById(R.id.serverSpinner);
		payloadSpinner = (Spinner) findViewById(R.id.payloadSpinner);
        connectionStatus = (TextView) findViewById(R.id.connection_status);
		serverList = new ArrayList<>();
		payloadList = new ArrayList<>();
		chronometer = (Chronometer)findViewById(R.id.chronometer);
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener(){
				@Override
				public void onChronometerTick(Chronometer chronometer) {
					long time = SystemClock.elapsedRealtime() - chronometer.getBase();
					int h   = (int)(time /3600000);
					int m = (int)(time - h*3600000)/60000;
					int s= (int)(time - h*3600000- m*60000)/1000 ;
					String t = (h < 10 ? "0"+h: h)+"h:"+(m < 10 ? "0"+m: m)+"m:"+ (s < 10 ? "0"+s: s)+"s";
					chronometer.setText(t);
				}
			});
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.setText("00h:00m:00s");
		
		serverAdapter = new SpinnerAdapter(this, R.id.serverSpinner, serverList);
		payloadAdapter = new PromoAdapter(this, R.id.payloadSpinner, payloadList);
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
		
        drawerlayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		loadServer();
		loadNetworks();
		updateConfig(true);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout,toolbar_main,R.string.app_name,R.string.app_name);
		toggle.syncState();
		drawerlayout.setDrawerListener(toggle);
		metodoConexaoRadio = (RadioGroup) findViewById(R.id.activity_mainMetodoConexaoRadio);
		customPayloadSwitch = (SwitchCompat) findViewById(R.id.activity_mainCustomPayloadSwitch);

		starterButton.setOnClickListener(this);
		proxyInputLayout.setOnClickListener(this);
		dnsCheckBox.setOnClickListener(this);
        TextView configVer = (TextView) findViewById(R.id.config_v);
		configVer.setText(config.getVersion());
        PackageInfo pinfo = Utils.getAppInfo(this);
		if (pinfo != null) {
			String version_nome = pinfo.versionName;
			int version_code = pinfo.versionCode;
			String header_text = String.format("%s (%d)", version_nome, version_code);
			app_info_text = (TextView) findViewById(R.id.appVersion);
			app_info_text.setText(header_text);
		}
		payloadLayout = (LinearLayout) findViewById(R.id.activity_mainInputPayloadLinearLayout);
		payloadEdit = (TextInputEditText) findViewById(R.id.activity_mainInputPayloadEditText);
		bytesIn = (TextView) findViewById(R.id.bytesIn);
        bytesOut = (TextView) findViewById(R.id.bytesOut);
		configMsgLayout = (LinearLayout) findViewById(R.id.activity_mainMensagemConfigLinearLayout);
		configMsgText = (TextView) findViewById(R.id.activity_mainMensagemConfigTextView);

		// fix bugs
		if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));
			}
		}
		else {
			payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
		}
        customPayloadSwitch.setChecked(true);
        
        edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, !true);
        setPayloadSwitch(prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT), true);
		metodoConexaoRadio.setOnCheckedChangeListener(this);
		inputPwShowPass.setOnClickListener(this);
        
        doTabs();
		
		serverSpinner.setSelection(prefs.getInt("LastSelectedServer", 0));
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

                    SharedPreferences sp = mConfig.getPrefsPrivate();
                    SharedPreferences.Editor edit = sp.edit();
					int pos = serverSpinner.getSelectedItemPosition();
                    edit.putInt("LastSelectedServer", pos).apply();

                }

                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                }
            });

		payloadSpinner.setSelection(prefs.getInt("LastSelectedPayload", -1));
        payloadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {

                    SharedPreferences sp = mConfig.getPrefsPrivate();
                    SharedPreferences.Editor edit = sp.edit();
					int pos = payloadSpinner.getSelectedItemPosition();
                    edit.putInt("LastSelectedPayload", pos).apply();
					// payloadSpinner.getSelectedItemPosition();
                }

                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                }
            });
		serverSpinner.setAdapter(serverAdapter);
		payloadSpinner.setAdapter(payloadAdapter);
		serverSpinner.setAnimation(ogag);
		serverSpinner.setSaveEnabled(true);
		payloadSpinner.setAnimation(ogag);
		payloadSpinner.setSaveEnabled(true);
		//registerForContextMenu(this.payloadSpinner);
	}
    
    private void checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected())
        {
	        toolbar_main.setSubtitle("WIFI: "+TunnelUtils.getLocalIpAddress());
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);

        } else if (mMobile.isConnected()) {
            
			toolbar_main.setSubtitle("MOBILE: "+TunnelUtils.getLocalIpAddress());
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
			
        } else {
			toolbar_main.setSubtitle("NO INTERNET CONNECTION");
			toolbar_main.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText);
        }
	}
	
	private void updateHeaderCallback() {
		DataTransferStats dataTransferStats = StatisticGraphData.getStatisticData().getDataTransferStats();
		bytesIn.setText(dataTransferStats.byteCountToDisplaySize(dataTransferStats.getTotalBytesReceived(), false));
		bytesOut.setText(dataTransferStats.byteCountToDisplaySize(dataTransferStats.getTotalBytesSent(), false));
	}
    
    public void doTabs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogAdapter = new LogsAdapter(layoutManager,this);
        logList = (RecyclerView) findViewById(R.id.recyclerLog);
        logList.setAdapter(mLogAdapter);
        logList.setLayoutManager(layoutManager);
        mLogAdapter.scrollToLastPosition();
        vp = (ViewPager)findViewById(R.id.viewpager);
        tabs = (TabLayout)findViewById(R.id.tablayout);
        vp.setAdapter(new MyAdapter(Arrays.asList(tabTitle)));
        vp.setOffscreenPageLimit(2);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(vp);
		
		vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
            {
                @Override
                public void onPageSelected(int position)
                {
                    if (position == 0) {
                        toolbar_main.getMenu().clear();
						getMenuInflater().inflate(R.menu.main_menu, toolbar_main.getMenu());
                    } else if (position == 1) {
                        toolbar_main.getMenu().clear();
						getMenuInflater().inflate(R.menu.logs_menu, toolbar_main.getMenu());
                    }
                }
			});
        }
        
    public class MyAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            // TODO: Implement this method
            return 2;
        }

        @Override
        public boolean isViewFromObject(View p1, Object p2)
        {
            // TODO: Implement this method
            return p1 == p2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int[] ids = new int[]{R.id.tab1, R.id.tab2};
            int id = 0;
            id = ids[position];
            // TODO: Implement this method
            return findViewById(id);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            // TODO: Implement this method
            return titles.get(position);
        }

        private List<String> titles;
        public MyAdapter(List<String> str)
        {
            titles = str;
        }
	}
       // CULT SETTING ↓ BY: NethPH (Pogi)
	   
	public void cult1(View ambatukam){
		
	try {
		try {
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.setClassName("com.android.phone" , "com.android.phone.settings.RadioInfo");
		startActivity(intent); 
		} catch (Exception e){
			
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.setClassName("com.android.settings" , "com.android.settings.RadioInfo");
			startActivity(intent); 
		}
		} catch (Exception eu){
			
			NusantaraApp.toast(getApplicationContext(), "Not supported!", Gravity.CENTER);
		}
	}
	public void cult2(View ambatukam){
	updateConfig(false);
	}
	public void cult3(View ambatukam){
updateapp(false, false);
	}
	public void cult4(View ambatukam){

		try{ 

			Intent intent = new Intent("android.intent.action.MAIN");
			intent.setClassName(getPackageName() , "egcodes.com.speedtest.MainActivity");
			startActivity(intent); }
		catch(Exception e)
		{ Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show(); 
		}
	}
	public void cult5(View ambatukam){
		startActivity(new Intent(NusantaraActivity.this,ConfigGeralActivity.class));
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
	}
	public void cult6(View ambatukam){
		iphunt();
	}
	
	
	
	
	
	private void doUpdateLayout() {
		SharedPreferences prefs = mConfig.getPrefsPrivate();
		final SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isRunning = SkStatus.isTunnelActive();
		int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
		
		setStarterButton(starterButton, this);
		setPayloadSwitch(tunnelType, !prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true));
		startSSHCheckbox.setEnabled(!isRunning);
		startSSHCheckbox.setChecked(mPref.getBoolean("startSSHCheck",true));
		dnsCheckBox.setChecked(mPref.getBoolean("startDns",true));
		dnsCheckBox.setEnabled(!isRunning);
		String proxyStr = getText(R.string.no_value).toString();

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			proxyStr = "*******";
			proxyInputLayout.setEnabled(false);
		}
		else {
			String proxy = mConfig.getPrivString(Settings.PROXY_IP_KEY);

			if (proxy != null && !proxy.isEmpty())
				proxyStr = String.format("%s:%s", proxy, mConfig.getPrivString(Settings.PROXY_PORTA_KEY));
			proxyInputLayout.setEnabled(!isRunning);
		} 

		proxyText.setText(proxyStr);


		switch (tunnelType) {
			case Settings.bTUNNEL_TYPE_SSH_DIRECT:
				((AppCompatRadioButton) findViewById(R.id.activity_mainSSHDirectRadioButton))
					.setChecked(true);
				customPayloadSwitch.setChecked(true);
				break;

			case Settings.bTUNNEL_TYPE_SSH_PROXY:
				((AppCompatRadioButton) findViewById(R.id.activity_mainSSHProxyRadioButton))
					.setChecked(true);
				customPayloadSwitch.setChecked(true);
                break;
            case Settings.bTUNNEL_TYPE_SSH_SSL:
                ((AppCompatRadioButton) findViewById(R.id.activity_mainSSHSSLRadioButton))
                    .setChecked(true);
				customPayloadSwitch.setChecked(false);
                break;
			case Settings.bTUNNEL_TYPE_PAY_SSL:
				customPayloadSwitch.setChecked(true);
                break;
			case Settings.bTUNNEL_TYPE_SSL_RP:
				customPayloadSwitch.setChecked(true);
                break;
		}

		int msgVisibility = View.GONE;
		int loginVisibility = View.GONE;
		String msgText = "";
		boolean enabled_radio = !isRunning;

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			
			if (prefs.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				loginVisibility = View.VISIBLE;
				
				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));
				
				inputPwUser.setEnabled(!isRunning);
				inputPwPass.setEnabled(!isRunning);
				inputPwShowPass.setEnabled(!isRunning);
				
				//inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			
			String msg = mConfig.getPrivString(Settings.CONFIG_MENSAGEM_KEY);
			if (!msg.isEmpty()) {
				msgText = msg.replace("\n", "<br/>");
				msgVisibility = View.VISIBLE;
			}
			
			if (mConfig.getPrivString(Settings.PROXY_IP_KEY).isEmpty() ||
					mConfig.getPrivString(Settings.PROXY_PORTA_KEY).isEmpty()) {
				enabled_radio = false;
			}
		}

		loginLayout.setVisibility(loginVisibility);
		configMsgText.setText(msgText.isEmpty() ? "" : Html.fromHtml(msgText));
		configMsgLayout.setVisibility(msgVisibility);
		
		// desativa/ativa radio group
		for (int i = 0; i < metodoConexaoRadio.getChildCount(); i++) {
			metodoConexaoRadio.getChildAt(i).setEnabled(enabled_radio);
		}
	}
	
	
	private synchronized void doSaveData() {
        try {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
            SharedPreferences.Editor edit = prefs.edit();
			if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					int pos = payloadSpinner.getSelectedItemPosition();
					// int modeType = prefs.getInt("TunnelMode",modeGroup.getCheckedRadioButtonId());


					boolean directModeType = config.getNetworksArray().getJSONObject(pos).getBoolean("isSSL");
					boolean sshssltype =  config.getNetworksArray().getJSONObject(pos).getBoolean("wsPayload");
					boolean remotessltype = config.getNetworksArray().getJSONObject(pos).getBoolean("remoteHost");
					boolean slowdnstype = config.getNetworksArray().getJSONObject(pos).getBoolean("SlowDns");
					if (directModeType) {
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_SSL).apply();
						String sni = config.getNetworksArray().getJSONObject(pos).getString("SNI");
						edit.putString(Settings.CUSTOM_SNI, sni);
						edit.apply();

					} else if (sshssltype) {
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL).apply();
						String payload = config.getNetworksArray().getJSONObject(pos).getString("Payload");
						String snissl = config.getNetworksArray().getJSONObject(pos).getString("SNI");
						edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload);
						edit.putString(Settings.CUSTOM_SNI, snissl);
						edit.apply();
					}else if (remotessltype){
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSL_RP).apply();
						String payloadrp = config.getNetworksArray().getJSONObject(pos).getString("Payload");
						String sslrp = config.getNetworksArray().getJSONObject(pos).getString("SNI");
						edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payloadrp);
						edit.putString(Settings.CUSTOM_SNI, sslrp);
						edit.apply();
					}else if (slowdnstype){
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS).apply();
						String chvKey = config.getNetworksArray().getJSONObject(pos).getString("chaveKey");
						String nvKey = config.getNetworksArray().getJSONObject(pos).getString("serverNameKey");
						String dnsKey = config.getNetworksArray().getJSONObject(pos).getString("dnsKey");

						edit.putString(Settings.CHAVE_KEY, chvKey);
						edit.putString(Settings.NAMESERVER_KEY, nvKey);
						edit.putString(Settings.DNS_KEY, dnsKey);
						edit.apply();
					} else {
						prefs.edit().putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY).apply();
						String payload = config.getNetworksArray().getJSONObject(pos).getString("Payload");
						edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payload);
						edit.apply();
					}
				}
			}
            
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	private void loadServerData() {
		try {
			SharedPreferences prefs = mConfig.getPrefsPrivate();
			SharedPreferences.Editor edit = prefs.edit();
			//    int modeType = prefs.getInt("TunnelMode",modeGroup.getCheckedRadioButtonId());
            int pos1 = serverSpinner.getSelectedItemPosition();
            int pos2 = payloadSpinner.getSelectedItemPosition();
            boolean directModeType = config.getNetworksArray().getJSONObject(pos2).getBoolean("isSSL");
            boolean sshssltype = config.getNetworksArray().getJSONObject(pos2).getBoolean("wsPayload");
			boolean remotessltype = config.getNetworksArray().getJSONObject(pos2).getBoolean("remoteHost");
			boolean slowdnstype = config.getNetworksArray().getJSONObject(pos2).getBoolean("SlowDns");
            if (directModeType) {
                String ssl_port = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port);

			} else if (sshssltype) {
				String ssl_port1 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
				edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port1);

			} else if (remotessltype) {
				String ssl_port2 = config.getServersArray().getJSONObject(pos1).getString("SSLPort");
				edit.putString(Settings.SERVIDOR_PORTA_KEY, ssl_port2);

			} else if (slowdnstype) {
				edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
				edit.putString(Settings.SERVIDOR_PORTA_KEY, "2222");
            } else {
                String ssh_port = config.getServersArray().getJSONObject(pos1).getString("ServerPort");
                edit.putString(Settings.SERVIDOR_PORTA_KEY, ssh_port);
            }
			String ssh_server = config.getServersArray().getJSONObject(pos1).getString("ServerIP");
			String remote_proxy = config.getServersArray().getJSONObject(pos1).getString("ProxyIP");
			String proxy_port = config.getServersArray().getJSONObject(pos1).getString("ProxyPort");
            String ssh_user = config.getServersArray().getJSONObject(pos1).getString("ServerUser");
            String ssh_password = config.getServersArray().getJSONObject(pos1).getString("ServerPass");
            edit.putString(Settings.USUARIO_KEY, ssh_user);
            edit.putString(Settings.SENHA_KEY, ssh_password);
			edit.putString(Settings.SERVIDOR_KEY, ssh_server);
			edit.putString(Settings.PROXY_IP_KEY, remote_proxy);
			edit.putString(Settings.PROXY_PORTA_KEY, proxy_port);
			edit.apply();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadServer() {
		try {
			if (serverList.size() > 0) {
				serverList.clear();
				serverAdapter.notifyDataSetChanged();
			}
			for (int i = 0; i < config.getServersArray().length(); i++) {
				JSONObject obj = config.getServersArray().getJSONObject(i);
				serverList.add(obj);
				serverAdapter.notifyDataSetChanged();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadNetworks1() {
		try {
			if (payloadList.size() > 0) {
				payloadList.clear();
				payloadAdapter.clear();
			}
			JSONObject obj = getJSONConfig2(this);
			JSONArray networkPayload = obj.getJSONArray("Networks");
			for (int i = 0; i < networkPayload.length(); i++) {
				payloadList.add(networkPayload.getJSONObject(i));
			}
			//Collections.sort(listNetwork, NetworkNameComparator());
			payloadAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Toast.makeText(NusantaraActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private void loadNetworks() {
		try {
			if (payloadList.size() > 0) {
				payloadList.clear();
				payloadAdapter.notifyDataSetChanged();
			}
			for (int i = 0; i < config.getNetworksArray().length(); i++) {
				JSONObject obj = config.getNetworksArray().getJSONObject(i);
				payloadList.add(obj);
				payloadAdapter.notifyDataSetChanged();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateConfig(final boolean isOnCreate) {
		new ConfigUpdate(this, new ConfigUpdate.OnUpdateListener() {
			@Override
			public void onUpdateListener(String result) {
				try {
					if (!result.contains("Error on getting data")) {
						String json_data = AESCrypt.decrypt(config.PASSWORD, result);
						if (isNewVersion(json_data)) {
							newUpdateDialog(result);
						} else {
							if (!isOnCreate) {
								noUpdateDialog();
							}
						}
					} else if(result.contains("Error on getting data") && !isOnCreate){
						errorUpdateDialog(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start(isOnCreate);
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
	}

	private boolean isNewVersion(String result) {
		try {
			String current = config.getVersion();
			String update = new JSONObject(result).getString("Version");
			return config.versionCompare(update, current);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void newUpdateDialog(final String result) throws JSONException, GeneralSecurityException {
        String json = AESCrypt.decrypt(config.PASSWORD, result);
        String releasenotes = new JSONObject(json).getString("ReleaseNotes");
		
		View inflate = LayoutInflater.from(this).inflate(R.layout.notif, null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		
		TextView title = inflate.findViewById(R.id.notiftext1);
		TextView ms = inflate.findViewById(R.id.confimsg);
		Button ok = inflate.findViewById(R.id.appButton1);
		TextView cancel = inflate.findViewById(R.id.appButton2);
		title.setText("Notification");
		ms.setText(releasenotes);
		ok.setText("Apply & Restart");
		cancel.setText("Dismiss");
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		alert.show();
		ok.setOnClickListener(new OnClickListener() {
			@Override
					public void onClick(View p1){
						try
						{
							alert.dismiss();
							File file = new File(getFilesDir(), "Config.json");
							OutputStream out = new FileOutputStream(file);
							out.write(result.getBytes());
							out.flush();
							out.close();
							restart_app();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			
		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1) {
					
					alert.dismiss();
				}
				
					
					
					
				});
		alert.show();
			
	}
	 public static void designed(String by, String nethph){
		 View inflate = LayoutInflater.from(NusantaraApp.getApp()).inflate(R.layout.nusantaradesign, null);
		 TextView d1 = inflate.findViewById(R.id.d1);
		 ImageView i1 = inflate.findViewById(R.id.i1);
		 TextView d2 = inflate.findViewById(R.id.d2);
		 d1.setText(by);
		 d2.setText(nethph);
		 i1.setBackgroundResource(R.drawable.nusantara);
		 Toast t1 = new Toast(NusantaraApp.getApp());
		 t1.setView(inflate);
		 t1.setDuration(1);
		 t1.show();
	 }
	
	
	private void iphunt () {
	
		designed("Latest IP Hunter by:", "JAMES FOX SMITH'"); // Do not remove this Toast | this is the sign of the pogi
		View inflate = LayoutInflater.from(this).inflate(R.layout.notif, null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		TextView title = inflate.findViewById(R.id.notiftext1);
		final TextView ms = inflate.findViewById(R.id.confimsg);
		final Button ok = inflate.findViewById(R.id.appButton1);
		TextView cancel = inflate.findViewById(R.id.appButton2);
		title.setText("IP Hunter");
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
		ms.setText("Make sure that you are now in the New IP. Click the button to check your IP!");
		ok.setText("Check Now");
		cancel.setText("Close");
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		alert.show();
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					ms.setText("Please wait while we are checking your IP...");
					ok.setEnabled(false);
					ok.setText("Checking...");
					new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								try {
									int l = 0;
									URL whatismyip = new URL(ServerAuthenticatorNone.ill);
									String magic = "✅ Congrats!! You are now connected to New IP.";
									String fail = "🚫 Disconnected. Please Airplane Mode On/Off and Try Again.";
									try{		
										Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ServerAuthenticatorNone.ll, 80));
										HttpURLConnection connection = (HttpURLConnection) whatismyip.openConnection(proxy);
										connection.setRequestMethod("GET");
										connection.connect();
										connection.getContentLength();
										connection.setConnectTimeout(3000);
										InputStream in = connection.getInputStream();
										byte[] buffer = new byte[4096];
										int countBytesRead;
										while((countBytesRead = in.read(buffer)) != -1) {
											l += countBytesRead;
										}
										in.markSupported();
										if (l == 333){
											ms.setText(magic);
											ok.setText("Check Again");
											ok.setEnabled(true);
											return;
										}
										if (connection.getResponseCode() == 200){
											ms.setText(magic);
											ok.setText("Check Again");
											ok.setEnabled(true);
											return;
										}
										in.close();
										ms.setText(fail);;
										ok.setText("Check Again");
										ok.setEnabled(true);
									} catch (IOException e) {
										ok.setText("Check Again");
										ok.setEnabled(true);
										ms.setText(fail);
									}

								}catch (MalformedURLException e) {}}
						}, 1000);
						
				}
				
			});

		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1) {

					alert.dismiss();
				}




			});
		alert.show();

	}
	
	private void noUpdateDialog() {
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
		View inflate = LayoutInflater.from(this).inflate(R.layout.notif, null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		overridePendingTransition(R.anim.grow, R.anim.shrink);
		TextView title = inflate.findViewById(R.id.notiftext1);
		TextView ms = inflate.findViewById(R.id.confimsg);
		Button ok = inflate.findViewById(R.id.appButton1);
		TextView cancel = inflate.findViewById(R.id.appButton2);
		title.setText("No Update Available");
		ms.setText("Please try again soon.");
		ok.setText("Hide");
		cancel.setText(".");
		cancel.setVisibility(View.GONE);
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					alert.dismiss();
				}
			});

		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1) {

					alert.dismiss();
				}




			});
		alert.show();
	}

	private void errorUpdateDialog(String error) {
		View inflate = LayoutInflater.from(this).inflate(R.layout.notif, null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		AdsManager.newInstance(getApplicationContext())
			.loadAdsInterstitial();
		TextView title = inflate.findViewById(R.id.notiftext1);
		TextView ms = inflate.findViewById(R.id.confimsg);
		Button ok = inflate.findViewById(R.id.appButton1);
		TextView cancel = inflate.findViewById(R.id.appButton2);
		title.setText("Notification");
		ms.setText("Update Error: " + error);
		ok.setText("OK");
		cancel.setText("");
		cancel.setVisibility(View.GONE);
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		alert.show();
		ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
				alert.dismiss();
				}
			});

		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1) {

					alert.dismiss();
				}




			});
		alert.show();
	}
	private void settings() {
		View inflate = LayoutInflater.from(this).inflate(R.layout.settings, null);
		AlertDialog.Builder builer = new AlertDialog.Builder(this); 
		builer.setView(inflate); 
		overridePendingTransition(R.anim.grow, R.anim.shrink);
		LinearLayout button1 = inflate.findViewById(R.id.but1);
		LinearLayout button2 = inflate.findViewById(R.id.but2);
		LinearLayout button3 = inflate.findViewById(R.id.but3);
		LinearLayout button4 = inflate.findViewById(R.id.but4);
		LinearLayout button5 = inflate.findViewById(R.id.but5);
		LinearLayout button6 = inflate.findViewById(R.id.but6);
		final AlertDialog alert = builer.create(); 
		alert.setCanceledOnTouchOutside(false);
		alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alert.getWindow().setGravity(Gravity.CENTER); 
		button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					
				}
			});

		button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){

				}
			});
		button3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					startActivity(new Intent(NusantaraActivity.this,ConfigGeralActivity.class));
					
				}
			});
		button4.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
					

				}
			});
		button5.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){

				}
			});
		button6.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1){
			
				}
			});
		
		alert.show();
	}
	private void restart_app() {
		try{ 
		
				new Handler().postDelayed(new Runnable(){

						@Override
						public void run() {

							Intent intent = new Intent("android.intent.action.MAIN");
							intent.setClassName(getPackageName() , "com.nusantara.id.LauncherActivity");
							startActivity(intent); 


						}
					}, 10);

	
			
		} catch(Exception e)
		{ Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show(); 
		}
	}
	
	public void offlineUpdate() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		startActivityForResult(intent, PICK_FILE);
	}
	
	private void updateapp(final boolean hmm, final boolean daddy)
    {
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new AppUpdateChecker(this, "https://raw.githubusercontent.com/Paolo1467/Config-No-Load-Update/main/Blu%20flame%20App%20Update", new AppUpdateChecker.Listener() {

                private AlertDialog.Builder dialog0;

				private ProgressDialog progressDialog;
                @Override
                public void onLoading(){
					AdsManager.newInstance(getApplicationContext())
						.loadAdsInterstitial();
					// IF UPDATE APP IS FALSE THEN PROGRESS DIALOG WILL SHOW..
if (!hmm){
	progressDialog = new ProgressDialog(NusantaraActivity.this, R.style.Edsondialog);
	progressDialog.setMessage("Checking for app updates...");
	progressDialog.setTitle("Please wait...");
	progressDialog.setCancelable(true);
	progressDialog.show();
	
}
                }
                @Override
                public void onCompleted(final String config)
                {
					progressDialog.dismiss();
                    try
                    {
                        final JSONObject obj = new JSONObject(config);
                        if(versionName.equals(obj.getString("newVersion")))
                        {

                        }else{
                            
							View inflate = LayoutInflater.from(NusantaraActivity.this).inflate(R.layout.notif, null);
							AlertDialog.Builder builer = new AlertDialog.Builder(NusantaraActivity.this); 
							builer.setView(inflate); 

							TextView title = inflate.findViewById(R.id.notiftext1);
							TextView ms = inflate.findViewById(R.id.confimsg);
							Button ok = inflate.findViewById(R.id.appButton1);
							TextView cancel = inflate.findViewById(R.id.appButton2);
							title.setText("Notification");
							ms.setText(Html.fromHtml(obj.getString("versionNotes")));
							ok.setText("Update Now");
							cancel.setText("Exit");
							final AlertDialog alert = builer.create(); 
							alert.setCanceledOnTouchOutside(false);
							alert.setCancelable(false);
							alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
							alert.getWindow().setGravity(Gravity.CENTER); 
							alert.show();
							ok.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View p1){
										try
                                        {
                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                                     Uri.parse(obj.getString("apkUrl"))));


										}
                                        catch (JSONException e)
                                        {

										}
									}
								});

							cancel.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View p1) {

										Utils.exitAll(NusantaraActivity.this);
										
									}




								});
							alert.show();
                            

                        }
                    }
                    catch (Exception e)
                    {
						progressDialog.dismiss();
						if (!hmm){

							NusantaraApp.toast(getApplicationContext(), "App Update Error: " + e.getMessage(), Gravity.BOTTOM);

						}
						}

                }

                @Override
                public void onCancelled()
                {
					
					}
           

                @Override
                public void onException(String ex)
                {

                }
            }).execute();
    }





    
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_FILE)
		{
			if (resultCode == RESULT_OK) {
				try {
					Uri uri = data.getData();
					String intentData = importer(uri);
					//String cipter = AESCrypt.decrypt(ConfigUtil.PASSWORD, intentData);
					File file = new File(getFilesDir(), "Config.json");
					OutputStream out = new FileOutputStream(file);
					out.write(intentData.getBytes());
					out.flush();
					out.close();
					loadServer();
					loadNetworks();
					restart_app();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String importer(Uri uri)
	{
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try
		{
			reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));

			String line = "";
			while ((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
			reader.close();
		}
		catch (IOException e) {e.printStackTrace();}
		return builder.toString();
	} 
	/**
	 * Tunnel SSH
	 */


	 
	public void startOrStopTunnel(Activity activity) {
		if (SkStatus.isTunnelActive()) {
            SharedPreferences prefs = mConfig.getPrefsPrivate();
			TunnelManagerHelper.stopSocksHttp(activity);
			this.startService(new Intent(this, InjectorService.class).setAction("STOP"));
//pauseChronometer();
			resetChronometer();
			chronometer.stop();
			chronometer.setText("00h:00m:00s");



		}
		else {
			
			// oculta teclado se vísivel, tá com bug, tela verde
			//Utils.hideKeyboard(activity);
			if(startSSHCheckbox.isChecked()){
				this.startService(new Intent(this, InjectorService.class).setAction("START"));
				
			}else{
				this.startService(new Intent(this, InjectorService.class).setAction("STOP"));
				
			}
			Settings mConfig = new Settings(activity);
			if (mConfig.getPrefsPrivate()
				.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				if (inputPwUser.getText().toString().isEmpty() || 
					inputPwPass.getText().toString().isEmpty()) {
					Toast.makeText(this, R.string.error_userpass_empty, Toast.LENGTH_SHORT)
						.show();
					return;
				}
			}
	
			
			Intent intent = new Intent(activity, LaunchVpn.class);
			intent.setAction(Intent.ACTION_MAIN);

			if (mConfig.getHideLog()) {
				intent.putExtra(LaunchVpn.EXTRA_HIDELOG, true);
			}

			activity.startActivity(intent);
		}
	}
	
	public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
	public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
	
	
	private void setPayloadSwitch(int tunnelType, boolean isCustomPayload) {
		SharedPreferences prefs = mConfig.getPrefsPrivate();

		boolean isRunning = SkStatus.isTunnelActive();

		customPayloadSwitch.setChecked(isCustomPayload);

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			payloadEdit.setEnabled(false);
			
			if (mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY).isEmpty()) {
				customPayloadSwitch.setEnabled(false);
			}
			else {
				customPayloadSwitch.setEnabled(!isRunning);
			}
			
			if (!isCustomPayload && tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY)
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
			else
				payloadEdit.setText("*******");
		}
		else {
			customPayloadSwitch.setEnabled(!isRunning);

			if (isCustomPayload) {
				payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
				payloadEdit.setEnabled(!isRunning);
			}
			else if (tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
				payloadEdit.setEnabled(true);
			}
		}

		if (isCustomPayload || tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
			payloadLayout.setVisibility(View.VISIBLE);
		}
		else {
			payloadLayout.setVisibility(View.GONE);
		}
	}

	public void setStarterButton(Button starterButton, Activity activity) {
		String state = SkStatus.getLastState();
		boolean isRunning = SkStatus.isTunnelActive();

		if (starterButton != null) {
			int resId;
			
			SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

			if (ConfigParser.isValidadeExpirou(prefsPrivate
					.getLong(Settings.CONFIG_VALIDADE_KEY, 0))) {
				resId = R.string.expired;
				starterButton.setEnabled(false);

				if (isRunning) {
					startOrStopTunnel(activity);
				}
			}
			else if (prefsPrivate.getBoolean(Settings.BLOQUEAR_ROOT_KEY, false) &&
					ConfigParser.isDeviceRooted(activity)) {
			   resId = R.string.blocked;
			   starterButton.setEnabled(false);
			   
			   Toast.makeText(activity, R.string.error_root_detected, Toast.LENGTH_SHORT)
					.show();

			   if (isRunning) {
				   startOrStopTunnel(activity);
			   }
			}
			else if (SkStatus.SSH_INICIANDO.equals(state)) {
				resId = R.string.stop;
				drawerlayout.openDrawer(Gravity.END);
				starterButton.setEnabled(false);
			}
			else if (SkStatus.SSH_PARANDO.equals(state)) {
				resId = R.string.state_stopping;
				starterButton.setEnabled(false);
			}
			else {
				resId = isRunning ? R.string.stop : R.string.start;
				starterButton.setEnabled(true);
			}

			starterButton.setText(resId);
		}
	}
	
	private boolean isMostrarSenha = false;
	
	@Override
	public void onClick(View p1)
	{
		SharedPreferences prefs = mConfig.getPrefsPrivate();

		switch (p1.getId()) {
			case R.id.activity_starterButtonMain:
				doSaveData();
				loadServerData();
				startOrStopTunnel(this);
				break;

			case R.id.activity_mainInputProxyLayout:
				break;
			
			case R.id.btnAddTime:
                startActivity(new Intent(NusantaraActivity.this,CoinsActivity.class));
				break;
                
			case R.id.activity_mainAutorText:
				String url = "http://t.me/MasFha04";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getText(R.string.open_with)));
				break;
				
			case R.id.activity_mainInputShowPassImageButton:
				isMostrarSenha = !isMostrarSenha;
				if (isMostrarSenha) {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_black_24dp));
				}
				else {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_off_black_24dp));
				}
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup p1, int p2)
	{
		SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();

		switch (p1.getCheckedRadioButtonId()) {
			case R.id.activity_mainSSHDirectRadioButton:
				edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
				proxyInputLayout.setVisibility(View.GONE);
				break;

			case R.id.activity_mainSSHProxyRadioButton:
				edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY);
				proxyInputLayout.setVisibility(View.VISIBLE);
				break;
		}

		edit.apply();

		doSaveData();
		doUpdateLayout();
	}

 
	protected void showBoasVindas() {
		designed("Powered by", "M'Devz'");
	}
	
	@Override
    public void updateState(final String state, String msg, int localizedResId, final ConnectionStatus level, Intent intent)
    {
        mHandler.post(new Runnable() {
                @Override
                public void run() {
                    doUpdateLayout();
                    if (SkStatus.isTunnelActive()){

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTED)){
                            connectionStatus.setText(R.string.connected);
                       start();
					   showme.setVisibility(View.VISIBLE);
							chronometer.setText("00H:00m:00s");
							chronometer.start();
							serverSpinner.setEnabled(false);
							payloadSpinner.setEnabled(false);
							NusantaraApp.toast(getApplicationContext(), "Connected!", Gravity.BOTTOM);
							// SABI KO NGA SAYO DITO ILAGAY EH :)
							AdsManager.newInstance(getApplicationContext())
								.loadAdsInterstitial();
                        }

                        if (level.equals(ConnectionStatus.LEVEL_NOTCONNECTED)){
                            connectionStatus.setText(R.string.servicestop);
                        }   
						

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTING_SERVER_REPLIED)){
                            connectionStatus.setText(R.string.authenticating);
						
                        }     
						

                        if (level.equals(ConnectionStatus.LEVEL_CONNECTING_NO_SERVER_REPLY_YET)){
                            connectionStatus.setText(R.string.connecting);
							serverSpinner.setEnabled(false);
							payloadSpinner.setEnabled(false);
						
                        }   
						
						
                        if (level.equals(ConnectionStatus.LEVEL_AUTH_FAILED)){
                            connectionStatus.setText(R.string.authfailed);
                        }
					//	SocksHttpApp.toast(getApplicationContext(), "Authentication Failed!!!", Gravity.BOTTOM);
						//Bat mo nilagay dito? Kaya pala nagtadtad ng toast sa vpn mo...
                        if (level.equals(ConnectionStatus.UNKNOWN_LEVEL)){
                            connectionStatus.setText(R.string.disconnected);
							stop();
							showme.setVisibility(View.GONE);
							serverSpinner.setEnabled(true);
							payloadSpinner.setEnabled(true);
							NusantaraApp.toast(getApplicationContext(), "Disconnected!", Gravity.BOTTOM);
                        }
						
						
                        //if (level.equals(ConnectionStatus.LEVEL_RECONNECTING)){
                        //      status.setText(R.string.reconnecting);
                    }               
                    if (level.equals(ConnectionStatus.LEVEL_NONETWORK)){
                        connectionStatus.setText(R.string.nonetwork);
						NusantaraApp.toast(getApplicationContext(), "No Internet -_-", Gravity.BOTTOM);
                    }
					
                }

				// Goods na yan ! wag mo na kutikutihin!
				
            });
		
		switch (state) {
			case SkStatus.SSH_CONECTADO:
				// carrega ads banner
				if (adsBannerView != null && TunnelUtils.isNetworkOnline(NusantaraActivity.this)) {
					adsBannerView.setAdListener(new AdListener() {
						@Override
						public void onAdLoaded() {
							if (adsBannerView != null && !isFinishing()) {
								adsBannerView.setVisibility(View.VISIBLE);
							}
						}
					});
					adsBannerView.postDelayed(new Runnable() {
						@Override
						public void run() {
							// carrega ads interestitial
							AdsManager.newInstance(getApplicationContext())
								.loadAdsInterstitial();
							// ads banner
							if (adsBannerView != null && !isFinishing()) {
								adsBannerView.loadAd(new AdRequest.Builder()
									.build());
							}
						}
					}, 5000);
				}
			break;
		}
	}


	/**
	 * Recebe locais Broadcast
	 */

	private BroadcastReceiver mActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;

            if (action.equals(UPDATE_VIEWS) && !isFinishing()) {
				doUpdateLayout();
			}
			
        }
    };


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Menu Itens
		switch (item.getItemId()) {
			case R.id.Share_Logs:
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,SkStatus.CopyLogs());
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, SkStatus.CopyLogs());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
				break;

			case R.id.settings:
				drawerlayout.openDrawer(logka);
				break;

				// logs opções
			case R.id.miLimparLogs:
				mLogAdapter.clearLog();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		
		
			showExitDialog();
		
	}

	@Override
    public void onResume() {
        super.onResume();
		
		//doSaveData();
		//doUpdateLayout();
		
		SkStatus.addStateListener(this);
		new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					runOnUiThread(new Runnable()
						{
							@Override
							public void run()
							{
								updateHeaderCallback();
                                checkNetwork();
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			}, 0,1000);
		if (adsBannerView != null) {
			adsBannerView.resume();
		}
		if (!mTimerEnabled) {

            resumeTime(); 
        }
        addTime();
    }

	@Override
	protected void onPause()
	{
		super.onPause();
		
		doSaveData();
		
		SkStatus.removeStateListener(this);
		
		if (adsBannerView != null) {
			adsBannerView.pause();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		LocalBroadcastManager.getInstance(this)
			.unregisterReceiver(mActivityReceiver);
			
		if (adsBannerView != null) {
			adsBannerView.destroy();
		}
	}


	/**
	 * DrawerLayout Listener
	 */

	/**
	 * Utils
	 */

	public static void updateMainViews(Context context) {
		Intent updateView = new Intent(UPDATE_VIEWS);
		LocalBroadcastManager.getInstance(context)
			.sendBroadcast(updateView);
	}
	
	public void showExitDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).
			create();
		dialog.setTitle(getString(R.string.attention));
		dialog.setMessage(getString(R.string.alert_exit));

		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.
				string.exit),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Utils.exitAll(NusantaraActivity.this);
				}
			}
		);

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.
				string.minimize),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// minimiza app
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);
				}
			}
		);

		dialog.show();
	}
	private void start() {

        if (saved_ads_time == 0) {

            Toast.makeText(NusantaraActivity.this, "Your time is expiring soon, please click ADD TIME to renew access!", Toast.LENGTH_LONG).show();

            long millisInput = 1000 * 500;

            setTime(millisInput);
        }

        if (!mTimerRunning) {
            startTimer();
        }

    }


    private void stop() {
        if (mTimerRunning) {
            pauseTimer();
        }

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void updateCountDownText() {

        long days = TimeUnit.MILLISECONDS.toDays(mTimeLeftInMillis);
        long daysMillis = TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis - daysMillis);
        long hoursMillis = TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis - daysMillis - hoursMillis);
        long minutesMillis = TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis - daysMillis - hoursMillis - minutesMillis);

        String resultString = (days < 10 ? "0"+days: days) + ":" + (hours < 10 ? "0"+hours: hours)+":"+(minutes < 10 ? "0"+minutes: minutes)+":"+ (seconds < 10 ? "0"+seconds: seconds);

        mTextViewCountDown.setText(resultString);
    }

    private void setTime(long milliseconds) {

        saved_ads_time = mTimeLeftInMillis + milliseconds;

        mTimeLeftInMillis = saved_ads_time;
        updateCountDownText();

    }


    private void addTime(long time){

        setTime(time);

        if (mTimerRunning){
            pauseTimer();
        }

        //startTimer();
    }


    private void saveTime(long time) {
        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        SharedPreferences.Editor time_edit = mTime.edit();
        time_edit.putLong("SAVED_TIME", time).apply();
    }

    private void addTime(){
        long added = sp.getLong("isAdded", 0);

        if (added == 1){
            long added_time = sp.getLong("AddedTime", 0);

            if (mTimerRunning){
                addTime(added_time);
            }else{
                setTime(added_time);
            }
            sp.edit().putLong("isAdded", 0).apply();
            saveTime(mTimeLeftInMillis);
        }
    }

    private void resumeTime() {

        SharedPreferences mTime = getSharedPreferences("time", Context.MODE_PRIVATE);

        long saved_time = mTime.getLong("SAVED_TIME", 1);
        setTime(saved_time);

        // Use this code to continue time if app close accidentally while connected
        /**
         String state = SkStatus.getLastState();

         if (SkStatus.SSH_CONECTADO.equals(state)) {

         if (!mTimerRunning){
         startTimer();
         }
         }**/

        mTimerEnabled = true;
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                saveTime(mTimeLeftInMillis);
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                pauseTimer();
                saved_ads_time = 0;

                // Code for auto stop vpn (sockshtttp)

                Intent stopVPN = new Intent(NusantaraService.TUNNEL_SSH_STOP_SERVICE);
                LocalBroadcastManager.getInstance(NusantaraActivity.this)
                    .sendBroadcast(stopVPN);


                Toast.makeText(NusantaraActivity.this, "Time expired! Click Add + Time to renew access!", Toast.LENGTH_LONG).show();

            }

        }.start();
        mTimerRunning = true;


    }
    
}

