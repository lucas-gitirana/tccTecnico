package lucas.gitirana.fumus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.Favorito;

public class FavoritoAdapter extends RecyclerView.Adapter{

    List<Favorito> favoritos;

    public FavoritoAdapter(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito, parent, false);
        ViewHolderClassFavorito vhClassFavorito = new ViewHolderClassFavorito(view);
        return vhClassFavorito;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClassFavorito vhClassFavorito = (ViewHolderClassFavorito) holder;
        Favorito f = favoritos.get(position);
        vhClassFavorito.txtNomeFavorito.setText(f.getNome());
        vhClassFavorito.txtLinkFavorito.setText(f.getLink());
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    private class ViewHolderClassFavorito extends RecyclerView.ViewHolder{
        TextView txtNomeFavorito, txtLinkFavorito;
        Button btn_apagar;

        public ViewHolderClassFavorito(@NonNull View itemView) {
            super(itemView);
            txtNomeFavorito = itemView.findViewById(R.id.txtNomeFavorito);
            txtLinkFavorito = itemView.findViewById(R.id.txtLinkFavorito);
            btn_apagar = itemView.findViewById(R.id.btn_apagar);

                btn_apagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DaoFavorito daoFavorito = new DaoFavorito();
                        String nome = txtNomeFavorito.getText().toString();
                        String link = txtLinkFavorito.getText().toString();
                        for(Favorito f : daoFavorito.listar()){
                            if(f.getNome().equals(nome) && f.getLink().equals(link)){
                                daoFavorito.excluir(f);
                            }
                        }
                    }
                });


        }
    }
}
