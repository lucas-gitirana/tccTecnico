package lucas.gitirana.fumus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.Experiencia;

public class ExperienciaAdapater extends RecyclerView.Adapter {
    List<Experiencia> experiencias;

    public ExperienciaAdapater(List<Experiencia> experiencias) {
        this.experiencias = experiencias;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exeperiencia, parent, false);
        ViewHolderClassExperiencia vhClassExperiencia = new ViewHolderClassExperiencia(view);
        return vhClassExperiencia;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClassExperiencia vhClassExperiencia = (ViewHolderClassExperiencia) holder;
        Experiencia experiencia = experiencias.get(position);
        vhClassExperiencia.txtUsuario.setText(experiencia.getNomeUsuario());
        vhClassExperiencia.txtExperiencia.setText(experiencia.getTexto());

    }

    @Override
    public int getItemCount() {
        return experiencias.size();
    }

    private class ViewHolderClassExperiencia extends RecyclerView.ViewHolder{
        TextView txtUsuario, txtExperiencia;

        public ViewHolderClassExperiencia(@NonNull View itemView) {
            super(itemView);
            txtUsuario = itemView.findViewById(R.id.txtUsuario);
            txtExperiencia = itemView.findViewById(R.id.txtExperiencia);
        }
    }
}
