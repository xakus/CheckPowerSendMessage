package power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xakus.checkpowersendmessage.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xakus on 07.04.2016.
 */
public class BattManager extends AppCompatActivity {
    TextView mText;
    TextView timeText;
    ImageView imageView;

    private SharedPreferences mSettings;
    private String phoneNumber = "";
    private String smsText = "";
    protected String wait = "";
    private int waitTime = 1000;
    private boolean send = false;
    private  boolean isRunning=false;
    private boolean isSended=false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onResume() {
        super.onResume();
        getSettings();
        timer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSettings();
        timer();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
         mText.destroyDrawingCache();
        timeText.destroyDrawingCache();
         imageView.destroyDrawingCache();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);



        mText = (TextView) this.findViewById(R.id.text);
        timeText = (TextView) this.findViewById(R.id.textTime);
        imageView = (ImageView) this.findViewById(R.id.imageView);

        if(TS.imageOn==null) {
            TS.imageOn = BitmapFactory.decodeResource(this.getResources(), R.drawable.on);
        }
        if(TS.imageOff==null) {
            TS.imageOff = BitmapFactory.decodeResource(this.getResources(), R.drawable.off);
        }
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mText.setText("");
                mText.append("\nStatus: " + TS.batteryStatus(intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)));
                mText.append("\nPlugged: " + TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)));
                mText.append("\nWait: " + wait + "sec");
                mText.append("\nPhone number: " + phoneNumber + "");
                if (TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).equals("UNDEFINED")) {
                    imageView.setImageBitmap(TS.imageOff);
                    send = true;

                } else {
                    waitTime = Integer.parseInt(wait);
                    send = false;
                    isRunning=true;
                    imageView.setImageBitmap(TS.imageOn);
                }
            }
        };
        try {
            registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (Exception e) {
               e.getMessage();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //setContentView(R.layout.settings);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.activity_settings:
                //new SendSMS().sendSMSMessage(this);
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.activity_about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void txt() {
        timeText.setText(String.valueOf(waitTime));
    }

    private void timer() {
        txt();

        if (TS.timer == null) {
            TS.timer = new Timer();
            TS.timer.schedule(new UpdateTimeTask(), 0, 1000);
        }







    }

    private void getSettings() {
        ///
        mSettings = getSharedPreferences(TS.SETTINGS, Context.MODE_PRIVATE);
        if (mSettings.contains(TS.PHONE_NUMBER)) {
            phoneNumber = (mSettings.getString(TS.PHONE_NUMBER, ""));
        }
        if (mSettings.contains(TS.SMS_TEXT)) {
            smsText = (mSettings.getString(TS.SMS_TEXT, ""));
        }
        if (mSettings.contains(TS.WAIT)) {
            wait = (mSettings.getString(TS.WAIT, "0"));
        }
        if (phoneNumber == null) {
            phoneNumber = "0";

        } else if (phoneNumber == "") {
            phoneNumber = "0";
        }
        if (smsText == null) {
            smsText = "0";

        } else if (smsText == "") {
            smsText = "0";
        }

        if (wait == null) {
            wait = "0";

        } else if (wait == "") {
            wait = "0";
        }
if(!send) {
    waitTime = Integer.parseInt(wait);
}
        ////
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        send=false;
        isRunning=false;
        android.os.Process.killProcess(android.os.Process.myPid());


    }
private  void send(){
    SendSMS.sendSMSMessage(this,phoneNumber,smsText);

}
    private class UpdateTimeTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (send&&isRunning) {
                        if (waitTime > 0) {
                            waitTime -= 1;
                        }
                    }
                    if(waitTime==0&&send&&isRunning){
                        isRunning=false;
                        send=false;
                        timeText.setText("");
                      send();
                        timeText.append("send SMS!!!");
                    }else if(isRunning) {
                    txt();
                    }
                }
            });

        }
    }


}
