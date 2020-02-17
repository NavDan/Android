package com.example.respondme.ui.Amigos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.respondme.AdapAmigos;
import com.example.respondme.AdapSolicitud;
import com.example.respondme.AddAmigo;
import com.example.respondme.Apiservice;
import com.example.respondme.MainActivity;
import com.example.respondme.ModeloAmigos;
import com.example.respondme.ModeloSolicitud;
import com.example.respondme.R;
import com.example.respondme.Usuario;
import com.example.respondme.Usuario_;
import com.example.respondme.menuactivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Amigos extends Fragment implements View.OnClickListener{

    private RecyclerView reciamigos, recisoli;
    private AdapAmigos adapamigos;
    private AdapSolicitud adapsoli;
    private Button btnadd;
    private SharedPreferences sp;
    public List<ModeloAmigos> amigo;
    public List<ModeloSolicitud> sol;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.amigos, container, false);

        sp = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);

        reciamigos = root.findViewById(R.id.recicleramigos);
        reciamigos.setLayoutManager(new LinearLayoutManager(getContext()));

        amigo = new ArrayList<>();
        obAmigos();

        recisoli = root.findViewById(R.id.reciclersolicitudes);
        recisoli.setLayoutManager(new LinearLayoutManager(getContext()));

        sol = new ArrayList<>();
        obSolicitud();

        btnadd = root.findViewById(R.id.btnamigoadd);
        btnadd.setOnClickListener(this);

        return root;
    }


    public void obSolicitud(){
        final String id = sp.getString("actId","0");
        if(id!="0"){

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Usuario> call = restClient.cargasol(id);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    switch (response.code()){
                        case 200:
                            Usuario data = response.body();
                            if (data.getEstado()==0){
                                // recibimos datos de usuario
                                for( Usuario_ user : data.getUsuarios()){
                                    sol.add(new ModeloSolicitud(user.getUsuario()+"",user.getNombre()+""));
                                }
                                adapsoli = new AdapSolicitud(sol,Integer.parseInt(id),getActivity());
                                recisoli.setAdapter(adapsoli);
                            }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(), "Error al conectar", Toast.LENGTH_SHORT).show();
        }
    }

    public void obAmigos(){
        String id = sp.getString("actId","0");
        if(id!="0"){

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Usuario> call = restClient.cargauser(id);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    switch (response.code()){
                        case 200:
                            Usuario data = response.body();
                            if (data.getEstado()==0){
                                // recibimos datos de usuario
                                for( Usuario_ user : data.getUsuarios()){
                                    amigo.add(new ModeloAmigos(user.getUsuario()+"",user.getNombre()+""));
                                }
                                adapamigos = new AdapAmigos(amigo);
                                reciamigos.setAdapter(adapamigos);
                            } else if(data.getEstado()==1){
                                // mensaje de credenciales incorrectas
                                Toast.makeText(getContext(),"Error al cargar los usuarios",Toast.LENGTH_SHORT).show();
                            }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(), "Error al conectar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        btnadd.setEnabled(false);
        Intent inte = new Intent(getActivity(), AddAmigo.class);
        startActivity(inte);
        getActivity().finish();

    }
}