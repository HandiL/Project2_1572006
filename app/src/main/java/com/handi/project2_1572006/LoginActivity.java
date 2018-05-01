package com.handi.project2_1572006;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.handi.project2_1572006.Entity.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Handi Lieputra 1572006
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.txtUserName)
    TextView txtUserName;

    @BindView(R.id.txtPassword)
    TextView txtPassword;

    private DatabaseReference dbRf;
    private ArrayList<User> users;

    public ArrayList<User> getUsers() {
        if(users == null)
        {
            users= new ArrayList<>();
        }
        return users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        dbRf = firebaseDatabase.getReference();
    }

    @OnClick(R.id.btnLogin)
    public void btnLoginAct(){
        if(!TextUtils.isEmpty(txtUserName.getText().toString().trim()) && !TextUtils.isEmpty(txtPassword.getText().toString().trim()))
        {

            dbRf.child("User").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean hasil = false;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        if(ds.getValue(User.class).getUsername().toString().equals(txtUserName.getText().toString().trim()) && ds.getValue(User.class).getPassword().toString().equals(txtPassword.getText().toString().trim()) ){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.finish();
                            LoginActivity.this.startActivity(intent);
                            hasil=true;
                            break;
                        }
                    }
                    if(hasil==false)
                    {
                           Toast.makeText(LoginActivity.this,"Invalid Username and Password",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else {
            Toast.makeText(this,"Invalid Username and Password", Toast.LENGTH_SHORT).show();
        }

    }
}
