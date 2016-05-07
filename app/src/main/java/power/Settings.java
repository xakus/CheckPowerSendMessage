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
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.xakus.checkpowersendmessage.R;


/**
 * Created by xakus on 10.04.2016.
 */
public class Settings extends AppCompatActivity {
    private SharedPreferences mSettings;
    private int mCounter;
    private TextView phoneNumber1;
    private TextView phoneNumber2;
    private CheckBox  chekB1;
    private CheckBox  chekB2;
    private CheckBox  chekB11;
    private CheckBox  chekB22;
    private TextView smsText1;
    private TextView smsText2;
    private TextView waitText;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.settings);

        mSettings = getSharedPreferences(TS.SETTINGS, Context.MODE_PRIVATE);
        phoneNumber1 = (TextView) findViewById(R.id.Phone1);
        phoneNumber2 = (TextView) findViewById(R.id.Phone2);
        chekB1=(CheckBox) findViewById(R.id.ch1);
        chekB2=(CheckBox) findViewById(R.id.ch2);
        chekB11=(CheckBox) findViewById(R.id.ch11);
        chekB22=(CheckBox) findViewById(R.id.ch22);
        smsText1 = (TextView) findViewById(R.id.editTextSMSText1);
        smsText2 = (TextView) findViewById(R.id.editTextSMSText2);
        waitText = (TextView) findViewById(R.id.editTextSeconds);
      if(mSettings.contains(TS.PHONE_NUMBER1)){
          phoneNumber1.setText(mSettings.getString(TS.PHONE_NUMBER1,"0"));
      }
        if(mSettings.contains(TS.CHEK1)){
            chekB1.setChecked(mSettings.getBoolean(TS.CHEK1,false));
        }
        if(mSettings.contains(TS.CHEK2)){
            chekB2.setChecked(mSettings.getBoolean(TS.CHEK2,false));
        }
        if(mSettings.contains(TS.CHEK11)){
            chekB11.setChecked(mSettings.getBoolean(TS.CHEK11,false));
        }
        if(mSettings.contains(TS.CHEK22)){
            chekB22.setChecked(mSettings.getBoolean(TS.CHEK22,false));
        }
        if(mSettings.contains(TS.PHONE_NUMBER2)){
            phoneNumber2.setText(mSettings.getString(TS.PHONE_NUMBER2,"0"));
        }
        if(mSettings.contains(TS.SMS_TEXT1)){
            smsText1.setText(mSettings.getString(TS.SMS_TEXT1,""));
        } if(mSettings.contains(TS.SMS_TEXT2)){
            smsText2.setText(mSettings.getString(TS.SMS_TEXT2,""));
        }
        if(mSettings.contains(TS.WAIT)){
            waitText.setText(mSettings.getString(TS.WAIT,"0"));
        }

    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void  okClick(View view){

        phoneNumber1 = (TextView) findViewById(R.id.Phone1);
        phoneNumber2 = (TextView) findViewById(R.id.Phone2);

       // chekB1=(CheckBox) findViewById(R.id.ch1) ;
        //chekB2=(CheckBox) findViewById(R.id.ch2) ;

        smsText1 = (TextView) findViewById(R.id.editTextSMSText1);
        smsText2 = (TextView) findViewById(R.id.editTextSMSText2);
        waitText = (TextView) findViewById(R.id.editTextSeconds);

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(TS.PHONE_NUMBER1, phoneNumber1.getText().toString());
        editor.putString(TS.PHONE_NUMBER2, phoneNumber2.getText().toString());

        editor.putBoolean(TS.CHEK1,chekB1.isChecked());
        editor.putBoolean(TS.CHEK2,chekB2.isChecked());

        editor.putBoolean(TS.CHEK11,chekB11.isChecked());
        editor.putBoolean(TS.CHEK22,chekB22.isChecked());

        editor.putString(TS.SMS_TEXT1, smsText1.getText().toString());
        editor.putString(TS.SMS_TEXT2, smsText2.getText().toString());
        editor.putString(TS.WAIT, waitText.getText().toString());
        editor.apply();
            finish();

    }
    public void  cancelClick(View view){
        finish();
    }
}
