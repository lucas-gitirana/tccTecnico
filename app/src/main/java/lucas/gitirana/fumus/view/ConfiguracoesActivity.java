package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoVisitante;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.model.Usuario;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class ConfiguracoesActivity extends AppCompatActivity {
    private EditText edt_nome_register;
    private EditText edt_cidade_register;
    private EditText edt_estado_register;
    private EditText edt_email_register;
    private EditText edt_senha_register;
    private EditText edt_confirmar_senha_register;
    private CheckBox ckb_mostrar_senha_register;
    private Button btn_editar, btn_excluir, btn_voltar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressBar login_progress_bar_register;
    private FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        edt_nome_register = findViewById(R.id.edt_nome_register);
        edt_cidade_register = findViewById(R.id.edt_cidade_register);
        edt_estado_register = findViewById(R.id.edt_estado_register);
        edt_email_register = findViewById(R.id.edt_email_register);
        edt_senha_register = findViewById(R.id.edt_senha_register);
        edt_confirmar_senha_register = findViewById(R.id.edt_confirmar_senha_register);
        ckb_mostrar_senha_register = findViewById(R.id.ckb_mostrar_senha_register);
        login_progress_bar_register = findViewById(R.id.login_progress_bar_register);
        radioGroup = findViewById(R.id.radioGroup);
        btn_editar = findViewById(R.id.btn_editar);
        btn_excluir = findViewById(R.id.btn_excluir);
        btn_voltar = findViewById(R.id.btn_voltar);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DaoVisitante daoVisitante = new DaoVisitante();

        edt_nome_register.setText(daoVisitante.selecionar().getNome());
        edt_cidade_register.setText(daoVisitante.selecionar().getCidade());
        edt_estado_register.setText(daoVisitante.selecionar().getEstado());
        edt_email_register.setText(daoVisitante.selecionar().getEmail());
        edt_senha_register.setText(daoVisitante.selecionar().getSenha());
        edt_confirmar_senha_register.setText(daoVisitante.selecionar().getSenha());

        /*databaseReference.child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u = snapshot.getValue(Usuario.class);
                edt_nome_register.setText(u.getNome());
                edt_cidade_register.setText(u.getCidade());
                edt_estado_register.setText(u.getEstado());
                edt_email_register.setText(u.getEmail());
                edt_senha_register.setText(u.getSenha());
                edt_confirmar_senha_register.setText(u.getSenha());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

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

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                u.setNome(edt_nome_register.getText().toString());
                u.setCidade(edt_cidade_register.getText().toString());
                u.setEstado(edt_estado_register.getText().toString());
                u.setEmail(edt_email_register.getText().toString());
                String senha = edt_senha_register.getText().toString();
                String confirmarSenha = edt_confirmar_senha_register.getText().toString();

                int radioId = radioGroup.getCheckedRadioButtonId();
                if(radioId != -1){
                    radioButton = findViewById(radioId);
                    u.setCondicao(radioButton.getText().toString());
                }


                if (!TextUtils.isEmpty(u.getNome()) && !TextUtils.isEmpty(u.getCidade()) &&
                        !TextUtils.isEmpty(u.getEstado()) && !TextUtils.isEmpty(u.getEmail()) &&
                        !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(confirmarSenha) &&
                        !TextUtils.isEmpty(u.getCondicao())) {
                    if (senha.equals(confirmarSenha)) {
                        login_progress_bar_register.setVisibility(View.VISIBLE);
                        if(daoVisitante.editar(u)){
                            Toast.makeText(ConfiguracoesActivity.this, "As informações foram alteradas.", Toast.LENGTH_SHORT).show();
                            login_progress_bar_register.setVisibility(View.INVISIBLE);
                        }else{
                            Toast.makeText(ConfiguracoesActivity.this, "Não foi possível editar perfil.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ConfiguracoesActivity.this, "As senhas informadas são diferentes.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(ConfiguracoesActivity.this, "Preencha todos os campos de edição.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracoesActivity.this);
                alerta.setTitle("Aviso");
                alerta
                        .setMessage("Deseja excluir sua conta?")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Ação cancelada.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(daoVisitante.excluir()){
                                    Toast.makeText(getApplicationContext(), "Usuário removido...", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ConfiguracoesActivity.this, Login.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Não foi possível remover usuário.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                AlertDialog alertDialog = alerta.create();
                alertDialog.show();
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeUsuarioActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}