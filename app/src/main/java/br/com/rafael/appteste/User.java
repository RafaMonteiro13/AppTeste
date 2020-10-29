package br.com.rafael.appteste;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String userme;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String userme, String email){
        this.userme=userme;
        this.email=email;
    }
}
