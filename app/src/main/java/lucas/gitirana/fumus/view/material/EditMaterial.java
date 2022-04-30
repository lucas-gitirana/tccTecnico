package lucas.gitirana.fumus.view.material;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoMaterial;
import lucas.gitirana.fumus.model.Material;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.view.MainActivity;
import lucas.gitirana.fumus.view.orgao.EditOrgao;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class EditMaterial extends AppCompatActivity {
    EditText edt_id_material, edt_titulo_material, edt_desc_material, edt_link_material, edt_classificacao_material;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btn_buscar, btn_editar_material, btn_voltar, btn_apagar_material;
    ProgressBar progress_bar, progress_bar_busca;
    DatabaseReference databaseReference;
    LinearLayout componentes;
    String classificacao;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_material);

        edt_id_material = findViewById(R.id.edt_id_material);
        edt_titulo_material = findViewById(R.id.edt_titulo_material);
        edt_desc_material = findViewById(R.id.edt_desc_material);
        edt_link_material = findViewById(R.id.edt_link_material);
        edt_classificacao_material = findViewById(R.id.edt_classificacao_material);
        radioGroup = findViewById(R.id.radioGroup);
        btn_editar_material = findViewById(R.id.btn_editar_material);
        btn_apagar_material = findViewById(R.id.btn_apagar_material);
        btn_voltar = findViewById(R.id.btn_voltar);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar_busca = findViewById(R.id.progress_bar_busca);
        componentes = findViewById(R.id.componentes);
        btn_buscar = findViewById(R.id.btn_buscar_material);
        DaoMaterial daoMaterial = new DaoMaterial();

//        databaseReference = FirebaseDatabase.getInstance().getReference();

        componentes.setVisibility(View.INVISIBLE);

       /* btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!edt_id_material.getText().toString().isEmpty()){

                    int codigo = Integer.parseInt(edt_id_material.getText().toString());

                    databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for(DataSnapshot dn : snapshot.getChildren()){
                                Material posicao = dn.getValue(Material.class);
                                if(posicao.getId() == codigo){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    edt_titulo_material.setText(posicao.getTitulo());
                                    edt_desc_material.setText(posicao.getDescricao());
                                    edt_link_material.setText(posicao.getLink());

                                    int radioId = radioGroup.getCheckedRadioButtonId();
                                    if(radioId != -1) {
                                        radioButton = findViewById(radioId);
                                        classificacao = radioButton.getText().toString();
                                    }

                                    componentes.setVisibility(View.VISIBLE);
                                    progress_bar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Material encontrado", Toast.LENGTH_SHORT).show();

                                    btn_editar_material.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            progress_bar.setVisibility(View.VISIBLE);
                                            if(!TextUtils.isEmpty(edt_titulo_material.getText().toString()) && !TextUtils.isEmpty(edt_desc_material.getText().toString())
                                                    && !TextUtils.isEmpty(edt_link_material.getText().toString()) && !TextUtils.isEmpty(classificacao)){
                                                posicao.setTitulo(edt_titulo_material.getText().toString());
                                                posicao.setDescricao(edt_desc_material.getText().toString());
                                                posicao.setLink(edt_link_material.getText().toString());
                                                posicao.setClassificacao(edt_link_material.getText().toString());

                                                databaseReference.child("materiais").child(dn.getKey()).setValue(posicao);
                                                progress_bar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(EditMaterial.this, "O site "+posicao.getTitulo()+" foi editado com sucesso!", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(EditMaterial.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    btn_apagar_material.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(EditMaterial.this);
                                            alerta.setTitle("Aviso");
                                            alerta
                                                    .setMessage("Deseja excluir este material do aplicativo?")
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
                                                            databaseReference.child("materiais").child(dn.getKey()).removeValue();
                                                            Toast.makeText(getApplicationContext(), "Material removido...", Toast.LENGTH_SHORT).show();
                                                            componentes.setVisibility(View.INVISIBLE);

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
                    });

                }else{
                    Toast.makeText(EditMaterial.this, "Preencha o campo solicitado.", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(edt_id_material.getText().toString())){
                    progress_bar_busca.setVisibility(View.VISIBLE);
                    int codigo = Integer.parseInt(edt_id_material.getText().toString());


                    if(daoMaterial.selecionar(codigo) != null){
                        componentes.setVisibility(View.VISIBLE);
                        edt_titulo_material.setText(daoMaterial.selecionar(codigo).getTitulo());
                        edt_desc_material.setText(daoMaterial.selecionar(codigo).getDescricao());
                        edt_link_material.setText(daoMaterial.selecionar(codigo).getLink());
                        progress_bar_busca.setVisibility(View.INVISIBLE);

                        btn_editar_material.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Material m = new Material();
                                m.setTitulo(edt_titulo_material.getText().toString());
                                m.setClassificacao(edt_classificacao_material.getText().toString());
                                m.setLink(edt_link_material.getText().toString());
                                m.setId(daoMaterial.selecionar(codigo).getId());

                                int radioId = radioGroup.getCheckedRadioButtonId();
                                if(radioId != -1) {
                                    radioButton = findViewById(radioId);
                                    classificacao = radioButton.getText().toString();
                                }

                                m.setClassificacao(classificacao);

                                if(!TextUtils.isEmpty(m.getTitulo()) && !TextUtils.isEmpty(m.getDescricao())
                                        && !TextUtils.isEmpty(m.getLink()) && !TextUtils.isEmpty(m.getClassificacao())){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    if(daoMaterial.editar(m)){
                                        Toast.makeText(getApplicationContext(), "O material foi editado com sucesso!", Toast.LENGTH_SHORT).show();
                                        progress_bar_busca.setVisibility(View.INVISIBLE);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Não foi possível editar o conteúdo.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btn_apagar_material.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                                alerta.setTitle("Aviso");
                                alerta
                                        .setMessage("Deseja excluir esse material?")
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
                                                if(daoMaterial.excluir(daoMaterial.selecionar(codigo))){
                                                    Toast.makeText(getApplicationContext(), "Material removido", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Não foi possível remover o material.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                AlertDialog alertDialog = alerta.create();
                                alertDialog.show();
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Este material não existe.", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(EditMaterial.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}