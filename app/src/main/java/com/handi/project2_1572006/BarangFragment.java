package com.handi.project2_1572006;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handi.project2_1572006.Entity.Barang;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Handi Lieputra 1572006
 */
public class BarangFragment extends Fragment {
    @BindView(R.id.txtNamaBarang)
    EditText txtNamaBarang;

    @BindView(R.id.txtHargaJual)
    EditText txtHargaJual;

    @BindView(R.id.txtHargaBeli)
    EditText txtHargaBeli;

    @BindView(R.id.txtStock)
    EditText txtStock;

    @BindView(R.id.btnDelete)
    Button btnDelete;

    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    @BindView(R.id.btnAdd)
    Button btnAdd;

    final static String ARG_Barang = "parcel_barang";
    private DatabaseReference db;
    int id;
    public Barang selectedBarang;
    public BarangFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_barang,container, false);
        ButterKnife.bind(this, view);
        addMode();
        return view;
    }
    public void updateMode(){
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }
    public void addMode(){
        btnAdd.setEnabled(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey(getResources().getString(R.string
                .parcel_barang))){
            Barang barang = getArguments().getParcelable(getResources().getString(R.string
                    .parcel_barang));
            txtNamaBarang.setText(barang.getNamaBarang());
            txtHargaJual.setText(String.valueOf(barang.getHargaJual()));
            txtHargaBeli.setText(String.valueOf(barang.getHargaBeli()));
            txtStock.setText(String.valueOf(barang.getStock()));
            selectedBarang=barang;
            updateMode();
        }
    }
    @OnClick(R.id.btnAdd)
    public void addBarang(){
        id=0;
        if(!TextUtils.isEmpty(txtHargaBeli.getText()) && !TextUtils.isEmpty(txtHargaJual.getText()) && !TextUtils.isEmpty(txtNamaBarang.getText())
                && !TextUtils.isEmpty(txtStock.getText()))
        {
                db.child("Barang").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            int cekId = Integer.valueOf(noteDataSnapshot.getValue(Barang.class).getIdBarang()
                                    .substring(2,3));
                            id = cekId;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });

                Barang barang = new Barang();
                barang.setIdBarang("00"+String.valueOf((id+1)));
                barang.setHargaBeli(Integer.valueOf(txtHargaBeli.getText().toString()));
                barang.setHargaJual(Integer.valueOf(txtHargaJual.getText().toString()));
                barang.setNamaBarang(txtNamaBarang.getText().toString());
                barang.setStock(Integer.valueOf(txtStock.getText().toString()));
                db.child("Barang").push().setValue(barang).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        txtHargaBeli.setText("");
                        txtHargaJual.setText("");
                        txtNamaBarang.setText("");
                        txtStock.setText("");
                        Toast.makeText(getActivity(), "Add Barang Success", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    @OnClick(R.id.btnUpdate)
    public void updateBarang(){
        if(!TextUtils.isEmpty(txtHargaBeli.getText()) && !TextUtils.isEmpty(txtHargaJual.getText()) && !TextUtils.isEmpty(txtNamaBarang.getText())
                && !TextUtils.isEmpty(txtStock.getText()))
        {
            selectedBarang.setStock(Integer.valueOf(txtStock.getText().toString()));
            selectedBarang.setNamaBarang(txtNamaBarang.getText().toString());
            selectedBarang.setHargaJual(Integer.valueOf(txtHargaJual.getText().toString()));
            selectedBarang.setHargaBeli(Integer.valueOf(txtHargaBeli.getText().toString()));
           db.child("Barang").child(selectedBarang.getKey()).setValue(selectedBarang).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   txtHargaBeli.setText("");
                   txtHargaJual.setText("");
                   txtNamaBarang.setText("");
                   txtStock.setText("");
                   Toast.makeText(getActivity(), "Update Barang Success", Toast.LENGTH_SHORT).show();
               }
           });
            addMode();
        }

    }
    @OnClick(R.id.btnDelete)
    public void deleteBarang(){
        if(selectedBarang!=null)
        {
            db.child("Barang").child(selectedBarang.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    txtHargaBeli.setText("");
                    txtHargaJual.setText("");
                    txtNamaBarang.setText("");
                    txtStock.setText("");
                    Toast.makeText(getActivity(), "Delete Barang Success", Toast.LENGTH_SHORT).show();
                }
            });
            addMode();
        }
    }
}
