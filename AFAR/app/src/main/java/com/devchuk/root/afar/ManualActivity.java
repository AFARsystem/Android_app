package com.devchuk.root.afar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ManualActivity extends Activity {

    ListView listView ;
    public static final String CATEGORY = "com.devchuk.root.afar.CATEGORY"; //Key
    public static final String DESCRIPTION = "com.devchuk.root.afar.DESCRIPTION"; //Key
    static String[] values, descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);



        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        values = new String[] {
                "Allergies",
                "Asthma Attack",
                "Bleeding",
                "Broken Bone",
                "Burns",
                "Choking",
                "Concussion",
                "Heart Attack",
                "Heat Stroke",
                "Hypothermia",
                "Poison",
                "Seizure",
                "Strains and sprains",
                "Stroke"
        };
        descriptions = new String[]{
                "1) The person may develop a rash, itchiness or swelling on their hands, feet or face. Their breathing may slow down. \n2) When you obvserve these symptoms, call 911. \n3) If the person has a known allergy and has an auto-injector/Epi-pen, you can help them to use it. Give them constant reassurance while waiting for the ambulance.",
                "1) Help the person sit in a comfortable position and help them take their medication. \n2) If the attack becomes severe, they don't have their medication, or they don't improve, call 911.",
                "1) Using whatever materials available, apply pressure on the wound.\n2) If the bleeding doesn't stop, get 911 on the line as soon as possible. Maintain pressure before help arrives.",
                "1) Find a way to prevent unnecessary movement of the injury. \n2)Call 911 if the injury is obviously deformed \n3)Make sure the injury is supported until help arrives.",
                "1) Cool the burn under cool running water for at least 10 minutes. \n2)If the burn requires more care, loosely cover the burn with plastic covering. \n3)If necessary, call 911.",
                "1) For adults and children, alternate between 5 back blows between the shoulder blades and 5 abdominal thrusts.\n2) For infants under a year old, keep head lower than chest, support head and neck, and follow the same procedure until the infant makes noise.\\n3) Call 911 in the case of voice change, unconsciousness, or any other concerning factor.",
                "1) If they become confused, drowsy, vomit, or if the fall was greater than two times their height, call 911. \n2) Ask them to rest and apply a cold compress to the injury.",
                "1) Call 911 immediately and make sure they're in a comfortable position. \n2)If the person goes unconscious and stops breathing, push firmly downwards in the middle of the chest and release at a rate of 100 per minute.",
                "1) Call 911 as soon as possible. \n2) Move the person to a cooler place. Remove tight clothes and apply rapid cooling with ice packs.",
                "1) Call 911. \n2) Warm the person slowly ad, if they're conscious, give them warm drinks or soup.",
                "1) Find out what they have taken and the quantity. \n2)Call 911. \n3)Do not give them anything to drink.",
                "1) Do not restrain, and do not put anything under his or her head. Use cloth to protect head from injury.\n2) Allow person to rest on their side with head tilted back.\n3) Call 911 using our app :D",
                "1) Apply ice. \n2)If there is no improvement seek medical advice.",
                "1) FAST- Is one side of the face weaker than the other? Can they equally raise both arms? Is their speech impaired? Call 911.\n2) Immediately get 911 on the line. Continue to reassure the patient."
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
*/
                displayInfo(itemPosition);

            }

        });
    }


    public void displayInfo(int itemPosition){
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra(CATEGORY, values[itemPosition]);
        intent.putExtra(DESCRIPTION, descriptions[itemPosition]);
        startActivity(intent);
    }

}

