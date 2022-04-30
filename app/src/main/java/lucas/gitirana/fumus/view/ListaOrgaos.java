package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.OrgaoAdapter;
import lucas.gitirana.fumus.model.DaoOrgao;
import lucas.gitirana.fumus.model.Orgao;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class ListaOrgaos extends AppCompatActivity {
//    List<Orgao> orgaos;
    RecyclerView recyclerView;
    OrgaoAdapter orgaoAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_orgaos);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DaoOrgao daoOrgao = new DaoOrgao();

        orgaoAdapter = new OrgaoAdapter(daoOrgao.listar());
        recyclerView.setAdapter(orgaoAdapter);

//        orgaos = new ArrayList<>();
        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Orgao o = ds.getValue(Orgao.class);
                    orgaos.add(o);
                }
                orgaoAdapter = new OrgaoAdapter(orgaos);
                recyclerView.setAdapter(orgaoAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/


    }
}