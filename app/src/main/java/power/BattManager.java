package power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
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

import power.TS;

/**
 * Created by xakus on 07.04.2016.
 */
public class BattManager extends AppCompatActivity {
    TextView mText;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) this.findViewById(R.id.text);
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
                if (TS.batteryPlugged(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)).equals("UNDEFINED")) {
                    mText.append("\n:(");
                    imageView.setImageResource(R.drawable.off);
                } else {
                    mText.append("\n:)");
                    imageView.setImageResource(R.drawable.on);
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
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
}
