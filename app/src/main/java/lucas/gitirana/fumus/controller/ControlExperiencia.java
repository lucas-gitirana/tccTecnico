package lucas.gitirana.fumus.controller;

import java.util.Random;

import lucas.gitirana.fumus.model.Experiencia;
import lucas.gitirana.fumus.model.Material;

public class ControlExperiencia {
    DaoExperiencia daoExperiencia = new DaoExperiencia();
    DaoVisitante daoVisitante = new DaoVisitante();
    Experiencia e = new Experiencia();

    public boolean gravar(String texto) {

        Random id = new Random();
        e.setNomeUsuario(daoVisitante.selecionar().getNome()+" - "
                +daoVisitante.selecionar().getCondicao()+ "\n"
                +daoVisitante.selecionar().getCidade()+" - "
                +daoVisitante.selecionar().getEstado()+"\n"
                +daoVisitante.selecionar().getEmail());
        e.setTexto(texto);
        e.setId(id.nextInt(100000));
/*
        if (daoExperiencia.inserir(e)) {
            return true;
        } else {
            return false;
        }*/
        return true;
    }

   /* public boolean editar(String texto, String nomeUsuario, int codigo) {

        e.setNomeUsuario(daoVisitante.selecionar().getNome()+" - "
                +daoVisitante.selecionar().getCondicao()+ "\n"
                +daoVisitante.selecionar().getCidade()+" - "
                +daoVisitante.selecionar().getEstado()+"\n"
                +daoVisitante.selecionar().getEmail());
        e.setTexto(texto);

        if (daoExperiencia.editar(e, codigo)) {
            return true;
        } else {
            return false;
        }*/
    //}
}
