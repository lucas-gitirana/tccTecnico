package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Material;


public class DaoMaterial extends Dao{
    Material material = new Material();

    public boolean inserir(Material material){

        try{
            databaseReference.child("materiais").push().setValue(material);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Material> listar(){
        List<Material> materiais = new ArrayList<>();

        try{
            databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Material m = ds.getValue(Material.class);
                        materiais.add(m);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return materiais;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Material selecionar(int codigo){
        try{
            databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Material m = ds.getValue(Material.class);
                        if(m.getId() == codigo){
                            material = m;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return material;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean editar(Material m){
        try{
            databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Material material = ds.getValue(Material.class);
                        if(material.getId() == m.getId()){
                            databaseReference.child("materiais").child(ds.getKey()).setValue(m);
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

    public boolean excluir(Material m){
        try{
            databaseReference.child("materiais").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Material material = ds.getValue(Material.class);
                        if(material.getId() == m.getId()){
                            databaseReference.child("materiais").child(ds.getKey()).removeValue();
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
