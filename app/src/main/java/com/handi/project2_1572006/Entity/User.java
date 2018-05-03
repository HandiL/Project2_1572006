package com.handi.project2_1572006.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Handi Lieputra 1572006
 */
public class User implements Parcelable{
    private int admin;
    private String alamatUser;
    private String idUser;
    private String namaUser;
    private String noTelpUser;
    private String password;
    private String username;
    private String key;

    public User() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(User user) {
        admin = user.getAdmin();
        alamatUser = user.getAlamatUser();
        idUser = user.getIdUser();
        namaUser = user.getNamaUser();
        noTelpUser = user.getNoTelpUser();
        password = user.getPassword();
        username = user.getUsername();
    }
    protected User(Parcel in) {
        admin = in.readInt();
        alamatUser = in.readString();
        idUser = in.readString();
        namaUser = in.readString();
        noTelpUser = in.readString();
        password = in.readString();
        username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getAlamatUser() {
        return alamatUser;
    }

    public void setAlamatUser(String alamatUser) {
        this.alamatUser = alamatUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNoTelpUser() {
        return noTelpUser;
    }

    public void setNoTelpUser(String noTelpUser) {
        this.noTelpUser = noTelpUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(admin);
        dest.writeString(alamatUser);
        dest.writeString(idUser);
        dest.writeString(namaUser);
        dest.writeString(noTelpUser);
        dest.writeString(password);
        dest.writeString(username);
    }
}
