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
import com.handi.project2_1572006.Adapter.BarangAdapter;
import com.handi.project2_1572006.Entity.Barang;
import com.handi.project2_1572006.Entity.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Handi Lieputra 1572006
 */
public class BarangListFragment extends Fragment {
    private BarangAdapter barangAdapter;
    @BindView(R.id.rvListBarang)
    RecyclerView rvListBarang;
    private DatabaseReference userRef;
    private ArrayList<Barang> barangs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barang_list, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration did = new DividerItemDecoration(getActivity(), linearLayoutManager
                .getOrientation());
        rvListBarang = rootView.findViewById(R.id.rvListBarang);
        rvListBarang.addItemDecoration(did);
        rvListBarang.setLayoutManager(linearLayoutManager);
        rvListBarang.setAdapter(getBarangAdapter());
        populateBarangData();
        return rootView;
    }

    public BarangAdapter getBarangAdapter() {
        if(barangAdapter == null)
        {
            barangAdapter = new BarangAdapter();
        }
        return barangAdapter;
    }
    public void populateBarangData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference();
        barangs = new ArrayList<>();
        userRef.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                barangs.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Barang barang = new Barang(noteDataSnapshot.getValue(Barang.class));
                    barangs.add(barang);
                    barang.setKey(noteDataSnapshot.getKey());
                }
                getBarangAdapter().setBarangs(barangs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
        getBarangAdapter().setBarangs(barangs);
    }
}
