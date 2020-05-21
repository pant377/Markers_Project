package com.example.themarkerproject;

import androidx.fragment.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static GoogleMap mMap;
    public static MarkerOptions markerOptions = new MarkerOptions();
    public static int counter = 0;
    // Ολα μου τα αντηκημενα εδω μαζι και ο counter ειναι δηλομενα public static ωστα να μπορω να τα χρησιμοποιησω και σε αλλες κλασεις γιατι θα χρειαστουν
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsActivity.mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {  // εδω χρησιμοποιησα ενα clickListener ωστε να παρω ακριβως τεις σηντεταγμενες
            @Override                                                                    // που θα κανει κλικ ο χρηστης η θα πατισει με το χερι τουκαι επειτα αν ο
            public void onMapClick(LatLng latLng) {    // counter ειναι μικροτερος του 5 θετω σαν lat lag αυτα που ανεκτισε απο εδω και ανοιγω το activity2 για τα υπολοιπα
                if(counter < 5){
                    openActivity();
                    markerOptions.position(latLng);
                }else{alertDialog();}}
        });}
    public void openActivity(){  // μεσο αυτης της μεθοδου ανοιγω το activity2
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);}
    private void alertDialog() {    // Αν ο counter ειναι μεγαλυτερος η ισος του 5 ανοιγη το παρακατο alertDialog με ενα κουμπακι οκ
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("You can't Add anymore");
        dialog.setTitle("Attention");
        dialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"OK is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }}