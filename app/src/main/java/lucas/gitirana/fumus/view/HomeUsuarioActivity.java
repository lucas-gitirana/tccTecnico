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

public class HomeUsuarioActivity extends AppCompatActivity {
    Button btn_lista_sites, btn_lista_orgaos, btn_lista_materiais, btn_logout, btn_experiencias,
            btn_configs, btn_lista_favoritos;
    FirebaseAuth mAuth;

    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(HomeUsuarioActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_usuario);
        mAuth = FirebaseAuth.getInstance();
        btn_lista_sites = findViewById(R.id.btn_lista_sites);
        btn_lista_orgaos = findViewById(R.id.btn_lista_orgaos);
        btn_lista_materiais = findViewById(R.id.btn_lista_materiais);
        btn_logout = findViewById(R.id.btn_logout);
        btn_experiencias = findViewById(R.id.btn_experiencias);
        btn_configs = findViewById(R.id.btn_configs);
        btn_lista_favoritos = findViewById(R.id.btn_lista_favoritos);

        btn_lista_sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuarioActivity.this, ListaSites.class);
                startActivity(i);
            }
        });

        btn_lista_orgaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuarioActivity.this, ListaOrgaos.class);
                startActivity(i);
            }
        });

        btn_lista_materiais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuarioActivity.this, ListaMateriais.class);
                startActivity(i);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(HomeUsuarioActivity.this, Login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Saindo do aplicativo...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btn_experiencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeUsuarioActivity.this, ExperienciasActivity.class);
                startActivity(intent);
            }
        });

        btn_configs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuarioActivity.this, ConfiguracoesActivity.class);
                startActivity(i);
            }
        });

        btn_lista_favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuarioActivity.this, ListaFavoritos.class);
                startActivity(i);
            }
        });
    }
}