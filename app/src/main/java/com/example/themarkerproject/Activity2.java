package com.example.themarkerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity2 extends AppCompatActivity implements SensorEventListener {
    SensorManager DeviceSensorManager;
    String thermometer = "";
    Sensor therm;
    public FirebaseFirestore db;
    public Button button;
    public String thefinaltext;
    public static String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        db = FirebaseFirestore.getInstance();   // Με την εκινιση της activity2  περνω το instance της βασης
        DeviceSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // και αρχικοποιω τον sensor manager ωστε να τον χρησιμοποιησω
        button = findViewById(R.id.buttonPaneldenxeroni);  // Βρησκο το κουμπι add  απο το layout του details
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // και δημιουργω ενα onClicklistener για να κανω τα παρακατο
                EditText txtDescription = findViewById(R.id.Detailsthisisone);  // περνω την τιμη απο τα details που ειναι textview τησ activity2 και που ειναι εκει που γραφουμε το κειμενακι για τεις λεπτομερειες του marker
                text = txtDescription.getText().toString();  // το κανω string
                thefinaltext = text + thermometer; // και προσθετω και την τιμη του θερμομετρου
                MapsActivity.markerOptions.title(thefinaltext); // προσθετω στα markerOptions το text που θελω να εχει σε τελικη μορφη
                Spinner myspinner = findViewById(R.id.spinner1);  // βρησκο το spinner για την επιλογη χρωματος
                String color = myspinner.getSelectedItem().toString(); // το μετατρεπω σε string
                switch(color){  // και κανω μια switch ωστε με βαση το χρωμα να διμιουργησει τον καταληλο marker επανω στον χαρτη
                    case "Blue":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        break;
                    case "Red":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        break;
                    case "Green":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        break;
                    case "Yellow":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        break;
                    case "Orange":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        break;
                    case "Rose":
                        MapsActivity.mMap.addMarker(MapsActivity.markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                        break;}
                MapsActivity.counter++;  // οταν βγει απο την switch αυξανω κατα 1 τον counter απο την κλαση MapsActivity
                try {  // μεσα σε ενα try catch  δημιουργω ενα αντικημενο το οποιο το περναω και στην βαση
                    Markers m = new Markers();
                    m.setLang(String.valueOf(MapsActivity.markerOptions.getPosition().longitude));
                    m.setLat(String.valueOf(MapsActivity.markerOptions.getPosition().latitude));
                    m.setText(thefinaltext);
                    m.setColor(color);
                    db.collection("Markers").add(m).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Not Ok",Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Not Ok",Toast.LENGTH_SHORT).show();
                }
                finish();}
        }); }
    @Override// ολες οι παρακατω μεθοδοι ειναι για τον σενσορα
    protected void onPause(){   // αυτη η μεθοδος στο pause κανει unregister τον σενσορα ωστε να μην δουλευει χωρις λογο
        super.onPause();
        DeviceSensorManager.unregisterListener(this);}
    @Override
    protected void onResume(){ // στο resume  κανει assing των σενσορα θερμοκρασιας στον manager και αν δεν εχει βγαζει μνμ
        super.onResume();
        therm = DeviceSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (therm != null){
            DeviceSensorManager.registerListener(this,therm,SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this,"No Temp Sensor ",Toast.LENGTH_LONG).show();}}
    @Override
    public void onSensorChanged(SensorEvent event) { // σε καθε αλλαγη της μετρισης του σενσορα αλλαζω το string thermometer με την τιμη την νεας θερμοκρασιας σε αυτο το format
        thermometer =" | Temperature: " + (event.values[0])+" C";}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}}