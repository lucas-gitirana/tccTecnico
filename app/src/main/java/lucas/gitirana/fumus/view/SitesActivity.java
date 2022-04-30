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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthEmailException;

import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.DaoSite;
import lucas.gitirana.fumus.model.Site;

public class SitesActivity extends AppCompatActivity {
    EditText edt_nome_site, edt_link_material;
    Button btn_registrar_material, btn_voltar;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        edt_nome_site = findViewById(R.id.edt_nome_site);
        edt_link_material = findViewById(R.id.edt_link_material);
        btn_registrar_material = findViewById(R.id.btn_registrar_material);
        btn_voltar = findViewById(R.id.btn_voltar);
        progress_bar = findViewById(R.id.progress_bar);
        DaoSite daoSite = new DaoSite();

        btn_registrar_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                Site s = new Site();
                s.setNome(edt_nome_site.getText().toString());
                s.setLink(edt_link_material.getText().toString());
                Random id = new Random();
                s.setId(id.nextInt(100000));
                if(!TextUtils.isEmpty(s.getNome()) || !TextUtils.isEmpty(s.getLink()) ){
                    /*try{

                        progress_bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Site cadastrado", Toast.LENGTH_SHORT).show();
                        abrirTelaPrincipal();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro...", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }*/

                    if(daoSite.inserir(s)){
                        Toast.makeText(getApplicationContext(), "Site cadastrado", Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro...", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SitesActivity.this, "Preencha todos os campos de cadastro.", Toast.LENGTH_SHORT).show();
                }
            }

            /*private void abrirTelaPrincipal() {
                Intent intent = new Intent(SitesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }*/

        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SitesActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}