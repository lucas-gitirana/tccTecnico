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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoVisitante;
import lucas.gitirana.fumus.model.Administrador;
import lucas.gitirana.fumus.model.Usuario;

public class Register extends AppCompatActivity {
    private EditText edt_nome_register;
    private EditText edt_cidade_register;
    private EditText edt_estado_register;
    private EditText edt_email_register;
    private EditText edt_senha_register;
    private EditText edt_confirmar_senha_register;
    private CheckBox ckb_mostrar_senha_register;
    private Button btn_registrar_register;
    private Button btn_login_register;
    private ProgressBar login_progress_bar_register;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private FirebaseAuth mAuth;
    private DaoVisitante daoVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        edt_nome_register = findViewById(R.id.edt_nome_register);
        edt_cidade_register = findViewById(R.id.edt_cidade_register);
        edt_estado_register = findViewById(R.id.edt_estado_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_senha_register = findViewById(R.id.edt_senha_register);
        edt_confirmar_senha_register = findViewById(R.id.edt_confirmar_senha_register);
        ckb_mostrar_senha_register = findViewById(R.id.ckb_mostrar_senha_register);
        btn_registrar_register = findViewById(R.id.btn_registrar_register);
        btn_login_register = findViewById(R.id.btn_login_register);
        login_progress_bar_register = findViewById(R.id.login_progress_bar_register);
        radioGroup = findViewById(R.id.radioGroup);
        daoVisitante = new DaoVisitante();


        ckb_mostrar_senha_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_senha_register.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_confirmar_senha_register.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edt_senha_register.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirmar_senha_register.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_registrar_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OBJETO USUARIO
                Usuario u = new Usuario();

                // SENHA
                String senha = edt_senha_register.getText().toString();
                String confirmarSenha = edt_confirmar_senha_register.getText().toString();

                // CONDIÇÃO
                int radioId = radioGroup.getCheckedRadioButtonId();
                if(radioId != -1){
                    radioButton = findViewById(radioId);
                    u.setCondicao(radioButton.getText().toString());
                }

                // VERIFICA CAMPOS
                if (!TextUtils.isEmpty(u.getNome()) || !TextUtils.isEmpty(u.getCidade()) ||
                        !TextUtils.isEmpty(u.getEstado()) || !TextUtils.isEmpty(u.getEmail()) ||
                        !TextUtils.isEmpty(senha) || !TextUtils.isEmpty(confirmarSenha) ||
                        !TextUtils.isEmpty(u.getCondicao())) {

                    // VERIFICA SENHA
                    if (senha.equals(confirmarSenha)) {

                        // ATRIBUI INFORMAÇÕES
                        login_progress_bar_register.setVisibility(View.VISIBLE);
                        u.setNome(edt_nome_register.getText().toString());
                        u.setCidade(edt_cidade_register.getText().toString());
                        u.setEstado(edt_estado_register.getText().toString());
                        u.setEmail(edt_email_register.getText().toString());
                        u.setSenha(edt_confirmar_senha_register.getText().toString());

                        // CRIA USUÁRIO
                        mAuth.createUserWithEmailAndPassword(u.getEmail(), senha)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // GRAVA USUÁRIO NO BANCO
                                    if (daoVisitante.inserir(u)){
                                        Toast.makeText(getApplicationContext(), "Usuário cadastrado", Toast.LENGTH_SHORT).show();
                                        abrirTelaPrincipal();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String erro = "";
                                    try{
                                        throw task.getException();
                                    }catch(FirebaseAuthWeakPasswordException e){
                                        erro = "A senha deve conter, no mínimo, seis caracteres.";
                                    }catch(FirebaseAuthInvalidCredentialsException e){
                                        erro = "E-mail inválido.";
                                    }catch(FirebaseAuthUserCollisionException e){
                                        erro = "Este e-mail já está cadastrado.";
                                    }catch(Exception e){
                                        erro = "Erro ao efetuar cadastro.";
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(Register.this, "" + erro, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "As senhas informadas são diferentes.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(Register.this, "Preencha todos os campos de cadastro.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}