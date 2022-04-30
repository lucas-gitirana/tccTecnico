package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Usuario;

public class DaoVisitante extends Dao{
    Usuario u = new Usuario();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public boolean inserir(Usuario usuario){
        try{
            databaseReference.child("usuarios").push().setValue(usuario);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Usuario selecionar(){

        try{
            databaseReference.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){
                        if(ds.getKey() == user.getUid()){
                            u = ds.getValue(Usuario.class);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return u;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean editar(Usuario u){
        try{
            user.updateEmail(u.getEmail());
            user.updatePassword(u.getSenha());
            databaseReference.child("usuarios").child(user.getUid()).setValue(u);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(){
        try{
            databaseReference.child("usuarios").child(user.getUid()).removeValue();
            user.delete();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
