package com.example.trafficlive;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViolationFragment extends Fragment{

//    private EditText mSearchField;
//    private ImageButton mSearchBtn;
//
//    private RecyclerView mResultList;
//
//    private DatabaseReference mUserDatabase;

    EditText search_edit_text;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;

    ArrayList<String> dateList;
    ArrayList<String> timeList;
    ArrayList<String> violOrAccList;
    ArrayList<String> faultList;
    ArrayList<String> licenceDetailsList;
    ArrayList<String> numplateList;
    ArrayList<String> locationList;
    ArrayList<String> descriptionList;
    Users users;



    public ViolationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View violView = inflater.inflate(R.layout.fragment_violation,container,false);

        search_edit_text = (EditText) violView.findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) violView.findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Violator");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        violOrAccList = new ArrayList<>();
        faultList = new ArrayList<>();
        licenceDetailsList = new ArrayList<>();
        numplateList = new ArrayList<>();
        locationList = new ArrayList<>();
        descriptionList = new ArrayList<>();


        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }else {

                    dateList.clear();
                    timeList.clear();
                    violOrAccList.clear();
                    faultList.clear();
                    licenceDetailsList.clear();
                    numplateList.clear();
                    locationList.clear();
                    descriptionList.clear();
                    recyclerView.removeAllViews();
                }

            }
        });

        return violView;
    }

    private void setAdapter(final String searchedString) {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dateList.clear();
                timeList.clear();
                violOrAccList.clear();
                faultList.clear();
                licenceDetailsList.clear();
                numplateList.clear();
                locationList.clear();
                descriptionList.clear();
                recyclerView.removeAllViews();


                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){


                    String uid = snapshot.getKey();

                    String Date = snapshot.child("Date").getValue(String.class);
                    String Time = snapshot.child("Time").getValue(String.class);
                    String ViolationOrAccident = snapshot.child("ViolationOrAccident").getValue(String.class);
                    String Fault = snapshot.child("Fault").getValue(String.class);
                    String LicenceDetails = snapshot.child("LicenceDetails").getValue(String.class);
                    String NumberPlate = snapshot.child("NumberPlate").getValue(String.class);
                    String Location = snapshot.child("Location").getValue(String.class);
                    String Description = snapshot.child("Description").getValue(String.class);




                          Log.e("log","success");


                   if (LicenceDetails.toLowerCase().contains(searchedString)) {

                       dateList.add(Date);
                       timeList.add(Time);
                       violOrAccList.add(ViolationOrAccident);
                       faultList.add(Fault);
                       licenceDetailsList.add(LicenceDetails);
                       numplateList.add(NumberPlate);
                       locationList.add(Location);
                       descriptionList.add(Description);

                       counter++;

//                        licenceDetailsList.add(LicenceDetails);
//                        dateList.add(Date);
//                        timeList.add(Time);
//                        violOrAccList.add(ViolationOrAccident);
//                        counter++;

//                    }


                    }


                    if (counter == 20){
                        break;
                    }

                }

                users = new Users(getActivity(),dateList,timeList,violOrAccList,faultList,licenceDetailsList,numplateList,locationList,descriptionList);
                recyclerView.setAdapter(users);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
