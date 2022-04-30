package lucas.gitirana.fumus.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.Favorito;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.view.ListaSites;

public class SiteAdapter extends RecyclerView.Adapter{

    List<Site> sites;

    public SiteAdapter(List<Site> sites) {
        this.sites = sites;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site, parent, false);
        ViewHolderClass vhClass = new ViewHolderClass(view);
        return vhClass;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Site site = sites.get(position);
        vhClass.txtNome.setText(site.getNome());
        vhClass.txtLink.setText(site.getLink());
        vhClass.txtCodigo.setText(String.valueOf(site.getId()));
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

   public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView txtNome, txt_nome_site, txt_link_site, txtCodigo;
        TextView txtLink;
        TextView txtEstado;
        ImageButton btn_favoritar;

        public ViewHolderClass(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtTituloMaterial);
            txt_nome_site = itemView.findViewById(R.id.txtNome);
            txt_link_site = itemView.findViewById(R.id.txtLink);
            txtNome = itemView.findViewById(R.id.txtTituloMaterial);
            txtLink = itemView.findViewById(R.id.txtLink);
            txtEstado = itemView.findViewById(R.id.txtEstadoOrgao);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            btn_favoritar = itemView.findViewById(R.id.btn_favoritar);
        }
    }
}
