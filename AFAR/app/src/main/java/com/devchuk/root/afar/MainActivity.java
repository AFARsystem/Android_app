package com.devchuk.root.afar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    private LocationManager locationManager = null;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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


    //displays whether GPS is on or not
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }


    //create alertbox
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void send(View v) {
        Button button = (Button) v;
        button.setText("SENT");
        EditText edittext = (EditText) findViewById(R.id.editText);
        String details = edittext.getText().toString();

        String phoneNo = "3476955532";

        //find location

        /*create the message
        Swagmaster
        Address:
        City:
        Detail:
        Phone:
        */
        //send the message

        flag = displayGpsStatus();
        if (flag) {

            try {
                SmsManager smsManager = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SENT";

                sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);


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
        else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }
}
