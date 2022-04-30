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
import lucas.gitirana.fumus.controller.DaoMaterial;
import lucas.gitirana.fumus.controller.MaterialAdapter;
import lucas.gitirana.fumus.model.Material;

public class ListaMateriais extends AppCompatActivity {
//    List<Material> materiais;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MaterialAdapter materialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materiais);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DaoMaterial daoMaterial = new DaoMaterial();
//        materiais = new ArrayList<>();

        materialAdapter = new MaterialAdapter(daoMaterial.listar());
        recyclerView.setAdapter(materialAdapter);

        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Material m = ds.getValue(Material.class);
                    materiais.add(m);
                }
                materialAdapter = new MaterialAdapter(materiais);
                recyclerView.setAdapter(materialAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/


    }
}