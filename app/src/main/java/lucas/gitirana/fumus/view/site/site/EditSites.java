package lucas.gitirana.fumus.view.site.site;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoSite;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.view.ConfiguracoesActivity;
import lucas.gitirana.fumus.view.Login;
import lucas.gitirana.fumus.view.MainActivity;
import lucas.gitirana.fumus.view.orgao.EditOrgao;

public class EditSites extends AppCompatActivity {
    EditText edt_nome_site, edt_link_site, edt_buscar_site;
    Button btn_editar_site, btn_voltar, btn_buscar, btn_apagar_site;
    LinearLayout botoesEditSite;
    CardView cardEditSite;
    DatabaseReference databaseReference;
    ProgressBar progress_bar_busca, progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sites);

        edt_nome_site = findViewById(R.id.edt_nome_site);
        edt_link_site = findViewById(R.id.edt_link_site);
        btn_editar_site = findViewById(R.id.btn_editar_site);
        btn_voltar = findViewById(R.id.btn_voltar);
        botoesEditSite = findViewById(R.id.botoesEditSite);
        cardEditSite = findViewById(R.id.cardEditSite);
        edt_buscar_site = findViewById(R.id.edt_buscar_site);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_apagar_site = findViewById(R.id.btn_apagar_site);
        progress_bar_busca = findViewById(R.id.progress_bar_busca);
        progress_bar = findViewById(R.id.progress_bar);
        DaoSite daoSite = new DaoSite();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        botoesEditSite.setVisibility(View.INVISIBLE);
        cardEditSite.setVisibility(View.INVISIBLE);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(edt_buscar_site.getText().toString())){
                    progress_bar_busca.setVisibility(View.VISIBLE);
                    int codigo = Integer.parseInt(edt_buscar_site.getText().toString());


                    if(daoSite.selecionar(codigo) != null){
                        botoesEditSite.setVisibility(View.VISIBLE);
                        cardEditSite.setVisibility(View.VISIBLE);
                        edt_nome_site.setText(daoSite.selecionar(codigo).getNome());
                        edt_link_site.setText(daoSite.selecionar(codigo).getLink());
                        progress_bar_busca.setVisibility(View.INVISIBLE);

                        btn_editar_site.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Site s = new Site();
                                s.setNome(edt_nome_site.getText().toString());
                                s.setLink(edt_link_site.getText().toString());
                                s.setId(daoSite.selecionar(codigo).getId());

                                if(!TextUtils.isEmpty(s.getNome()) && !TextUtils.isEmpty(s.getLink())){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    if(daoSite.editar(s)){
                                        Toast.makeText(EditSites.this, "O site foi editado com sucesso!", Toast.LENGTH_SHORT).show();
                                        progress_bar_busca.setVisibility(View.INVISIBLE);
                                    }else{
                                        Toast.makeText(EditSites.this, "Não foi possível editar o conteúdo.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(EditSites.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                        btn_apagar_site.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                                alerta.setTitle("Aviso");
                                alerta
                                        .setMessage("Deseja excluir esse site?")
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
                                                if(daoSite.excluir(daoSite.selecionar(codigo))){
                                                    Toast.makeText(getApplicationContext(), "Site removido", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Não foi possível remover o site.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                AlertDialog alertDialog = alerta.create();
                                alertDialog.show();
                            }
                        });

                    }else{
                        Toast.makeText(EditSites.this, "Este site não existe.", Toast.LENGTH_SHORT).show();
                    }

                    /*databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for(DataSnapshot dn : snapshot.getChildren()){
                                Site posicao = dn.getValue(Site.class);
                                if(posicao.getId() == codigo){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    edt_nome_site.setText(posicao.getNome());
                                    edt_link_site.setText(posicao.getLink());

                                    botoesEditSite.setVisibility(View.VISIBLE);
                                    cardEditSite.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Site encontrado", Toast.LENGTH_SHORT).show();

                                    btn_editar_site.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progress_bar.setVisibility(View.VISIBLE);
                                            if(!TextUtils.isEmpty(edt_nome_site.getText().toString()) && !TextUtils.isEmpty(edt_link_site.getText().toString())){
                                                posicao.setNome(edt_nome_site.getText().toString());
                                                posicao.setLink(edt_link_site.getText().toString());

                                                try{
                                                    databaseReference.child("sites").child(dn.getKey()).setValue(posicao);
                                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(EditSites.this,
                                                            "O site "+posicao.getNome()+" foi editado com sucesso!",
                                                            Toast.LENGTH_SHORT).show();
                                                }catch(Exception e){
                                                    Toast.makeText(getApplicationContext(),
                                                            "Ocorreu um erro ao editar o site.",
                                                            Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }


                                            }else{
                                                Toast.makeText(EditSites.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    btn_apagar_site.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(EditSites.this);
                                            alerta.setTitle("Aviso");
                                            alerta
                                                    .setMessage("Deseja excluir este site do aplicativo?")
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
                                                            try {
                                                                databaseReference.child("sites").child(dn.getKey()).removeValue();
                                                                Toast.makeText(getApplicationContext(), "Site removido...",
                                                                        Toast.LENGTH_SHORT).show();
                                                                botoesEditSite.setVisibility(View.INVISIBLE);
                                                                cardEditSite.setVisibility(View.INVISIBLE);
                                                            }catch(Exception e){
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Ocorreu um erro ao excluir o site.", Toast.LENGTH_SHORT).show();
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                            AlertDialog alertDialog = alerta.create();
                                            alertDialog.show();
                                        }
                                    });
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });*/

                }else{
                    Toast.makeText(EditSites.this, "Preencha o campo solicitado.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditSites.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}