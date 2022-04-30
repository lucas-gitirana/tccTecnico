package lucas.gitirana.fumus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.view.material.EditMaterial;
import lucas.gitirana.fumus.view.orgao.EditOrgao;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private Button btn_logout, btn_materiais, btn_sites, btn_orgaos;
    private Button btn_edit_sites, btn_edit_orgaos, btn_edit_materiais;
    private Button btn_listar_sites, btn_listar_orgaos, btn_listar_materiais;
    // TODO - GERENCIAR EXPERIÊNCIAS, MATERIAIS, SITES  E ORGAOS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.btn_logout);
        btn_materiais = findViewById(R.id.btn_materiais);
        btn_sites = findViewById(R.id.btn_sites);
        btn_orgaos = findViewById(R.id.btn_orgaos);
        btn_edit_sites = findViewById(R.id.btn_edit_sites);
        btn_edit_orgaos = findViewById(R.id.btn_edit_orgaos);
        btn_edit_materiais = findViewById(R.id.btn_edit_materiais);
        btn_listar_sites = findViewById(R.id.btn_listar_sites);
        btn_listar_orgaos = findViewById(R.id.btn_listar_orgaos);
        btn_listar_materiais = findViewById(R.id.btn_listar_materiais);

        btn_materiais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MateriaisActivity.class);
                startActivity(i);
            }
        });

        btn_sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SitesActivity.class);
                startActivity(i);
            }
        });

        btn_orgaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OrgaosActivity.class);
                startActivity(i);
            }
        });

        btn_edit_sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditSites.class);
                startActivity(i);
            }
        });

        btn_edit_orgaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditOrgao.class);
                startActivity(i);
            }
        });

        btn_edit_materiais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditMaterial.class);
                startActivity(i);
            }
        });

        btn_listar_sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaSites.class);
                startActivity(i);
            }
        });

        btn_listar_orgaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaOrgaos.class);
                startActivity(i);
            }
        });

        btn_listar_materiais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaMateriais.class);
                startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Saindo do aplicativo...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}