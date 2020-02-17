package com.example.respondme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapAddAmigo extends RecyclerView.Adapter<AdapAddAmigo.ViewHolder> {

    public List<ModeloAddAmigo> perLista;
    public String id;
    public Activity act;

    public AdapAddAmigo(List<ModeloAddAmigo> perLista, String id, Activity act) {
        this.perLista = perLista;
        this.id =id;
        this.act = act;
    }

    @NonNull
    @Override
    public AdapAddAmigo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addamigo,parent,false);
        AdapAddAmigo.ViewHolder viewHolder = new AdapAddAmigo.ViewHolder(view,id,act);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapAddAmigo.ViewHolder holder, int position) {
        holder.user.setText(perLista.get(position).getTitulo());
        holder.rond.setText(perLista.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return perLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView user, rond;
        private ImageButton btnadd;
        private String id;
        private Activity act;

        public ViewHolder(@NonNull View itemView, String id , Activity act) {
            super(itemView);

            user = itemView.findViewById(R.id.addUser);
            rond = itemView.findViewById(R.id.addNom);

            btnadd = itemView.findViewById(R.id.btnaddamigo);
            btnadd.setOnClickListener(this);

            this.id = id;
            this.act = act;

        }

        @Override
        public void onClick(View v) {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice addamigo = retrofit.create(Apiservice.class);
            Call<Usuario> call = addamigo.addamg(id,user.getText().toString());

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    switch (response.code()){
                        case 200:
                            Usuario data = response.body();
                            if (data.getEstado()==0){
                                Toast.makeText(itemView.getContext(), "Solicituda enviada a "+user.getText().toString(), Toast.LENGTH_SHORT).show();
                                Context context = itemView.getContext();
                                context.startActivity(new Intent(context, menuactivity.class));
                                act.finish();
                            }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(itemView.getContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
