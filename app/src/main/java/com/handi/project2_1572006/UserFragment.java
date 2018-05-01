package com.handi.project2_1572006;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handi.project2_1572006.Entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Handi Lieputra 1572006
 *
 */
public class UserFragment extends Fragment {
    @BindView(R.id.txtNama)
    EditText txtNama;

    @BindView(R.id.txtUserName)
    EditText txtUserName;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.txtRePass)
    EditText txtRePass;

    @BindView(R.id.txtNoTelp)
    EditText txtNoTelp;

    @BindView(R.id.txtAlamat)
    EditText txtAlamat;

    @BindView(R.id.rbRole)
    RadioGroup rbRole;

    @BindView(R.id.rbAdmin)
    RadioButton rbAdmin;

    @BindView(R.id.rbKasir)
    RadioButton rbKasir;

    public User selectedUser;
    final static String ARG_User = "parcel_user";
    private DatabaseReference db;
    boolean addData,updateData,deleteData;
    int id;
    private  MainActivity mainActivity;

    public UserFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments()!=null && getArguments().containsKey(getResources().getString(R.string.parcel_user))){
            User user = getArguments().getParcelable(getResources().getString(R.string.parcel_user));
            txtAlamat.setText(user.getAlamatUser());
            txtPassword.setText(user.getPassword());
            txtNama.setText(user.getNamaUser());
            txtNoTelp.setText(user.getNoTelpUser());
            txtUserName.setText(user.getUsername());
            txtRePass.setText(user.getPassword());

            if(user.getAdmin()==0)
            {
                rbKasir.setChecked(true);
            }
            else{
                rbAdmin.setChecked(true);
            }
            selectedUser = user;

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        addData=false;
        updateData = false;
        deleteData = false;
        id=0;
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @OnClick(R.id.btnAdd)
    public void addUser(){
        if(!TextUtils.isEmpty(txtUserName.getText()) && !TextUtils.isEmpty(txtAlamat.getText()) && !TextUtils.isEmpty(txtNama.getText())
                && !TextUtils.isEmpty(txtPassword.getText()) && !TextUtils.isEmpty(txtNoTelp.getText()) && !TextUtils.isEmpty(txtRePass.getText()))
        {
            if(txtPassword.getText().toString().equals(txtRePass.getText().toString()))
            {
                db.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            int cekId = Integer.valueOf(noteDataSnapshot.getValue(User.class).getIdUser()
                                    .substring(2,3));
                            id = cekId;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });

                User user = new User();
                user.setIdUser("00"+String.valueOf((id+1)));
                if(rbKasir.isChecked())
                {
                    user.setAdmin(0);
                }
                else{
                    user.setAdmin(1);
                }
                user.setAlamatUser(txtAlamat.getText().toString());
                user.setNamaUser(txtNama.getText().toString());
                user.setNoTelpUser(txtNoTelp.getText().toString());
                user.setUsername(txtUserName.getText().toString());
                user.setPassword(txtPassword.getText().toString());
                db.child("User").push().setValue(user).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        txtAlamat.setText("");
                        txtNama.setText("");
                        txtPassword.setText("");
                        txtNoTelp.setText("");
                        txtUserName.setText("");
                        txtRePass.setText("");
                        addData = true;
                        Toast.makeText(getActivity(), "Add User Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(), "Password and RePassword Must Be Same ", Toast.LENGTH_SHORT).show();
            }

        }
    }

    ////
    @OnClick(R.id.btnUpdate)
    public void updateUser(){
        if(!TextUtils.isEmpty(txtUserName.getText()) && !TextUtils.isEmpty(txtAlamat.getText()) && !TextUtils.isEmpty(txtNama.getText())
                && !TextUtils.isEmpty(txtPassword.getText()) && !TextUtils.isEmpty(txtNoTelp.getText()) && !TextUtils.isEmpty(txtRePass.getText()))
        {
            selectedUser.setAlamatUser(txtAlamat.getText().toString());
            selectedUser.setNamaUser(txtNama.getText().toString());
            selectedUser.setPassword(txtPassword.getText().toString());
            selectedUser.setNoTelpUser(txtNoTelp.getText().toString());
            selectedUser.setUsername(txtUserName.getText().toString());
            if(rbKasir.isChecked())
            {
                selectedUser.setAdmin(0);
            }
            else{
                selectedUser.setAdmin(1);
            }
            db.child("User").child(selectedUser.getIdUser()).setValue(selectedUser).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    txtNama.setText("");
                    txtAlamat.setText("");
                    txtPassword.setText("");
                    txtUserName.setText("");
                    txtNoTelp.setText("");
                    txtRePass.setText("");
                    updateData = true;
                    Toast.makeText(getActivity(), "Update User Success", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    @OnClick(R.id.btnDelete)
    public void deleteUser(){
        if(selectedUser!=null)
        {
            db.child("User").child(selectedUser.getIdUser()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    txtNama.setText("");
                    txtAlamat.setText("");
                    txtPassword.setText("");
                    txtUserName.setText("");
                    txtNoTelp.setText("");
                    txtRePass.setText("");
                    deleteData=true;
                    Toast.makeText(getActivity(), "Delete User Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }






    ////
}
