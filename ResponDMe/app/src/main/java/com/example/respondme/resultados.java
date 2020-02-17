package com.example.respondme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class resultados extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolver, btnRonda;
    private TextView res, acert, contra, fin;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        sp = getSharedPreferences("datos", Context.MODE_PRIVATE);

        btnVolver = findViewById(R.id.btnresultadosvolver);
        res = findViewById(R.id.resultadosganarperder);
        acert = findViewById(R.id.resultadosmis);
        contra=findViewById(R.id.resultadoscont);
        btnRonda = findViewById(R.id.btnresultadosnuevaronda);
        fin = findViewById(R.id.resultadores);

        loadJson();
        cargarVentana();


        btnVolver.setOnClickListener(this);
        btnRonda.setOnClickListener(this);
    }

    public void cargarVentana(){
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        Apiservice restClient = retrofit.create(Apiservice.class);
        Call<Juego> call = restClient.juego(getIntent().getStringExtra("Usuario1"),getIntent().getStringExtra("Usuario2"));
        call.enqueue(new Callback<Juego>() {
            @Override
            public void onResponse(Call<Juego> call, Response<Juego> response) {
                switch (response.code()){
                    case 200:
                        Juego data = response.body();
                        if (data.getEstado()==0){
                            if(getIntent().getStringExtra("respuesta").equals("1")){
                                res.setText("Correcto");
                            }else{
                                res.setText("Incorrecto");
                            }
                            Juego_ juego = data.getJuego().get(0);
                            String id = sp.getString("actId","0");

                            int total1 = 0;
                            int total2 =0;

                            if(juego.getResU11()!=null){
                                if(juego.getResU11().equals("1")){
                                    total1++;
                                }
                                if(juego.getResU12()!=null){
                                    if(juego.getResU12().equals("1")){
                                        total1++;
                                    }
                                    if(juego.getResU13()!=null){
                                        if(juego.getResU13().equals("1")){
                                            total1++;
                                        }
                                    }
                                }
                            }

                            if(juego.getResU21()!=null){
                                if(juego.getResU21().equals("1")){
                                    total2++;
                                }
                                if(juego.getResU22()!=null){
                                    if(juego.getResU22().equals("1")){
                                        total2++;
                                    }
                                    if(juego.getResU23()!=null){
                                        if(juego.getResU23().equals("1")){
                                            total2++;
                                        }
                                    }
                                }
                            }

                            if(id.equals(juego.getUsuario1())){
                                acert.setText("Respuestas acertadas: "+total1);
                                contra.setText("El rival ha acertado: "+total2);
                            }else{
                                acert.setText("Respuestas acertadas: "+total2);
                                contra.setText("El rival ha acertado: "+total1);
                            }

                            if(juego.getPreg2()!=null){
                                if(juego.getResU12()!=null&&juego.getResU22()!=null){
                                    btnRonda.setVisibility(View.VISIBLE);
                                    btnVolver.setVisibility(View.INVISIBLE);
                                    fin.setText("Comienza la siguiente ronda.");
                                }else{
                                    btnRonda.setVisibility(View.INVISIBLE);
                                    btnVolver.setVisibility(View.VISIBLE);
                                    fin.setText("Esperando al rival.");
                                }
                                if(juego.getPreg3()!=null){
                                    if(juego.getResU13()!=null&&juego.getResU23()!=null){
                                        btnRonda.setVisibility(View.VISIBLE);
                                        btnVolver.setVisibility(View.INVISIBLE);
                                        if(id.equals(juego.getUsuario1())){
                                            if(total1>total2){
                                                fin.setText("Has ganado.");
                                            }else{
                                                if(total1<total2){
                                                    fin.setText("Has perdido.");
                                                }else{
                                                    res.setText("Habeis empatado.");
                                                }
                                            }
                                        }else{
                                            if(total1>total2){
                                                res.setText("Has perdido.");
                                            }else{
                                                if(total1<total2){
                                                    res.setText("Has ganado.");
                                                }else{
                                                    res.setText("Habeis empatado.");
                                                }
                                            }
                                        }
                                    }else{
                                        btnRonda.setVisibility(View.INVISIBLE);
                                        btnVolver.setVisibility(View.VISIBLE);
                                        fin.setText("Esperando al rival.");
                                    }
                                }
                            }else{
                                if(juego.getResU11()!=null&&juego.getResU21()!=null){
                                    btnRonda.setVisibility(View.VISIBLE);
                                    btnVolver.setVisibility(View.INVISIBLE);
                                    fin.setText("Comienza la siguiente ronda.");
                                }else{
                                    btnRonda.setVisibility(View.INVISIBLE);
                                    btnVolver.setVisibility(View.VISIBLE);
                                    fin.setText("Esperando al rival.");
                                }
                            }

                        } else if (data.getEstado()==1){
                            Toast.makeText(getApplicationContext(),"__error__", Toast.LENGTH_SHORT).show();
                        }
                }
            }

            @Override
            public void onFailure(Call<Juego> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadJson(){

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        Apiservice restClient = retrofit.create(Apiservice.class);
        Call<Juego> call = restClient.insres(getIntent().getStringExtra("Usuario1"),getIntent().getStringExtra("Usuario2"),getIntent().getStringExtra("ronda"),getIntent().getStringExtra("respuesta"),sp.getString("actId",""));
        call.enqueue(new Callback<Juego>() {
            @Override
            public void onResponse(Call<Juego> call, Response<Juego> response) {
                switch (response.code()){
                    case 200:
                        Juego data = response.body();
                        if (data.getEstado()==1){
                        } else if (data.getEstado()==-1){
                            // no insert
                            Toast.makeText(resultados.this, ""+getIntent().getStringExtra("Usuario1")+getIntent().getStringExtra("Usuario2")+getIntent().getStringExtra("ronda")+getIntent().getStringExtra("respuesta")+sp.getString("actId",""), Toast.LENGTH_SHORT).show();
                            //Intent inte = new Intent(resultados.this,menuactivity.class);
                            //startActivity(inte);
                           // finish();
                        }
                }
            }

            @Override
            public void onFailure(Call<Juego> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnRonda.getId()){
            btnRonda.setEnabled(false);
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Juego> call = restClient.newrond(getIntent().getStringExtra("Usuario1"),getIntent().getStringExtra("Usuario2"),getIntent().getStringExtra("ronda"));
            call.enqueue(new Callback<Juego>() {
                @Override
                public void onResponse(Call<Juego> call, Response<Juego> response) {
                    switch (response.code()){
                        case 200:
                            Juego data = response.body();
                            if (data.getEstado()==1){
                                Intent inte = new Intent(resultados.this, Partida.class);
                                inte.putExtra("Usuario1",getIntent().getStringExtra("Usuario1"));
                                inte.putExtra("Usuario2",getIntent().getStringExtra("Usuario2"));
                                startActivity(inte);
                                finish();
                            } else if (data.getEstado()==-1){
                                // no insert
                                Intent inte = new Intent(resultados.this,menuactivity.class);
                                startActivity(inte);
                                finish();
                            }
                    }
                }

                @Override
                public void onFailure(Call<Juego> call, Throwable t) {
                    //Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            btnVolver.setEnabled(false);
            Intent inte = new Intent(this, menuactivity.class);
            startActivity(inte);
            finish();
        }
    }
}
