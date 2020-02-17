package com.example.respondme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.respondme.ui.Amigos.Amigos;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapSolicitud extends RecyclerView.Adapter<AdapSolicitud.ViewHolder> {

    public List<ModeloSolicitud> perLista;
    private int id;
    Activity act;

    public AdapSolicitud(List<ModeloSolicitud> perLista, int id, Activity act) {
        this.perLista = perLista;
        this.id =id;
        this.act = act;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud,parent,false);
        AdapSolicitud.ViewHolder viewHolder = new AdapSolicitud.ViewHolder(view, id, act);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.user.setText(perLista.get(position).getTitulo());
        holder.nom.setText(perLista.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return perLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView user, nom;
        private ImageButton acept, anul;
        private int idusu;
        private Activity act;

        public ViewHolder(@NonNull View itemView, int idusu, Activity act) {
            super(itemView);

            user = itemView.findViewById(R.id.solicitudUser);
            nom = itemView.findViewById(R.id.solicitudNom);
            acept = itemView.findViewById(R.id.solbtnacept);
            anul = itemView.findViewById(R.id.solbtnden);

            acept.setOnClickListener(this);
            anul.setOnClickListener(this);

            this.idusu=idusu;
            this.act = act;

        }

        @Override
        public void onClick(View v) {
            if(v.getId()==acept.getId()){
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
                Apiservice restClient = retrofit.create(Apiservice.class);
                Call<Usuario> call = restClient.confsol(String.valueOf(idusu),user.getText().toString());

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        switch (response.code()){
                            case 200:
                                Usuario data = response.body();
                                if (data.getEstado()==0){
                                    // recibimos datos de usuario
                                    Toast.makeText(itemView.getContext(), "Se ha agregado a "+user.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(itemView.getContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                    }
                });
                Context context = itemView.getContext();
                context.startActivity(new Intent(context, menuactivity.class));
                act.finish();
            }else{
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
                Apiservice restClient = retrofit.create(Apiservice.class);
                Call<Usuario> call = restClient.elisol(String.valueOf(idusu),user.getText().toString());

                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        switch (response.code()){
                            case 200:
                                Usuario data = response.body();
                                if (data.getEstado()==0){
                                    // recibimos datos de usuario
                                    Toast.makeText(itemView.getContext(), "Se ha eliminado la solicitud de "+user.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(itemView.getContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                    }
                });
                Context context = itemView.getContext();
                context.startActivity(new Intent(context, menuactivity.class));
                act.finish();
            }
        }
    }

}
