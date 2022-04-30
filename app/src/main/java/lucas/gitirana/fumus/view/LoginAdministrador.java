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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoAdministrador;
import lucas.gitirana.fumus.model.Administrador;

public class LoginAdministrador extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_entrar, btn_voltar;
    private ProgressBar pb_entrar;
    private CheckBox ckb_mostrar_senha;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_administrador);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
        btn_voltar = findViewById(R.id.btn_voltar);
        pb_entrar = findViewById(R.id.pb_entrar);
        ckb_mostrar_senha = findViewById(R.id.ckb_mostrar_senha);
        DaoAdministrador daoAdministrador = new DaoAdministrador();
        mAuth = FirebaseAuth.getInstance();

        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String senha = edt_senha.getText().toString();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(senha)){
                    pb_entrar.setVisibility(View.VISIBLE);
                    for(Administrador adm : daoAdministrador.listar()){
                        if(adm.getSenha() == senha){
                            abrirTelaAdm();
                        }
                    }
                    /*mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                abrirTelaAdm();
                                Toast.makeText(getApplicationContext(), "Bem-vindo(a), administrador(a)!", Toast.LENGTH_SHORT).show();
                            }else{
                                String erro = task.getException().getMessage();
                                Toast.makeText(LoginAdministrador.this, ""+erro, Toast.LENGTH_SHORT).show();
                                pb_entrar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });*/
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

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginAdministrador.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void abrirTelaAdm() {
        Intent i = new Intent(LoginAdministrador.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}