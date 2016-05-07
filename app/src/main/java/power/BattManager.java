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
import android.widget.CheckBox;
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
    TextView mText2;
    TextView timeText;
    ImageView imageView;

    private SharedPreferences mSettings;
    private String phoneNumber1 = "";
    private String phoneNumber2 = "";
    private String smsText1 = "";
    private String smsText2 = "";
    protected String wait = "";
    private int waitTime = 1000;
    private boolean sendoff = false;
    private boolean sendon = false;
    private boolean isRunning = false;
    private boolean isSended = false;
    private boolean ch1;
    private boolean ch2;
    private boolean ch11;
    private boolean ch22;
    private boolean isON = false;
    private boolean isOFF = false;
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
        setTimeText();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSettings();
        timer();
        setTimeText();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // store the data in the fragment
        mText.destroyDrawingCache();
        mText2.destroyDrawingCache();
        timeText.destroyDrawingCache();
        imageView.destroyDrawingCache();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        final int[] isChange = {0};
        mText = (TextView) this.findViewById(R.id.text);
        mText2 = (TextView) this.findViewById(R.id.text2);
        timeText = (TextView) this.findViewById(R.id.textTime);
        imageView = (ImageView) this.findViewById(R.id.imageView);

        if (TS.imageOn == null) {
            TS.imageOn = BitmapFactory.decodeResource(this.getResources(), R.drawable.on);
        }
        if (TS.imageOff == null) {
            TS.imageOff = BitmapFactory.decodeResource(this.getResources(), R.drawable.off);
        }
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isSended) {
                    waitTime = Integer.parseInt(wait);
                }
                mText.setText("");
                mText.append("\nStatus: " + TS.batteryStatus(intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)));
                mText.append("\nPlugged: " + TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)));
                mText.append("\nWait: " + wait + "sec");

