package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import lucas.gitirana.fumus.controller.DaoFavorito;
import lucas.gitirana.fumus.controller.FavoritoAdapter;
import lucas.gitirana.fumus.model.Favorito;

public class ListaFavoritos extends AppCompatActivity {
//    List<Favorito> favoritos;
    RecyclerView recyclerView;
    FavoritoAdapter favoritoAdapter;
    DatabaseReference databaseReference;
    EditText edt_id_favorito;
    Button btn_buscar_favorito;
    // FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_favoritos);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        favoritos = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        edt_id_favorito = findViewById(R.id.edt_id_favorito);
        btn_buscar_favorito = findViewById(R.id.btn_buscar_favorito);
//        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DaoFavorito daoFavorito = new DaoFavorito();

        favoritoAdapter = new FavoritoAdapter(daoFavorito.listar());
        recyclerView.setAdapter(favoritoAdapter);

        /*databaseReference.child("usuarios").child(user).child("favoritos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn : snapshot.getChildren()){
                    Favorito f = dn.getValue(Favorito.class);
                    favoritos.add(f);
                }
                favoritoAdapter = new FavoritoAdapter(favoritos);
                recyclerView.setAdapter(favoritoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        btn_buscar_favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_id_favorito.getText().toString())){
                    int codigo = Integer.parseInt(edt_id_favorito.getText().toString());
                    if(daoFavorito.selecionar(codigo) != null){
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                        alerta.setTitle("Aviso");
                        alerta
                                .setMessage("Deseja excluir esse favorito?")
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
                                        if(daoFavorito.excluir(daoFavorito.selecionar(codigo))){
                                            Toast.makeText(getApplicationContext(), "Favorito removido. Atualize a página!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Não foi possível remover o item.", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                        AlertDialog alertDialog = alerta.create();
                        alertDialog.show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Este item não existe.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "O campo está vazio.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}