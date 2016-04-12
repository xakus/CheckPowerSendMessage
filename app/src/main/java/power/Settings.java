package power;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.xakus.checkpowersendmessage.R;


/**
 * Created by xakus on 10.04.2016.
 */
public class Settings extends AppCompatActivity {
    private SharedPreferences mSettings;
    private int mCounter;
    private TextView phoneNumber;
    private TextView smsText;
    private TextView waitText;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.settings);

        mSettings = getSharedPreferences(TS.SETTINGS, Context.MODE_PRIVATE);
        phoneNumber = (TextView) findViewById(R.id.editTextPhoneNumber);
        smsText = (TextView) findViewById(R.id.editTextSMSText);
        waitText = (TextView) findViewById(R.id.editTextSeconds);
      if(mSettings.contains(TS.PHONE_NUMBER)){
          phoneNumber.setText(mSettings.getString(TS.PHONE_NUMBER,"0"));
      }
        if(mSettings.contains(TS.SMS_TEXT)){
            smsText.setText(mSettings.getString(TS.SMS_TEXT,""));
        }
        if(mSettings.contains(TS.WAIT)){
            waitText.setText(mSettings.getString(TS.WAIT,"0"));
        }

    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void  okClick(View view){

        phoneNumber = (TextView) findViewById(R.id.editTextPhoneNumber);
        smsText = (TextView) findViewById(R.id.editTextSMSText);
        waitText = (TextView) findViewById(R.id.editTextSeconds);

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(TS.PHONE_NUMBER, phoneNumber.getText().toString());
        editor.putString(TS.SMS_TEXT, smsText.getText().toString());
        editor.putString(TS.WAIT, waitText.getText().toString());
        editor.apply();
            finish();

    }
    public void  cancelClick(View view){
        finish();
    }
}
