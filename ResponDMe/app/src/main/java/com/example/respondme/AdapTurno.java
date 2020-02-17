package com.example.respondme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapTurno extends RecyclerView.Adapter<AdapTurno.ViewHolder>  {

    public List<ModeloTurno> perLista;

    public AdapTurno(List<ModeloTurno> perLista) {
        this.perLista = perLista;
    }

    @NonNull
    @Override
    public AdapTurno.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno,parent,false);
        AdapTurno.ViewHolder viewHolder = new AdapTurno.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapTurno.ViewHolder holder, int position) {
        holder.user.setText(perLista.get(position).getTitulo());
        holder.rond.setText("Ronda: "+perLista.get(position).getRonda());
    }

    @Override
    public int getItemCount() {
        return perLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView user, rond;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.turnoUser);
            rond = itemView.findViewById(R.id.turnoRonda);

        }
    }

}
