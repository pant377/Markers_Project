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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity2 extends AppCompatActivity implements SensorEventListener {
    SensorManager DeviceSensorManager;
    String thermometer = "";
    Sensor therm;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static FirebaseFirestore db;
    public Button button;
    public String thefinaltext;
    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        db = FirebaseFirestore.getInstance();
        DeviceSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        button = findViewById(R.id.buttonPaneldenxeroni);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtDescription = findViewById(R.id.Detailsthisisone);
                text = txtDescription.getText().toString();
                thefinaltext = text + thermometer;
                MapsActivity.markerOptions.title(thefinaltext);
                Spinner myspinner = findViewById(R.id.spinner1);
                String color = myspinner.getSelectedItem().toString();

                switch(color){
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
                MapsActivity.counter++;
                try {
                    System.out.println("mpike sto try ");
                    Markers m = new Markers();
                    m.setLang(String.valueOf(MapsActivity.markerOptions.getPosition().longitude));
                    m.setLat(String.valueOf(MapsActivity.markerOptions.getPosition().latitude));
                    m.setText(thefinaltext);
                    m.setColor(color);
                    db.collection("Markers").add(m).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_SHORT).show();
                            System.out.println("mpikkeee");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Not Ok",Toast.LENGTH_SHORT).show();
                            System.out.println("mpikkeee lathos");
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Not Ok",Toast.LENGTH_SHORT).show();
                }
                finish();}
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        DeviceSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        therm = DeviceSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (therm != null){
            DeviceSensorManager.registerListener(this,therm,SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this,"No Temp Sensor ",Toast.LENGTH_LONG).show();
        }}
    @Override
    public void onSensorChanged(SensorEvent event) {
        thermometer =" | Temperature: " + (event.values[0])+" C";}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }}
