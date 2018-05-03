package com.handi.project2_1572006;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handi.project2_1572006.Adapter.UserAdapter;
import com.handi.project2_1572006.Entity.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Handi Lieputra 1572006
 */
public class UserListFragment extends Fragment {
    private UserAdapter userAdapter;
    @BindView(R.id.rvListUser)
    RecyclerView rvListUser;
    private DatabaseReference userRef;
    private ArrayList<User> users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration did = new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation());
        rvListUser = rootView.findViewById(R.id.rvListUser);
        rvListUser.addItemDecoration(did);
        rvListUser.setLayoutManager(linearLayoutManager);
        rvListUser.setAdapter(getUserAdapter());
        populateUserData();
        return rootView;
    }

    public UserAdapter getUserAdapter() {
        if (userAdapter == null){
            userAdapter = new UserAdapter();
        }
        return userAdapter;
    }
    public void populateUserData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        users = new ArrayList<>();
        userRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user = new User(noteDataSnapshot.getValue(User.class));
                    users.add(user);
                    user.setKey(noteDataSnapshot.getKey());
                }
                getUserAdapter().setUsers(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
        getUserAdapter().setUsers(users);
    }
}
