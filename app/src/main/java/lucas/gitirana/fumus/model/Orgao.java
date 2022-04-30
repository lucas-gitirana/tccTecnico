package lucas.gitirana.fumus.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Orgao {
    private String nome;
    private String estado;
    private String link;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void salvar(){
        Random id = new Random();
        this.setId(id.nextInt(100000));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("orgaos").push().setValue(this);
    }
}
