package com.example.trafficlive;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViolationFragment extends Fragment {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;


    public ViolationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       final View violView = inflater.inflate(R.layout.fragment_violation,container,false);

        mSearchField = (EditText) violView.findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) violView.findViewById(R.id.searchBtn);

        mResultList = (RecyclerView) violView.findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(getContext()));

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Violator");

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

               firebaseUsersSearch(searchText);

            }
        });

        return violView;
    }

    private void firebaseUsersSearch(String searchText) {

        Toast.makeText(getContext(),"Started Search",Toast.LENGTH_LONG).show();;

        Query firebaseSearchQuery = mUserDatabase.orderByChild("Time").startAt(searchText).endAt(searchText +"\uf8ff");

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(firebaseSearchQuery, Users.class)
                .build();

        final FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

                holder.setDetails(model.getTime(),model.getViolationOrAccident(),model.getDate(),model.getLocation());

            }


            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_layout,viewGroup,false);


                return new UsersViewHolder(v);

            }



        };


//        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
//
//             Users.class,
//             R.layout.list_layout,
//             UsersViewHolder.class,
//             firebaseSearchQuery
//
//        ) {
//            @Override
//            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
//
//                holder.setDetails(model.getTime(),model.getViolationOrAccident(),model.getDate(),model.getLocation());
//
//            }
//
//            @NonNull
//            @Override
//            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                return null;
//            }
//        };



        mResultList.setAdapter(firebaseRecyclerAdapter);

    }




    //view holder class

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mview;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mview = itemView;

        }

        public void setDetails(String userTime,String userViolAcc, String userDate,String userLocation){

            TextView user_time = (TextView) mview.findViewById(R.id.Vtime);
            TextView user_violAcc = (TextView) mview.findViewById(R.id.accident_violation);
            TextView user_date = (TextView) mview.findViewById(R.id.Vdate);
            TextView user_location = (TextView) mview.findViewById(R.id.VlocationText);

            user_time.setText(userTime);
            user_violAcc.setText(userViolAcc);
            user_date.setText(userDate);
            user_location.setText(userLocation);


        }



    }

}
