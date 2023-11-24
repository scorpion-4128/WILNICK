package com.nusantara.id;

import android.os.AsyncTask;
import android.content.Context;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.util.Log;



public class AppUpdateChecker extends AsyncTask<String, String, String> {
    private static final String TAG = "NetGuard.Download";
    private static final String PrivateKey = "Renz_FreeNet_PH";

    private Context context;
    private Listener listener;
    private String url;

    /** mga di ginagamit na variables
     private PowerManager.WakeLock wakeLock;
     private HttpURLConnection uRLConnection;
     private InputStream is;
     private BufferedReader buffer;
     */

    public interface Listener {
        void onLoading();

        void onCompleted(String config) throws Exception;
        void onCancelled();
        void onException(String ex);
    }

    public AppUpdateChecker(Context context, String url, Listener listener) {
        this.context = context;
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onLoading();
    }

    // Start Fixed by Awoo
    @Override
    protected String doInBackground(String... urlArg) {
        StringBuilder sb = new StringBuilder();
        try {
            String api = url;
            if(!api.startsWith("http")) {
                api = new StringBuilder().append("").append(url).toString();
            }

            URL url = new URL(api);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(isr);    
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error on getting data: " + e.getMessage();
        }
    }
    // end of fixed line

    @Override
    protected void onCancelled() {
        super.onCancelled();
        // Log.i(TAG, "Cancelled");
        // pd.dismiss();
        listener.onCancelled();
    }

    @Override
    protected void onPostExecute(String result) {
        // wakeLock.release();
        // nm.cancel(1);
        // pd.dismiss();
        Log.i(TAG, PrivateKey);
        Log.i(TAG, "error while verifying the privateKey");
        try {
            if (result.equals("error")) {
                listener.onException(result);
            }
            else {
                listener.onCompleted(result);
            }
        }
        catch (Exception e){
            listener.onException(e.getMessage());
        }
    }
   
}

