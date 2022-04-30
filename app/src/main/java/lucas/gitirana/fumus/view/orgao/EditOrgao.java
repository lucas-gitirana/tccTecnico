package lucas.gitirana.fumus.view.orgao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.DaoOrgao;
import lucas.gitirana.fumus.model.Material;
import lucas.gitirana.fumus.model.Orgao;
import lucas.gitirana.fumus.view.MainActivity;
import lucas.gitirana.fumus.view.OrgaosActivity;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class EditOrgao extends AppCompatActivity {
    EditText edt_id_orgao, edt_nome_orgao, edt_estado_orgao, edt_link_orgao;
    Button btn_buscar, btn_editar_orgao, btn_voltar, btn_apagar_orgao;
    ProgressBar progress_bar, progress_bar_busca;
    LinearLayout componentesEditOrgao;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_orgao);

        edt_id_orgao = findViewById(R.id.edt_id_orgao);
        edt_nome_orgao = findViewById(R.id.edt_nome_orgao);
        edt_estado_orgao = findViewById(R.id.edt_estado_orgao);
        edt_link_orgao = findViewById(R.id.edt_link_orgao);
        btn_buscar = findViewById(R.id.btn_buscar_orgao);
        btn_editar_orgao = findViewById(R.id.btn_editar_orgao);
        btn_apagar_orgao = findViewById(R.id.btn_apagar_orgao);
        btn_voltar = findViewById(R.id.btn_voltar);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar_busca = findViewById(R.id.progress_bar_busca);
        componentesEditOrgao = findViewById(R.id.componentesEditOrgao);
        DaoOrgao daoOrgao = new DaoOrgao();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        componentesEditOrgao.setVisibility(View.INVISIBLE);

        /*btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!edt_id_orgao.getText().toString().isEmpty()){
                    int codigo = Integer.parseInt(edt_id_orgao.getText().toString());


                    databaseReference.child("orgaos").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                Orgao posicao = ds.getValue(Orgao.class);
                                if(codigo == posicao.getId()){
                                    edt_nome_orgao.setText(posicao.getNome());
                                    edt_estado_orgao.setText(posicao.getEstado());
                                    edt_link_orgao.setText(posicao.getLink());

                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    componentesEditOrgao.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Órgão encontrado", Toast.LENGTH_SHORT).show();

                                    btn_editar_orgao.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progress_bar.setVisibility(View.VISIBLE);
                                            if(!TextUtils.isEmpty(edt_nome_orgao.getText().toString()) && !TextUtils.isEmpty(edt_estado_orgao.getText().toString())
                                                    && !TextUtils.isEmpty(edt_link_orgao.getText().toString())){
                                                posicao.setNome(edt_nome_orgao.getText().toString());
                                                posicao.setEstado(edt_estado_orgao.getText().toString());
                                                posicao.setLink(edt_link_orgao.getText().toString());

                                                databaseReference.child("orgaos").child(ds.getKey()).setValue(posicao);
                                                progress_bar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(EditOrgao.this, "O Órgão "+posicao.getNome()+" foi editado com sucesso!", Toast.LENGTH_SHORT).show();



                                            }else{
                                                Toast.makeText(EditOrgao.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    btn_apagar_orgao.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(EditOrgao.this);
                                            alerta.setTitle("Aviso");
                                            alerta
                                                    .setMessage("Deseja excluir este órgão do aplicativo?")
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
                                                            databaseReference.child("orgaos").child(ds.getKey()).removeValue();
                                                            Toast.makeText(getApplicationContext(), "Órgão removido...", Toast.LENGTH_SHORT).show();
                                                            componentesEditOrgao.setVisibility(View.INVISIBLE);
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
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }else{
                    Toast.makeText(EditOrgao.this, "Preencha o campo solicitado.", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(edt_id_orgao.getText().toString())){
                    progress_bar_busca.setVisibility(View.VISIBLE);
                    int codigo = Integer.parseInt(edt_id_orgao.getText().toString());


                    if(daoOrgao.selecionar(codigo) != null){
                        componentesEditOrgao.setVisibility(View.VISIBLE);
                        edt_nome_orgao.setText(daoOrgao.selecionar(codigo).getNome());
                        edt_link_orgao.setText(daoOrgao.selecionar(codigo).getLink());
                        edt_estado_orgao.setText(daoOrgao.selecionar(codigo).getEstado());
                        progress_bar_busca.setVisibility(View.INVISIBLE);

                        btn_editar_orgao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Orgao o = new Orgao();
                                o.setNome(edt_nome_orgao.getText().toString());
                                o.setEstado(edt_estado_orgao.getText().toString());
                                o.setLink(edt_link_orgao.getText().toString());
                                o.setId(daoOrgao.selecionar(codigo).getId());

                                if(!TextUtils.isEmpty(o.getNome()) && !TextUtils.isEmpty(o.getEstado())
                                        && !TextUtils.isEmpty(o.getLink())){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    if(daoOrgao.editar(o)){
                                        Toast.makeText(getApplicationContext(), "O órgao foi editado com sucesso!", Toast.LENGTH_SHORT).show();
                                        progress_bar_busca.setVisibility(View.INVISIBLE);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Não foi possível editar o conteúdo.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btn_apagar_orgao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                                alerta.setTitle("Aviso");
                                alerta
                                        .setMessage("Deseja excluir esse órgão?")
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
                                                if(daoOrgao.excluir(daoOrgao.selecionar(codigo))){
                                                    Toast.makeText(getApplicationContext(), "Órgão removido", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Não foi possível remover o órgão.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                AlertDialog alertDialog = alerta.create();
                                alertDialog.show();
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Este órgão não existe.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Preencha o campo solicitado.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditOrgao.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}