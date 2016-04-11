package power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xakus.checkpowersendmessage.AboutActivity;
import com.example.xakus.checkpowersendmessage.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;

import power.TS;

/**
 * Created by xakus on 07.04.2016.
 */
public class BattManager extends AppCompatActivity {
    TextView mText;
    TextView timeText;
    ImageView imageView;
    Timer timer = null;
    private SharedPreferences mSettings;
    private String phoneNumber = "";
    private String smsText = "";
    protected String wait = "";
    private int waitTime = 1000;
    private boolean send = false;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) this.findViewById(R.id.text);
        timeText = (TextView) this.findViewById(R.id.textTime);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        mText.setText("Hello XAKUS!!!");

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mText.setText("");
                mText.append("\nMax scale: " + intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1));
                mText.append("\nCurrent scale: " + intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
                mText.append("\nTemperature: " + intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1));
                mText.append("\nVoltage: " + intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1));
                mText.append("\nTechnology: " + intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY));
                mText.append("\nHealth: " + TS.batteryHealth(intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)));
                mText.append("\nStatus: " + TS.batteryStatus(intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)));
                mText.append("\nPlugged: " + TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)));
                mText.append("\nWait: " + wait + "sec");
                mText.append("\nPhone number: " + phoneNumber + "");
                if (TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).equals("UNDEFINED")) {
                    mText.append("\n:(");
                    imageView.setImageResource(R.drawable.off);
                    send = true;
                } else {
                    waitTime = Integer.parseInt(wait);
                    send = false;
                    mText.append("\n:)");
                    imageView.setImageResource(R.drawable.on);
                }
            }
        };
        try {
            registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (Exception e) {

        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void settingsClick(View view) {
        setContentView(R.layout.settings);
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


        if (timer == null) {
            timer = new Timer();
            timer.schedule(new UpdateTimeTask(), 0, 1000); //тикаем каждую секунду без задержки
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
            wait = (mSettings.getString(TS.WAIT, ""));
        }
        waitTime = Integer.parseInt(wait);
        ////
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BattManager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://power/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BattManager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://power/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class UpdateTimeTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (send) {
                        if (waitTime > 0) {
                            waitTime -= 1;
                        }
                    }
                    txt();
                }
            });

        }
    }
}
