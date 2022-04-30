package lucas.gitirana.fumus.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Favorito {
    private String nome, link;
    private int id;

    public Favorito() {
    }

    public Favorito(String nome, String link) {
        this.nome = nome;
        this.link = link;
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child("usuarios").child(user).child("favoritos").push().setValue(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
