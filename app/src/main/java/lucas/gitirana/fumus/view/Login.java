package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;

public class Login extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_senha;
    private CheckBox ckb_mostrar_senha;
    private Button btn_login;
    private Button btn_registrar;
    private Button btn_login_adm;
    private ProgressBar login_progress_bar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ValueEventListener valueEventListener;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        ckb_mostrar_senha = findViewById(R.id.ckb_mostrar_senha);
        btn_login = findViewById(R.id.btn_login);
        btn_registrar = findViewById(R.id.btn_registrar);
        btn_login_adm = findViewById(R.id.btn_login_adm);
        login_progress_bar = findViewById(R.id.login_progress_bar);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(senha)){
                    login_progress_bar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email,senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                abrirTelaPrincipal();
                                Toast.makeText(getApplicationContext(), "Bem-vindo(a)!", Toast.LENGTH_SHORT)
                                        .show();
                            }else{
                                String erro = task.getException().getMessage();
                                Toast.makeText(Login.this, "Não foi possível autenticar-se no sistema - "+erro, Toast.LENGTH_SHORT).show();
                                login_progress_bar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        ckb_mostrar_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edt_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    edt_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        btn_login_adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, LoginAdministrador.class);
                startActivity(i);
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Login.this, HomeUsuarioActivity.class);
        startActivity(intent);
        finish();
    }
}