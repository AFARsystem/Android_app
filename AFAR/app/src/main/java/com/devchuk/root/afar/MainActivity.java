package com.devchuk.root.afar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    private Boolean flag = false;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

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

    protected void alertbox() {
        Button button = (Button) findViewById(R.id.button);
        button.setText("SEND");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your device's GPS is disabled")
                .setCancelable(false)
                .setTitle("GPS STATUS")
                .setPositiveButton("Turn it on",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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


        String phoneNo = "9292442978";//Insert server phone number here. 9292442978

        gps = new GPSTracker(MainActivity.this);
        double latitude = 0.0;
        double longitude = 0.0;
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        String message = "swagmaster2000\n";
        String cityName;
        String countryName;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(latitude,longitude, 1);
            cityName = addresses.get(0).getLocality();
            countryName = addresses.get(0).getCountryName();
            Address address = addresses.get(0);
            String readablehurrdurr = String.format(
                    "%s, %s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ?
                            address.getAddressLine(0) : "",
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getCountryName(), address.getPostalCode()); //getPostalCode()
            message += (readablehurrdurr  + "\n");
            message += (countryName + "\n");
            message += (cityName + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        message += details + "\n";
        //Toast.makeText(getApplicationContext(), message + longitude + "\n" + latitude, Toast.LENGTH_LONG).show();

        if (edittext.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please describe the emergency and your location", Toast.LENGTH_LONG).show();
            button.setText("SEND");
            return;
        }

        flag = displayGpsStatus();

        if (flag) {

            try {
                SmsManager smsManager = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SENT";

                sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);


                smsManager.sendTextMessage(phoneNo, null, message, sentPI, null);
                Toast.makeText(getApplicationContext(), "Sent. Please wait for aid.",//"SMS sent. " + sentPI.toString()
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
            alertbox();
        }
        edittext.setText("");
    }
}