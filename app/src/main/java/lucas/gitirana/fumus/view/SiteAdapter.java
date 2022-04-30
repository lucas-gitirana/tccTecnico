package lucas.gitirana.fumus.view;

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
import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.controller.DaoFavorito;
import lucas.gitirana.fumus.model.Favorito;
import lucas.gitirana.fumus.model.Site;

public class SiteAdapter extends RecyclerView.Adapter{
    List<Site> sites;

    public SiteAdapter(List<Site> sites) {
        this.sites = sites;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_site,parent,false);
        ViewHolderClass vhClass=  new ViewHolderClass(view);
        return vhClass;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass vhClass = (ViewHolderClass) holder;
        Site site = sites.get(position);
        vhClass.txt_nome_site.setText(site.getNome());
        vhClass.txt_link_site.setText(site.getLink());
        vhClass.txtCodigo.setText(String.valueOf(site.getId()));
    }

    @Override
    public int getItemCount() {
        return sites.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView txt_nome_site, txtCodigo;
        TextView txt_link_site;
        ImageButton btn_favoritar;

        public ViewHolderClass(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_nome_site = itemView.findViewById(R.id.txtNome);
            txt_link_site = itemView.findViewById(R.id.txtLink);
            btn_favoritar = itemView.findViewById(R.id.btn_favoritar);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);

            btn_favoritar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DaoFavorito daoFavorito = new DaoFavorito();
                    String nome = txt_nome_site.getText().toString();
                    String link = txt_link_site.getText().toString();
                    Favorito favorito = new Favorito(nome,link);
                    Random id = new Random();
                    favorito.setId(id.nextInt(100000));
                    daoFavorito.inserir(favorito);
                }
            });
        }
    }

}
