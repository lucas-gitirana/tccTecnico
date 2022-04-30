package lucas.gitirana.fumus.controller;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import lucas.gitirana.fumus.model.Usuario;

public class ControlVisitante {
    DaoVisitante daoVisitante = new DaoVisitante();

    public boolean registrar(String nome, String cidade, String estado, String condicao, String email){
        Usuario u = new Usuario(nome, cidade, estado, condicao, email);
        if(daoVisitante.inserir(u)){
            return true;
        }else{
            return false;
        }
    }
}
