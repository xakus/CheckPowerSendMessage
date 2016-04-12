package power;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xakus.checkpowersendmessage.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AboutActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "EXIT.", Toast.LENGTH_LONG).show();
        finish();

    }
ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        image= (ImageView) findViewById(R.id.imageView3);
        if(TS.imageOn==null) {
            TS.imageOn = BitmapFactory.decodeResource(this.getResources(), R.drawable.on);
        }
        if(TS.imageOn!=null){

            image.setImageBitmap(TS.imageOn);

        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }




}
