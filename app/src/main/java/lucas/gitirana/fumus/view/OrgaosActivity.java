package lucas.gitirana.fumus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.DaoOrgao;
import lucas.gitirana.fumus.model.Orgao;

public class OrgaosActivity extends AppCompatActivity {
    EditText edt_nome_orgao, edt_estado_orgao, edt_link_orgao;
    Button btn_registrar_material, btn_voltar;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgaos);
        edt_nome_orgao = findViewById(R.id.edt_nome_orgao);
        edt_estado_orgao = findViewById(R.id.edt_estado_orgao);
        edt_link_orgao = findViewById(R.id.edt_link_orgao);
        btn_registrar_material = findViewById(R.id.btn_registrar_material);
        btn_voltar = findViewById(R.id.btn_voltar);
        progress_bar = findViewById(R.id.progress_bar);
        DaoOrgao daoOrgao = new DaoOrgao();

        btn_registrar_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                Orgao o = new Orgao();
                o.setNome(edt_nome_orgao.getText().toString());
                o.setEstado(edt_estado_orgao.getText().toString());
                o.setLink(edt_link_orgao.getText().toString());
                Random id = new Random();
                o.setId(id.nextInt(100000));

                if(!TextUtils.isEmpty(o.getNome()) && !TextUtils.isEmpty(o.getEstado())
                && !TextUtils.isEmpty(o.getLink())){
                    /*o.salvar();
                    progress_bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Órgão cadastrado", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();*/

                    if(daoOrgao.inserir(o)){
                        Toast.makeText(getApplicationContext(), "Órgão cadastrado", Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro...", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(OrgaosActivity.this, "Preencha todos os campos de cadastro.", Toast.LENGTH_SHORT).show();
                }
            }

            /*private void abrirTelaPrincipal() {
                Intent intent = new Intent(OrgaosActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }*/
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrgaosActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}