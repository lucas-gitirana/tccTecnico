package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lucas.gitirana.fumus.model.Experiencia;
import lucas.gitirana.fumus.model.Usuario;


public class DaoExperiencia extends Dao{
    Experiencia experiencia = new Experiencia();

    public boolean inserir(String texto){
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference.child("usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario u = snapshot.getValue(Usuario.class);
                    Experiencia e = new Experiencia();

                    Random id = new Random();
                    e.setNomeUsuario(u.getNome()+" - "
                            +u.getCondicao()+ "\n"
                            +u.getCidade()+" - "
                            +u.getEstado()+"\n"
                            +u.getEmail());
                    e.setTexto(texto);
                    e.setId(id.nextInt(100000));

                    databaseReference.child("experiencias").push().setValue(e);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public List<Experiencia> listar(){
        List<Experiencia> experiencias = new ArrayList<>();

        try{
            databaseReference.child("experiencias").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Experiencia e = ds.getValue(Experiencia.class);
                        experiencias.add(e);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return experiencias;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public Experiencia selecionar(int codigo){

        try{
            databaseReference.child("experiencias").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Experiencia e = ds.getValue(Experiencia.class);
                        if(e.getId() == codigo){
                            experiencia = e;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return experiencia;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean editar(Experiencia e){
        try{
            databaseReference.child("experiencias").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Experiencia experiencia = ds.getValue(Experiencia.class);
                        if(experiencia.getId() == e.getId()){
                            databaseReference.child("experiencias").child(ds.getKey()).setValue(e);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            return true;
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Experiencia e){
        try{
            databaseReference.child("experiencias").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Experiencia experiencia = ds.getValue(Experiencia.class);
                        if(experiencia.getId() == e.getId()){
                            databaseReference.child("experiencias").child(ds.getKey()).removeValue();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
