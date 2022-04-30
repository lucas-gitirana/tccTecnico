package lucas.gitirana.fumus.controller;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import lucas.gitirana.fumus.controller.DaoSite;
import lucas.gitirana.fumus.model.Site;

public class ControlSite {
    DaoSite daoSite = new DaoSite();
    Site s = new Site();

    public boolean gravar(String nome, String link) {

        Random id = new Random();
        s.setNome(nome);
        s.setLink(link);
        s.setId(id.nextInt(100000));

        if (daoSite.inserir(s)) {
            return true;
        } else {
            return false;
        }
    }



}
