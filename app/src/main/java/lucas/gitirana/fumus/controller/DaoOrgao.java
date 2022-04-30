package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Experiencia;
import lucas.gitirana.fumus.model.Orgao;

public class DaoOrgao extends Dao{

    public boolean inserir(Orgao orgao){
        try{
            databaseReference.child("orgaos").push().setValue(orgao);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Orgao> listar(){
        List<Orgao> orgaos = new ArrayList<>();

        try{
            databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Orgao o = ds.getValue(Orgao.class);
                        orgaos.add(o);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return orgaos;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Orgao selecionar(int codigo){
        final Orgao[] orgao = {new Orgao()};
        try{
            databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Orgao o = ds.getValue(Orgao.class);
                        if(o.getId() == codigo){
                             orgao[0] = o;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return orgao[0];

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean editar(Orgao o, int codigo){
        try{
            databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Orgao orgao = ds.getValue(Orgao.class);
                        if(orgao.getId() == codigo){
                            databaseReference.child("orgaos").child(ds.getKey()).setValue(o);
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

    public boolean excluir(int codigo){
        try{
            databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Orgao orgao = ds.getValue(Orgao.class);
                        if(orgao.getId() == codigo){
                            databaseReference.child("orgaos").child(ds.getKey()).removeValue();
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
