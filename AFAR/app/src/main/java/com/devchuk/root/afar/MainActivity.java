package com.devchuk.root.afar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void send(View v) {
        Button button = (Button) v;
        button.setText("SENT");
        EditText edittext = (EditText) findViewById(R.id.editText);
        String details = edittext.getText().toString();

        String phoneNo = "3476955532";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            PendingIntent sentPI;
            String SENT = "SMS_SENT";

            sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);




            smsManager.sendTextMessage(phoneNo, null, details, sentPI, null);
            Toast.makeText(getApplicationContext(), "SMS sent. " + sentPI.toString(),
            Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //error lol
            Toast.makeText(getApplicationContext(),
                    e.toString(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
