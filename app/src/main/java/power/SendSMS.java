package power;
import android.util.Log;
import android.telephony.SmsManager;
import android.widget.Toast;
import android.app.Activity;
/**
 * Created by xakus on 11.04.2016.
 */
public class SendSMS extends Activity {

    public static   void sendSMSMessage(Activity activity,String phoneNo,String message) {
        Log.i("Send SMS", "");


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(activity, "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(activity, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
