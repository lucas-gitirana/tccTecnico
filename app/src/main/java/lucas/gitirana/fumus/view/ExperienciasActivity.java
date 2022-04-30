package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.ControlExperiencia;
import lucas.gitirana.fumus.controller.DaoExperiencia;
import lucas.gitirana.fumus.controller.ExperienciaAdapater;
import lucas.gitirana.fumus.model.Experiencia;
import lucas.gitirana.fumus.model.Usuario;

public class ExperienciasActivity extends AppCompatActivity {
    EditText edt_experiencia;
    Button btn_enviar_experiencia, btn_voltar, btn_gerenciar;
    List<Experiencia> experiencias;
    RecyclerView recyclerView;
    ExperienciaAdapater experienciaAdapater;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);
        edt_experiencia = findViewById(R.id.edt_experiencia);
        btn_enviar_experiencia = findViewById(R.id.btn_enviar_experiencia);
        btn_gerenciar = findViewById(R.id.btn_gerenciar);
        recyclerView = findViewById(R.id.recyclerView);
        btn_voltar = findViewById(R.id.btn_voltar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        experiencias = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Experiencia e = new Experiencia();
        databaseReference = FirebaseDatabase.getInstance().getReference();

       /* databaseReference.child("experiencias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Experiencia e = ds.getValue(Experiencia.class);
                    experiencias.add(e);
                }
                experienciaAdapater = new ExperienciaAdapater(experiencias);
                recyclerView.setAdapter(experienciaAdapater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        DaoExperiencia daoExperiencia = new DaoExperiencia();
        if(daoExperiencia.listar() == null){
            Toast.makeText(getApplicationContext(), "Nenhuma experiência cadastrada.", Toast.LENGTH_SHORT).show();
        }else{
            experienciaAdapater = new ExperienciaAdapater(daoExperiencia.listar());
            recyclerView.setAdapter(experienciaAdapater);
        }

        btn_gerenciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditarExperiencia.class);
                startActivity(i);
            }
        });

        btn_enviar_experiencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_experiencia.getText().toString())){

                       /* String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        databaseReference.child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Usuario u = snapshot.getValue(Usuario.class);
                                e.setNomeUsuario(u.getNome()+"("+u.getCondicao()+") - "+u.getCidade()+" - "+u.getEstado());
                                e.setTexto(edt_experiencia.getText().toString());
                                if(daoExperiencia.inserir(e)){
                                    Toast.makeText(ExperienciasActivity.this,
                                            "Experiência publicada!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ExperienciasActivity.this,
                                            "Não foi possível publicar experiência!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),
                                "Erro ao carregar informações", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    ControlExperiencia controlExperiencia = new ControlExperiencia(); */

                    if(daoExperiencia.inserir(edt_experiencia.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Relato gravado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Não foi possível gravar o relato.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(ExperienciasActivity.this, "Você não digitou o texto da experiência.", Toast.LENGTH_SHORT).show();
                }
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