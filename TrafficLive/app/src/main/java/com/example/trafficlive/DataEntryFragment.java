package com.example.trafficlive;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//import android.content.SharedPreferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataEntryFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener {

    //-----------------------------------------
    private String qr_code;
    private String numPlate_code;
    //-----------------------------------------
    private List<String> names;

    //SharedPreferences sharedpreferences;
    private Button getLocation;
    private TextView locationText;

    private ProgressDialog progressDialog;

    LocationManager locationManager;

    private static final String TAG = "DataEntryFragment";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //TextView scanResults;

    private TextView mLicenceDetails;
    private TextView mNumPlate;
    private TextView mLocation;
    private TextView mDate;
    private TextView mTime;
    private EditText mDescription;
    private Button mSubmitBtn;
    private Spinner mspinner;
    private Spinner mspinner1;


    private StorageReference mStorage;
    private DatabaseReference mDatabase;


//    private DatabaseReference mDatabaseUser;

    private FirebaseAuth mAuth;

    private FirebaseUser mCurrentUser;
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
        mDatabase.keepSynced(true);

//        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());


        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();

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
        mDescription= (EditText) view.findViewById(R.id.editText2);
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
        mspinner1 = view.findViewById(R.id.spinnerViol);



        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClass = parent.getItemAtPosition(position).toString();
                switch (selectedClass)
                {
                    case "Violation":
                        // assigning div item list defined in XML to the div Spinner
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.item_div_class_1, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mspinner1.setAdapter(adapter1);


                        break;

                    case "Accident":

                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.items_div_class_2, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mspinner1.setAdapter(adapter2);


                        break;


                }

                //set divSpinner Visibility to Visible
                mspinner1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        /////////////////////////////

        Button scan_qr = (Button) view.findViewById(R.id.scan_qr);

        scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---------------------------------------------------------
                Bundle bundleIntent1 = new Bundle();
                bundleIntent1.putString("NumPlateActivity", numPlate_code);
                Intent intent = new Intent(getActivity(), QRActivity.class);
                intent.putExtras(bundleIntent1);
                startActivity(intent);
                //---------------------------------------------------------


            }

        });



        readBundle(getArguments());






        Button rec_numPlate = (Button) view.findViewById(R.id.rec_numPlate);
        rec_numPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------------------------------------------------------------------
                Bundle bundleIntent2 = new Bundle();
                bundleIntent2.putString("QRActivity", qr_code);
                Intent intent2 = new Intent(getActivity(), NumPlateActivity.class);
                intent2.putExtras(bundleIntent2);
                startActivity(intent2);

                //---------------------------------------------------------------------
                //readBundle(getArguments());


            }
        });


        //readBundle2(getArguments());

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
                String time = "Time" + format.format(calendar2.getTime());
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

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        return view;
    }



    private void readBundle(Bundle bundle) {
        if (bundle != null) {


            //---------------------------------------------------
            numPlate_code = bundle.getString("NumPlateActivity");
            qr_code = bundle.getString("QRActivity");

            mNumPlate.setText(numPlate_code);
            mLicenceDetails.setText(qr_code);
            //----------------------------------------------------



        }
    }

    /*
        private void readBundle2(Bundle bundle) {
            if (bundle != null) {

                String strtext2 = bundle.getString("NumPlateActivity");
                mNumPlate.setText(strtext2);

            }
        }
    */
    private void startPosting() {

        final String LicenceDetVal = mLicenceDetails.getText().toString().trim();
        final String NumPlateVal = mNumPlate.getText().toString().trim();
        final String LocationVal = mLocation.getText().toString().trim();
        final String DateVal = mDate.getText().toString().trim();
        final String TimeVal = mTime.getText().toString().trim();
        final String DescriptionVal = mDescription.getText().toString().trim();

        final String SpinnerVal = mspinner.getSelectedItem().toString().trim();
        final String SpinnerVal1 = mspinner1.getSelectedItem().toString().trim();



//        if (!TextUtils.isEmpty(test)){

        final DatabaseReference newPost = mDatabase.push();

        newPost.child("LicenceDetails").setValue(LicenceDetVal);
        newPost.child("NumberPlate").setValue(NumPlateVal);
        newPost.child("Location").setValue(LocationVal);
        newPost.child("Date").setValue(DateVal);
        newPost.child("Time").setValue(TimeVal);
        newPost.child("Description").setValue(DescriptionVal);
        newPost.child("ViolationOrAccident").setValue(SpinnerVal);
        newPost.child("Fault").setValue(SpinnerVal1);




    }



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

//        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1));
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




}

