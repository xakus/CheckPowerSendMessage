package power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.xakus.checkpowersendmessage.R;

/**
 * Created by xakus on 02.05.2016.
 */

public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //context.startActivity(new Intent(context.getApplicationContext(),BattManager.class));
        Intent i = new Intent(context, BattManager.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
