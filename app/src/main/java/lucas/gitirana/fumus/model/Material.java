package lucas.gitirana.fumus.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Material {
    private String titulo;
    private String descricao;
    private String link;
    private String classificacao;
    private int id;

    public Material() {
    }

    public Material(String titulo, String descricao, String link, String classificacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.classificacao = classificacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void salvar(){
        Random id = new Random();
        this.setId(id.nextInt(100000));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("materiais").push().setValue(this);
    }
}
