package lucas.gitirana.fumus.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lucas.gitirana.fumus.view.SiteAdapter;

public class DaoSite {
    DatabaseReference databaseReference;
    Site s = new Site();
    boolean result;

    public boolean inserir(Site site){
        try{
            databaseReference.child("sites").push().setValue(site);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Site selecionar(int codigo){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dn : snapshot.getChildren()){
                    Site posicao = dn.getValue(Site.class);
                    if(posicao.getId() == codigo){
                        s = dn.getValue(Site.class);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return s;
    }

    public List<Site> listar(){
        List<Site> sites = new ArrayList<>();

        try{
            databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Site s = ds.getValue(Site.class);
                        sites.add(s);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return sites;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public boolean editar(Site s){
        databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Site posicao = ds.getValue(Site.class);
                    String key = ds.getKey();
                    if(posicao.getId() == s.getId()){
                        databaseReference.child("sites").child(key).setValue(s);
                        result = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                result = false;
            }
        });
        return result;
    }
}
