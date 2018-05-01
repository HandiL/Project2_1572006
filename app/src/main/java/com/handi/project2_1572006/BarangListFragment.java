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

import com.handi.project2_1572006.Adapter.BarangAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Handi Lieputra 1572006
 */
public class BarangListFragment extends Fragment {
    private BarangAdapter barangAdapter;
    @BindView(R.id.rvListBarang)
    RecyclerView rvListBarang;

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
        return rootView;
    }

    public BarangAdapter getBarangAdapter() {
        if(barangAdapter == null)
        {
            barangAdapter = new BarangAdapter();
        }
        return barangAdapter;
    }
}
