package lucas.gitirana.fumus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoMaterial;
import lucas.gitirana.fumus.model.Material;

public class MateriaisActivity extends AppCompatActivity {
    EditText edt_titulo_material, edt_desc_material, edt_link_material, edt_classificacao_material;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btn_registrar_material, btn_voltar;
    ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais);

        edt_titulo_material = findViewById(R.id.edt_titulo_material);
        edt_desc_material = findViewById(R.id.edt_desc_material);
        edt_link_material = findViewById(R.id.edt_link_material);
        edt_classificacao_material = findViewById(R.id.edt_classificacao_material);
        radioGroup = findViewById(R.id.radioGroup);
        btn_registrar_material = findViewById(R.id.btn_registrar_material);
        btn_voltar = findViewById(R.id.btn_voltar);
        progress_bar = findViewById(R.id.progress_bar);
        DaoMaterial daoMaterial = new DaoMaterial();

        btn_registrar_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                Material m = new Material();
                m.setTitulo(edt_titulo_material.getText().toString());
                m.setDescricao(edt_desc_material.getText().toString());
                m.setLink(edt_link_material.getText().toString());
                m.setClassificacao(edt_link_material.getText().toString());
                int radioId = radioGroup.getCheckedRadioButtonId();
                if(radioId != -1){
                    radioButton = findViewById(radioId);
                    m.setClassificacao(radioButton.getText().toString());
                }else{
                    m.setClassificacao(edt_classificacao_material.getText().toString());
                }

                Random id = new Random();
                m.setId(id.nextInt(100000));

                if(!TextUtils.isEmpty(m.getTitulo()) || !TextUtils.isEmpty(m.getDescricao()) ||
                        !TextUtils.isEmpty(m.getLink()) || !TextUtils.isEmpty(m.getClassificacao())){
                    /*m.salvar();
                    progress_bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Material cadastrado", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();*/

                    if(daoMaterial.inserir(m)){
                        Toast.makeText(getApplicationContext(), "Material cadastrado", Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro...", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(MateriaisActivity.this, "Preencha todos os campos de cadastro.", Toast.LENGTH_SHORT).show();
                }
            }

            /*private void abrirTelaPrincipal() {
                Intent intent = new Intent(MateriaisActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }*/
        });


        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MateriaisActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}