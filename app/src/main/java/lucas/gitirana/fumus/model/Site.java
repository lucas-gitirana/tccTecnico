package lucas.gitirana.fumus.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Site {
    private String nome;
    private String link;
    private int id;

    public Site() {
    }

    public Site(String nome, String link, int id) {
        this.nome = nome;
        this.link = link;
        this.id = id;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void salvar(){
        Random id = new Random();
        this.setId(id.nextInt(100000));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("sites").push().setValue(this);
    }
}
