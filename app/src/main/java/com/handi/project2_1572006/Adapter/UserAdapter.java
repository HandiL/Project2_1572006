package com.handi.project2_1572006.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handi.project2_1572006.Entity.User;
import com.handi.project2_1572006.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private ArrayList<User> users;
    private UserDataListener userDataClickedListener;

    public void setUserDataClickedListener(UserDataListener userDataClickedListener) {
        this.userDataClickedListener = userDataClickedListener;
    }

    public ArrayList<User> getUsers() {
        if(users==null)
        {
            users=new ArrayList<>();
        }
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        getUsers().clear();
        getUsers().addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = getUsers().get(position);
        String id = user.getIdUser();
        String username = user.getUsername();
        String alamat = user.getAlamatUser();
        String name = user.getNamaUser();
        int admin = user.getAdmin();
        String role = "";
        if(admin == 0){
            role = "Kasir";
        }else{
            role = "Admin";
        }

        holder.tvName.setText(name);
        holder.tvRole.setText(role);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userDataClickedListener!=null)
                {
                    userDataClickedListener.onUserClicked(user);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvRole)
        TextView tvRole;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface UserDataListener{
        void onUserClicked(User user);
    }
}
