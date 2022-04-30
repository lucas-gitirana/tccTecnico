package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Favorito;

public class DaoFavorito extends Dao{
    Favorito f = new Favorito();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public boolean inserir(Favorito f){
        try{
            databaseReference.child("usuarios").child(user.getUid()).child("favoritos").push().setValue(f);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Favorito> listar(){
        List<Favorito> favoritos = new ArrayList<>();

        try{
            databaseReference.child("usuarios").child(user.getUid()).child("favoritos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Favorito f = ds.getValue(Favorito.class);
                        favoritos.add(f);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return favoritos;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Favorito selecionar(int codigo){
        try{
            databaseReference.child("usuarios").child(user.getUid()).child("favoritos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Favorito favorito = ds.getValue(Favorito.class);
                        if(favorito.getId() == codigo){
                            f = favorito;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return f;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean excluir(Favorito f){
        try{
            databaseReference.child("usuarios").child(user.getUid()).child("favoritos").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Favorito favorito = ds.getValue(Favorito.class);
                        if(favorito.getNome().equals(f.getNome()) && favorito.getLink().equals(f.getLink())){
                            databaseReference.child("usuarios").child(user.getUid()).child("favoritos").child(ds.getKey()).removeValue();
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
