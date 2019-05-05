package com.example.trafficlive;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataEntryFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener {


    private Button getLocation;
    private TextView locationText;

    LocationManager locationManager;

    private static final String TAG = "DataEntryFragment";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextView mLicenceDetails;
    private TextView mNumPlate;
    private TextView mLocation;
    private TextView mDate;
    private TextView mTime;
    private Button mSubmitBtn;
    private Spinner mspinner;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;



    public DataEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_data_entry, container, false);

        ///////////////sending data/////////////////

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Violator");


//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                if (firebaseAuth.getCurrentUser() == null){
//
//                    Intent registerIntent = new Intent(getActivity(),LoginActivity.class);
//                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(registerIntent);
//
//                }
//
//            }
//        };


        mLicenceDetails = (TextView) view.findViewById(R.id.licence_details);
        mNumPlate = (TextView) view.findViewById(R.id.numPlate);
        mLocation = (TextView) view.findViewById(R.id.locationText);
        mDate = (TextView) view.findViewById(R.id.date);
        mTime = (TextView) view.findViewById(R.id.timer);
        mspinner = (Spinner) view.findViewById(R.id.spinner);

        mSubmitBtn = (Button) view.findViewById(R.id.submit);




        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                startPosting();

            }
        });


        ////////////////////////////////////////////

        ///////////spinner///////////

        mspinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.violation_accident, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(adapter);
        mspinner.setOnItemSelectedListener(this);

        /////////////////////////////

        Button scan_qr = (Button) view.findViewById(R.id.scan_qr);
        scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRActivity.class);
                startActivity(intent);
            }
        });

        Button rec_numPlate = (Button) view.findViewById(R.id.rec_numPlate);
        rec_numPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), NumPlateActivity.class);
                startActivity(intent2);
            }
        });

        //enter date
        mDisplayDate = (TextView) view.findViewById(R.id.date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG,"onDataSet: dd/mm/yyy:" + year+"/"+month+"/"+dayOfMonth);

                String date = year+"/"+month+"/"+dayOfMonth;
                mDisplayDate.setText(date);
            }
        };

        //enter time

        Button timeBtn = (Button) view.findViewById(R.id.timeBtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar2 = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time = "Current Time:"+ format.format(calendar2.getTime());
                TextView timeView = (TextView) view.findViewById(R.id.timer);
                timeView.setText(time);
            }
        });

        ////////////////  GPS CURRENT LOCATION  ///////////////////

        //super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        //setContentView(R.layout.fragment_data_entry);

        getLocation = (Button) view.findViewById(R.id.getLocation);
        locationText = (TextView) view.findViewById(R.id.locationText);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        return view;
    }

    private void startPosting() {

        String LicenceDetVal = mLicenceDetails.getText().toString().trim();
        String NumPlateVal = mNumPlate.getText().toString().trim();
        String LocationVal = mLocation.getText().toString().trim();
        String DateVal = mDate.getText().toString().trim();
        String TimeVal = mTime.getText().toString().trim();
        String SpinnerVal = mspinner.getSelectedItem().toString().trim();


//        if (!TextUtils.isEmpty(test)){

            DatabaseReference newPost = mDatabase.push();

            newPost.child("LicenceDetails").setValue(LicenceDetVal);
            newPost.child("NumberPlate").setValue(NumPlateVal);
            newPost.child("Location").setValue(LocationVal);
            newPost.child("Date").setValue(DateVal);
            newPost.child("Time").setValue(TimeVal);
            newPost.child("ViolationOrAccident").setValue(SpinnerVal);




      //  }

    }



    //    private ActionBar getSupportActionBar() {
//    }

//    private void setContentView(int fragment_data_entry) {
//    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }




    @Override
    public void onLocationChanged(Location location) {

        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

//        new AlertDialog.Builder(getContext())
//                .setTitle("Required GPS Permission")
//                .setMessage("You have to give this permission to access the feature")
//                .setPositiveButton("OK")

    }


    //spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    //spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        mAuth.addAuthStateListener(mAuthListener);
//    }


}

