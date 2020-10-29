package br.com.rafael.appteste;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        mAuth = FirebaseAuth.getInstance();

        Button cadastrar = findViewById(R.id.btnCadastrar1);
        Button cancelar = findViewById(R.id.btnCancel1);
        final EditText nome = findViewById(R.id.edNome);
        final EditText email = findViewById(R.id.edEmail);
        final EditText senha = findViewById(R.id.edSenha1);
        final EditText confSenha = findViewById(R.id.edConfSenha);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString() != "") {
                    Log.d("Btn: ", "email");
                    if (senha.getText().toString().equals(confSenha.getText().toString())) {
                        if (senha.getText().length() > 5) {
                            Log.d("Btn: ", "senha");
                            criarUser(email.getText().toString(), senha.getText().toString(), nome.getText().toString());
                        } else {
                            Toast.makeText(CadastrarActivity.this, "Senha com minimo de 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastrarActivity.this, "Senhas não conferem.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastrarActivity.this, "E-Mail vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome.setText("");
                email.setText("");
                senha.setText("");
                confSenha.setText("");
                getCurrentFocus().clearFocus();
            }
        });
    }
    private void criarUser(final String email, String password, final String nome) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login: ", "createUserWithEmail:success");
                            FirebaseUser userId = mAuth.getCurrentUser();
                            User user = new User(nome, email);
                            gravaNome(userId.getUid(), user);
                            entrada();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login: ", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastrarActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void gravaNome(String userId, User user){
        myRef.child(userId).setValue(user);
    }
    private void entrada() {
        Intent intent = new Intent(this, DentroActivity.class);
        startActivity(intent);
    }
}