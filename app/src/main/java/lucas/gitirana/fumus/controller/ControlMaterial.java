package lucas.gitirana.fumus.controller;

import java.util.Random;

import lucas.gitirana.fumus.model.Material;

public class ControlMaterial {
    DaoMaterial daoMaterial = new DaoMaterial();
    Material m = new Material();

    public boolean gravar(String titulo, String descricao, String link, String classificacao) {

        Random id = new Random();
        m.setTitulo(titulo);
        m.setDescricao(descricao);
        m.setLink(link);
        m.setClassificacao(classificacao);
        m.setId(id.nextInt(10000));

        if (daoMaterial.inserir(m)) {
            return true;
        } else {
            return false;
        }
    }

    /*public boolean editar(String titulo, String descricao, String link, String classificacao, int codigo) {

        m.setTitulo(titulo);
        m.setDescricao(descricao);
        m.setLink(link);
        m.setClassificacao(classificacao);

        if (daoMaterial.editar(m, codigo)) {
            return true;
        } else {
            return false;
        }
    }*/
}
