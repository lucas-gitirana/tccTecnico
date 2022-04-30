package lucas.gitirana.fumus.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.DaoSite;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.view.site.site.EditSites;

public class ListaSites extends AppCompatActivity {

//    List<Site> sites;
    RecyclerView recyclerView;
    SiteAdapter siteAdapter;
//    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sites);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DaoSite daoSite = new DaoSite();
//        sites = new ArrayList<>();

        siteAdapter = new SiteAdapter(daoSite.listar());
        recyclerView.setAdapter(siteAdapter);

        /*databaseReference = FirebaseDatabase.getInstance().getReference();

        try{
            databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for(DataSnapshot dn : snapshot.getChildren()){
                        Site s = dn.getValue(Site.class);
                        sites.add(s);
                    }
                    siteAdapter = new SiteAdapter(sites);
                    recyclerView.setAdapter(siteAdapter);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Não foi ṕossível carregar as informações", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/

    }
}