package power;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import power.BattManager;
import com.example.xakus.checkpowersendmessage.R;

/**
 * Created by xakus on 10.04.2016.
 */
public class Settings extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }
    public void  okClick(View view){

            finish();

    }
}
