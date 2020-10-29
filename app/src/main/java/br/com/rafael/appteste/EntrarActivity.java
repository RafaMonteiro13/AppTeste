package br.com.rafael.appteste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class EntrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        mAuth = FirebaseAuth.getInstance();

        Button btnEntrar = findViewById(R.id.btnEntEntrar);
        Button btnCancel = findViewById(R.id.btnEntCancel);
        final EditText edEmail = findViewById(R.id.edEntEmail);
        final EditText edSenha = findViewById(R.id.edEntSenha);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar(edEmail.getText().toString(),edSenha.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edEmail.setText("");
                edSenha.setText("");
                getCurrentFocus().clearFocus();
            }
        });


    }

    private void entrar(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Entrar: ", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            entrada();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Entrar: ", "signInWithEmail:failure", task.getException());
                            Toast.makeText(EntrarActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void entrada(){
        Intent intent = new Intent(this,DentroActivity.class);
        startActivity(intent);
    }
}