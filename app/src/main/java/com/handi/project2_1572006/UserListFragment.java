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

import com.handi.project2_1572006.Adapter.UserAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Handi Lieputra 1572006
 */
public class UserListFragment extends Fragment {
    private UserAdapter userAdapter;
    @BindView(R.id.rvListUser)
    RecyclerView rvListUser;

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
        return rootView;
    }

    public UserAdapter getUserAdapter() {
        if (userAdapter == null){
            userAdapter = new UserAdapter();
        }
        return userAdapter;
    }
}