//                if (ch1) {
//                    if(ch11) {
//                        mText.append("\nNumber1: " + phoneNumber1 + " will send 'light ON and OFF'");
//                    }else {
//                        mText.append("\nPhone number1: " + phoneNumber1 + " will send 'light OFF'");
//                    }
//                } else if(ch11){
//                    if(ch1) {
//                        mText.append("\nNumber1: " + phoneNumber1 + " will send 'light ON and OFF'");
//                    }else {
//                        mText.append("\nPhone number1: " + phoneNumber1 + " will send 'light ON'");
//                    }
//                } else  {
//                    mText.append("\nPhone number1: " + phoneNumber1 + " won't send");
//                }
//
//                if (ch2) {
//                    if(ch22) {
//                        mText.append("\nNumber2: " + phoneNumber2 + " will send 'light ON and OFF'");
//                    }else {
//                        mText.append("\nPhone number2: " + phoneNumber2 + " will send 'light OFF'");
//                    }
//                } else if(ch22){
//                    if(ch2) {
//                        mText.append("\nNumber2: " + phoneNumber2 + " will send 'light ON and OFF'");
//                    }else {
//                        mText.append("\nPhone number2: " + phoneNumber2 + " will send 'light ON'");
//                    }
//                } else  {
//                    mText.append("\nPhone number2: " + phoneNumber2 + " won't send");
//                }
//                if (ch2) {
//                    mText.append("\nPhone number2: " + phoneNumber2 + " will send");
//                } else {
//                    mText.append("\nPhone number2: " + phoneNumber2 + " won't send");
//                }


                if (TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).equals("UNDEFINED")) {
                    imageView.setImageBitmap(TS.imageOff);

                    sendoff = true;
                    sendon = false;

                    if (isChange[0] == 0) {
                        isChange[0] = 1;
                        isSended = false;
                        waitTime = Integer.parseInt(wait);
                    }
                    isOFF = true;
                    if (isON && isOFF) {
                        isRunning = true;
                    }
                } else {
                    isON = true;
                    if (isChange[0] == 1) {
                        isChange[0] = 0;
                        isSended = false;
                        waitTime = Integer.parseInt(wait);
                    }
                    sendoff = false;
                    sendon = true;


                    imageView.setImageBitmap(TS.imageOn);
                }

            }
        };
        try {
            registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (Exception e) {
            e.getMessage();
        }
       setTimeText();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

private void  setTimeText(){
    mText2.setText("");
    if (ch1) {
        if(ch11) {
            mText2.append("\nNumber1: " + phoneNumber1 + " will send 'light ON and OFF'");
        }else {
            mText2.append("\nPhone number1: " + phoneNumber1 + " will send 'light OFF'");
        }
    } else if(ch11){
        if(ch1) {
            mText2.append("\nNumber1: " + phoneNumber1 + " will send 'light ON and OFF'");
        }else {
            mText2.append("\nPhone number1: " + phoneNumber1 + " will send 'light ON'");
        }
    } else  {
        mText2.append("\nPhone number1: " + phoneNumber1 + " won't send");
    }

    if (ch2) {
        if(ch22) {
            mText2.append("\nNumber2: " + phoneNumber2 + " will send 'light ON and OFF'");
        }else {
            mText2.append("\nPhone number2: " + phoneNumber2 + " will send 'light OFF'");
        }
    } else if(ch22){
        if(ch2) {
            mText2.append("\nNumber2: " + phoneNumber2 + " will send 'light ON and OFF'");
        }else {
            mText2.append("\nPhone number2: " + phoneNumber2 + " will send 'light ON'");
        }
    } else  {
        mText2.append("\nPhone number2: " + phoneNumber2 + " won't send");
    }
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
        if (mSettings.contains(TS.PHONE_NUMBER1)) {
            phoneNumber1 = (mSettings.getString(TS.PHONE_NUMBER1, ""));

        }
        if (mSettings.contains(TS.PHONE_NUMBER1)) {
            phoneNumber2 = (mSettings.getString(TS.PHONE_NUMBER2, ""));

        }
        if (mSettings.contains(TS.CHEK1)) {
            ch1 = (mSettings.getBoolean(TS.CHEK1, false));

        }
        if (mSettings.contains(TS.CHEK2)) {
            ch2 = (mSettings.getBoolean(TS.CHEK2, false));

        }
        if (mSettings.contains(TS.CHEK11)) {
            ch11 = (mSettings.getBoolean(TS.CHEK11, false));

        }
        if (mSettings.contains(TS.CHEK22)) {
            ch22 = (mSettings.getBoolean(TS.CHEK22, false));

        }
        if (mSettings.contains(TS.SMS_TEXT1)) {
            smsText1 = (mSettings.getString(TS.SMS_TEXT1, ""));
        }
        if (mSettings.contains(TS.SMS_TEXT2)) {
            smsText2 = (mSettings.getString(TS.SMS_TEXT2, ""));
        }
        if (mSettings.contains(TS.WAIT)) {
            wait = (mSettings.getString(TS.WAIT, "0"));
        }
        if (phoneNumber1 == null) {
            phoneNumber1 = "0";

        } else if (phoneNumber1 == "") {
            phoneNumber1 = "0";
        }
        if (phoneNumber2 == null) {
            phoneNumber2 = "0";

        } else if (phoneNumber2 == "") {
            phoneNumber2 = "0";
        }
        if (smsText1 == null) {
            smsText1 = "0";

        } else if (smsText1 == "") {
            smsText1 = "0";
        }
        if (smsText2 == null) {
            smsText2 = "0";

        } else if (smsText2 == "") {
            smsText2 = "0";
        }

        if (wait == null) {
            wait = "0";

        } else if (wait == "") {
            wait = "0";
        }
        if (!sendoff) {
            waitTime = Integer.parseInt(wait);
        }
        ////
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendoff = false;
        sendon = false;
        isRunning = false;
        android.os.Process.killProcess(android.os.Process.myPid());


    }

    private void sendoff() {
        if (ch1) {
            SendSMS.sendSMSMessage(this, phoneNumber1, smsText1);
        }
        if (ch2) {
            SendSMS.sendSMSMessage(this, phoneNumber2, smsText1);
        }

    }

    private void sendon() {
        if (ch11) {
            SendSMS.sendSMSMessage(this, phoneNumber1, smsText2);
        }
        if (ch22) {
            SendSMS.sendSMSMessage(this, phoneNumber2, smsText2);
        }

    }

    int sentOrNo = 1;

    private class UpdateTimeTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sendoff && isRunning && !isSended && sentOrNo == 1) {
                        if (waitTime > 0) {
                            waitTime -= 1;
                        }
                    }
                    if (sendon && isRunning && !isSended && sentOrNo == 2) {
                        if (waitTime > 0) {
                            waitTime -= 1;
                        }
                    }
                    if (waitTime == 0 && sendoff && isRunning && !isSended) {
                        //   isRunning = false;
                        sendoff = false;
                        timeText.setText("");
                        isSended = true;
                        sendoff();
                        sentOrNo=2;
                        if(ch1||ch2) {
                            timeText.append("SMS light off sent!!!");
                        }else{
                            timeText.append("SMS light off not sent!!!");
                        }
                    } else if (waitTime == 0 && sendon && isRunning && !isSended) {
                        //  isRunning = false;
                        sendon = false;
                        timeText.setText("");
                        isSended = true;
                        sendon();
                        sentOrNo=1;
                        if(ch11||ch22) {
                            timeText.append("SMS light on sent!!!");
                        }else{
                            timeText.append("SMS light on not sent!!!");
                        }
                    } else if (!isSended) {
                        txt();
                    }
                }
            });

        }
    }


}
