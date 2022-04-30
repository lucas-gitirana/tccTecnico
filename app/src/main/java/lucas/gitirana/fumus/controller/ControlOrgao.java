package lucas.gitirana.fumus.controller;

import java.util.Random;

import lucas.gitirana.fumus.model.Orgao;
import lucas.gitirana.fumus.model.Site;

public class ControlOrgao {
    DaoOrgao daoOrgao = new DaoOrgao();
    Orgao o = new Orgao();

    public boolean gravar(String nome, String estado, String link) {

        Random id = new Random();
        o.setNome(nome);
        o.setEstado(estado);
        o.setLink(link);
        o.setId(id.nextInt(100000));

        if (daoOrgao.inserir(o)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editar(String nome, String estado, String link, int codigo) {

        o.setNome(nome);
        o.setEstado(estado);
        o.setLink(link);

        if (daoOrgao.editar(o, codigo)) {
            return true;
        } else {
            return false;
        }
    }
}
