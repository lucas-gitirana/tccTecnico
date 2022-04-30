package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Site;


public class DaoSite extends Dao{
    Site site = new Site();

    public boolean inserir(Site site){
        try{
            databaseReference.child("sites").push().setValue(site);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
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

    public Site selecionar(int codigo){
        try{
            databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Site s = ds.getValue(Site.class);
                        if(s.getId() == codigo){
                            site= s;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return site;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean editar(Site s){
        try{
            databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Site site = ds.getValue(Site.class);
                        if(site.getId() == s.getId()){
                            databaseReference.child("sites").child(ds.getKey()).setValue(s);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Site s){
        try{
            databaseReference.child("sites").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Site site = ds.getValue(Site.class);
                        if(site.getId() == s.getId()){
                            databaseReference.child("sites").child(ds.getKey()).removeValue();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
