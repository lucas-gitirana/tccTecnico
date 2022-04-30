package lucas.gitirana.fumus.model;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Experiencia {
    private int id;
    private String texto;
    private String nomeUsuario;

    public Experiencia() {
    }

    public Experiencia(int id, String texto, String nomeUsuario) {
        this.id = id;
        this.texto = texto;
        this.nomeUsuario = nomeUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public boolean salvar(){
        try{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("experiencias").push().setValue(this);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
