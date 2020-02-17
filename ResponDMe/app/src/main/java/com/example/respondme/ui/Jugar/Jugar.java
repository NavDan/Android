package com.example.respondme.ui.Jugar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.respondme.Apiservice;
import com.example.respondme.Juego;
import com.example.respondme.Juego_;
import com.example.respondme.MainActivity;
import com.example.respondme.Partida;
import com.example.respondme.R;
import com.example.respondme.Usuario;
import com.example.respondme.crearpregunta;
import com.example.respondme.menuactivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Jugar extends Fragment implements View.OnClickListener{

    private Button btnJugar, btnCrear, btnCerrar;
    private TextView lbluser, lblnivel;
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.jugar, container, false);

        sp  = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);

        lbluser = root.findViewById(R.id.lbljugaruser);
        lblnivel = root.findViewById(R.id.lbljugarnivel);

        lbluser.setText(sp.getString("actUsuario","_error_"));
        lblnivel.setText("Nivel "+sp.getString("actNivel","_error_"));

        btnJugar = root.findViewById(R.id.btnjugar);
        btnJugar.setOnClickListener(this);

        btnCerrar = root.findViewById(R.id.btncerrar);
        btnCerrar.setOnClickListener(this);

        btnCrear = root.findViewById(R.id.btncrear);
        btnCrear.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnJugar.getId()){
            btnJugar.setEnabled(false);
            btnCrear.setEnabled(false);
            btnCerrar.setEnabled(false);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Juego> call = restClient.crearale(sp.getString("actId","0"));
            call.enqueue(new Callback<Juego>() {
                @Override
                public void onResponse(Call<Juego> call, Response<Juego> response) {
                    switch (response.code()){
                        case 200:
                            Juego data = response.body();
                            if (data.getEstado()==1){

                                Juego_ game = data.getJuego().get(0);
                                Intent inte = new Intent(getActivity(), Partida.class);
                                inte.putExtra("Usuario1",game.getUsuario1());
                                inte.putExtra("Usuario2",game.getUsuario2());
                                startActivity(inte);
                                getActivity().finish();
                            } else if (data.getEstado()==2){
                                Toast.makeText(getContext(),"Ya estás jugando con todos los usuarios", Toast.LENGTH_SHORT).show();
                                btnCerrar.setEnabled(true);
                                btnCrear.setEnabled(true);
                                btnJugar.setEnabled(true);
                            }
                    }
                }

                @Override
                public void onFailure(Call<Juego> call, Throwable t) {
                    Toast.makeText(getContext(),"No se ha podido crear la partida",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if(v.getId()==btnCerrar.getId()){
                btnJugar.setEnabled(false);
                btnCrear.setEnabled(false);
                btnCerrar.setEnabled(false);
                Intent inte = new Intent(getActivity(), MainActivity.class);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("sesion",false);
                edit.commit();
                startActivity(inte);
                getActivity().finish();

            }else {
                btnJugar.setEnabled(false);
                btnCrear.setEnabled(false);
                btnCerrar.setEnabled(false);
                Intent inte = new Intent(getActivity(), crearpregunta.class);
                startActivity(inte);
                getActivity().finish();
            }
        }

    }
}