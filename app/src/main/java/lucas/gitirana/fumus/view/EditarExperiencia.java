package lucas.gitirana.fumus.view;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoExperiencia;
import lucas.gitirana.fumus.model.Experiencia;
import lucas.gitirana.fumus.model.Orgao;

public class EditarExperiencia extends AppCompatActivity {
    EditText edt_id_experiencia, edt_editar_experiẽncia;
    Button btn_buscar, btn_editar, btn_apagar, btn_voltar;
    ProgressBar progress_bar, progress_bar_busca;
    TextView txtUsuario;
    CardView card_invisivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_experiencia);

        edt_id_experiencia = findViewById(R.id.edt_id_experiencia);
        edt_editar_experiẽncia = findViewById(R.id.edt_editar_experiẽncia);
        txtUsuario = findViewById(R.id.txtUsuario);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_editar = findViewById(R.id.btn_editar);
        btn_apagar = findViewById(R.id.btn_apagar);
        btn_voltar = findViewById(R.id.btn_voltar);
        card_invisivel = findViewById(R.id.card_invisivel);
        progress_bar_busca = findViewById(R.id.progress_bar_busca);
        progress_bar = findViewById(R.id.progress_bar);
        DaoExperiencia daoExperiencia = new DaoExperiencia();

        card_invisivel.setVisibility(View.INVISIBLE);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar_busca.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(edt_id_experiencia.getText().toString())){
                    progress_bar_busca.setVisibility(View.VISIBLE);
                    int codigo = Integer.parseInt(edt_id_experiencia.getText().toString());

                    if(daoExperiencia.selecionar(codigo) != null){
                        card_invisivel.setVisibility(View.VISIBLE);
                        edt_editar_experiẽncia.setText(daoExperiencia.selecionar(codigo).getTexto());
                        txtUsuario.setText(daoExperiencia.selecionar(codigo).getNomeUsuario());
                        progress_bar_busca.setVisibility(View.INVISIBLE);

                        btn_editar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Experiencia e = new Experiencia();
                                e.setTexto(edt_editar_experiẽncia.getText().toString());
                                e.setNomeUsuario(daoExperiencia.selecionar(codigo).getNomeUsuario());

                                if(!TextUtils.isEmpty(e.getTexto())){
                                    progress_bar_busca.setVisibility(View.INVISIBLE);
                                    if(daoExperiencia.editar(e)){
                                        Toast.makeText(getApplicationContext(), "O relato foi editado com sucesso!", Toast.LENGTH_SHORT).show();
                                        progress_bar_busca.setVisibility(View.INVISIBLE);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Não foi possível editar o relato.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Preencha o campo acima.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btn_apagar.setOnClickListener(new View.OnClickListener() {
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
                                                if(daoExperiencia.excluir(daoExperiencia.selecionar(codigo))){
                                                    Toast.makeText(getApplicationContext(), "Relato removido", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), ExperienciasActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Não foi possível remover o relato.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                AlertDialog alertDialog = alerta.create();
                                alertDialog.show();
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "Este relato não existe.", Toast.LENGTH_SHORT).show();
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
    }
}