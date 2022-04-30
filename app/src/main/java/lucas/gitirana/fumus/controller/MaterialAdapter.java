package lucas.gitirana.fumus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.Favorito;
import lucas.gitirana.fumus.model.Material;
import lucas.gitirana.fumus.model.Site;
import lucas.gitirana.fumus.view.SiteAdapter;

public class MaterialAdapter extends RecyclerView.Adapter {

    List<Material> materiais;

    public MaterialAdapter(List<Material> materiais) {
        this.materiais = materiais;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material, parent, false);
        ViewHolderClassMaterial vhClassMaterial = new ViewHolderClassMaterial(view);
        return vhClassMaterial;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClassMaterial vhClassMaterial = (ViewHolderClassMaterial) holder;
        Material m = materiais.get(position);
        vhClassMaterial.txtTituloMaterial.setText(m.getTitulo());
        vhClassMaterial.txtLinkMaterial.setText(m.getLink());
        vhClassMaterial.txtDescricaoMaterial.setText(m.getDescricao());
        vhClassMaterial.txtClassificacaoMaterial.setText(m.getClassificacao());
        vhClassMaterial.txtCodigo.setText(String.valueOf(m.getId()));
    }

    @Override
    public int getItemCount() {
        return materiais.size();
    }

    private class ViewHolderClassMaterial extends RecyclerView.ViewHolder{
        TextView txtTituloMaterial, txtLinkMaterial, txtDescricaoMaterial, txtClassificacaoMaterial, txtCodigo;
        ImageButton btn_favoritar;

        public ViewHolderClassMaterial(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTituloMaterial = itemView.findViewById(R.id.txtTituloMaterial);
            txtLinkMaterial = itemView.findViewById(R.id.txtLinkMaterial);
            txtDescricaoMaterial = itemView.findViewById(R.id.txtDescricaoMaterial);
            txtClassificacaoMaterial = itemView.findViewById(R.id.txtClassificacaoMaterial);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            btn_favoritar = itemView.findViewById(R.id.btn_favoritar);

            btn_favoritar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DaoFavorito daoFavorito =  new DaoFavorito();
                    String nome = txtTituloMaterial.getText().toString();
                    String link = txtLinkMaterial.getText().toString();
                    Favorito favorito = new Favorito(nome,link);
                    Random id = new Random();
                    favorito.setId(id.nextInt(100000));
                    daoFavorito.inserir(favorito);
                }
            });
        }
    }
}
