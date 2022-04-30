package lucas.gitirana.fumus.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import lucas.gitirana.fumus.R;
import lucas.gitirana.fumus.model.Favorito;
import lucas.gitirana.fumus.model.Orgao;
import lucas.gitirana.fumus.view.SiteAdapter;

public class OrgaoAdapter extends RecyclerView.Adapter {
    List<Orgao> orgaos;

    public OrgaoAdapter(List<Orgao> orgaos) {
        this.orgaos = orgaos;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orgao, parent, false);
        ViewHolderClassOrgao vhClassOrgao = new ViewHolderClassOrgao(view);
        return vhClassOrgao;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClassOrgao vhClassOrgao = (ViewHolderClassOrgao) holder;
        Orgao o = orgaos.get(position);
        vhClassOrgao.txtNomeOrgao.setText(o.getNome());
        vhClassOrgao.txtEstadoOrgao.setText(o.getEstado());
        vhClassOrgao.txtLinkOrgao.setText(o.getLink());
        vhClassOrgao.txtCodigo.setText(String.valueOf(o.getId()));
    }

    @Override
    public int getItemCount() {
        return orgaos.size();
    }

    private class ViewHolderClassOrgao extends RecyclerView.ViewHolder {
        TextView txtNomeOrgao, txtEstadoOrgao, txtLinkOrgao, txtCodigo;
        ImageButton btn_favoritar;

        public ViewHolderClassOrgao(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNomeOrgao = itemView.findViewById(R.id.txtNomeOrgao);
            txtEstadoOrgao = itemView.findViewById(R.id.txtEstadoOrgao);
            txtLinkOrgao = itemView.findViewById(R.id.txtLinkOrgao);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            btn_favoritar = itemView.findViewById(R.id.btn_favoritar);

            btn_favoritar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DaoFavorito daoFavorito =  new DaoFavorito();
                    String nome = txtNomeOrgao.getText().toString();
                    String link = txtLinkOrgao.getText().toString();
                    Favorito favorito = new Favorito(nome,link);
                    Random id = new Random();
                    favorito.setId(id.nextInt(100000));
                    daoFavorito.inserir(favorito);
                }
            });
        }
    }
}
