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
    ArrayList<String> licenceDetailsList;
    ArrayList<String> dateList;
    ArrayList<String> timeList;
    ArrayList<String> violOrAccList;
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

        licenceDetailsList = new ArrayList<>();
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        violOrAccList = new ArrayList<>();

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

                    violOrAccList.clear();
                    dateList.clear();
                    timeList.clear();
                    licenceDetailsList.clear();
                    recyclerView.removeAllViews();
                }

            }
        });

        return violView;
    }

    private void setAdapter(final String searchedString) {

        Log.e("log","top 1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                violOrAccList.clear();
                dateList.clear();
                timeList.clear();
                licenceDetailsList.clear();
                recyclerView.removeAllViews();

                Log.e("log","top 2");

                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    //String uid = snapshot.getKey();
                    String LicenceDetails = snapshot.child("LicenceDetails").getValue(String.class);
                    String Date = snapshot.child("Date").getValue(String.class);
                    String Time = snapshot.child("Time").getValue(String.class);
                    String ViolationOrAccident = snapshot.child("ViolationOrAccident").getValue(String.class);

                    Log.e("log","top");

                  // if (LicenceDetails.toLowerCase().contains(searchedString)) {
                       Log.e("log", "success");
                       licenceDetailsList.add(LicenceDetails);
                       dateList.add(Date);
                       timeList.add(Time);
                       violOrAccList.add(ViolationOrAccident);
                       counter++;


/*
                   }else if (Date.toLowerCase().contains(searchedString)){


//




                       licenceDetailsList.add(LicenceDetails);
                        dateList.add(Date);
                        timeList.add(Time);
                        violOrAccList.add(ViolationOrAccident);
                       counter++;

                   }

*/
                    if (counter == 20){
                        break;
                    }

                }

                users = new Users(getActivity(),violOrAccList,dateList,timeList,licenceDetailsList);
                recyclerView.setAdapter(users);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//       final View violView = inflater.inflate(R.layout.fragment_violation,container,false);

//        mSearchField = (EditText) violView.findViewById(R.id.search_field);
//        mSearchBtn = (ImageButton) violView.findViewById(R.id.searchBtn);
//
//        mResultList = (RecyclerView) violView.findViewById(R.id.result_list);
//        mResultList.setHasFixedSize(true);
//        mResultList.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        mUserDatabase = FirebaseDatabase.getInstance().getReference("Violator");
//
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String searchText = mSearchField.getText().toString();
//
//               firebaseUsersSearch(searchText);
//
//            }
//        });
//
//        return violView;
//    }
//
//    private void firebaseUsersSearch(String searchText) {
//
//        Toast.makeText(getContext(),"Started Search",Toast.LENGTH_LONG).show();;
//
//        Query firebaseSearchQuery = mUserDatabase.orderByChild("Time").startAt(searchText).endAt(searchText +"\uf8ff");
//
//        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
//                .setQuery(firebaseSearchQuery, Users.class)
//                .build();
//
//        final FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
//
//                holder.setDetails(model.getTime(),model.getViolationOrAccident(),model.getDate(),model.getLocation());
//
//            }
//
//
//            @NonNull
//            @Override
//            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//                View v = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.list_layout,viewGroup,false);
//
//
//                return new UsersViewHolder(v);
//
//            }
//
//
//
//        };
//
//
////        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
////
////             Users.class,
////             R.layout.list_layout,
////             UsersViewHolder.class,
////             firebaseSearchQuery
////
////        ) {
////            @Override
////            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
////
////                holder.setDetails(model.getTime(),model.getViolationOrAccident(),model.getDate(),model.getLocation());
////
////            }
////
////            @NonNull
////            @Override
////            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////                return null;
////            }
////        };
//


//        mResultList.setAdapter(firebaseRecyclerAdapter);

//    }




//    //view holder class
//
//    public static class UsersViewHolder extends RecyclerView.ViewHolder{
//
//        View mview;
//
//        public UsersViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mview = itemView;
//
//        }
//
//        public void setDetails(String userTime,String userViolAcc, String userDate,String userLocation){
//
//            TextView user_time = (TextView) mview.findViewById(R.id.Vtime);
//            TextView user_violAcc = (TextView) mview.findViewById(R.id.accident_violation);
//            TextView user_date = (TextView) mview.findViewById(R.id.Vdate);
//            TextView user_location = (TextView) mview.findViewById(R.id.VlocationText);
//
//            user_time.setText(userTime);
//            user_violAcc.setText(userViolAcc);
//            user_date.setText(userDate);
//            user_location.setText(userLocation);
//
//
//        }
//
//
//
//    }

}
