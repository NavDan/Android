package com.example.respondme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapAmigos extends RecyclerView.Adapter<AdapAmigos.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView user, nom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.amigoUser);
            nom = itemView.findViewById(R.id.amigoNom);

        }
    }

    public List<ModeloAmigos> perLista;

    public AdapAmigos(List<ModeloAmigos> perLista) {
        this.perLista = perLista;
    }

    @NonNull
    @Override
    public AdapAmigos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amigo,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapAmigos.ViewHolder holder, int position) {
        holder.user.setText(perLista.get(position).getTitulo());
        holder.nom.setText(perLista.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return perLista.size();
    }
}
