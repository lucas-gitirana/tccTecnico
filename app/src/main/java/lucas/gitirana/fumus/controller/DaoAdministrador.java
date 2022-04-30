package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lucas.gitirana.fumus.model.Administrador;
import lucas.gitirana.fumus.model.Experiencia;

public class DaoAdministrador extends Dao{

    public List<Administrador> listar(){
        List<Administrador> adms = new ArrayList<>();

        try{
            databaseReference.child("administradores").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Administrador a = ds.getValue(Administrador.class);
                        adms.add(a);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return adms;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
