package lucas.gitirana.fumus.model;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DaoOrgao {
    DatabaseReference databaseReference;
    Orgao o = new Orgao();
    boolean result;

    public boolean inserir(Orgao orgao){
        try{
            databaseReference.child("orgaos").push().setValue(orgao);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Orgao selecionar(int codigo){
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("orgaos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Orgao posicao = ds.getValue(Orgao.class);
                    if(codigo == posicao.getId()){
                        o = ds.getValue(Orgao.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                o = null;
            }
        });

        return o;
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

    public boolean editar(Orgao o){
        databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Orgao posicao = ds.getValue(Orgao.class);
                    String key = ds.getKey();
                    if(posicao.getId() == o.getId()){
                        databaseReference.child("orgaos").child(key).setValue(o);
                        result = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return result;
    }

    public boolean excluir(Orgao o){
        try{
            databaseReference.child("orgaos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Orgao orgao = ds.getValue(Orgao.class);
                        if(orgao.getId() == o.getId()){
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
