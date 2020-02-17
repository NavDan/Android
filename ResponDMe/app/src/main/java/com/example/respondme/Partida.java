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

public class Partida extends AppCompatActivity implements View.OnClickListener{

    private Button btn1, btn2, btn3;
    private TextView pregunta, ronda , acert, contra;
    private String respu ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btn1 = findViewById(R.id.btnpartida1);
        btn2 = findViewById(R.id.btnpartida2);
        btn3 = findViewById(R.id.btnpartida3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        pregunta = findViewById(R.id.lblpartidapregunta);
        ronda = findViewById(R.id.lblpartidaronda);
        acert = findViewById(R.id.lblpartidaacertadas);
        contra = findViewById(R.id.lblpartidaacertada2);

        loadJson();

    }

    @Override
    public void onClick(View v) {
        Button btnres = findViewById(v.getId());
        respu = btnres.getText().toString();
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);

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
                            Juego_ juego = data.getJuego().get(0);

                            int rond = 1;
                            String resp = juego.getResC1();
                            if(juego.getPreg2()!=null){
                                rond++;
                                resp = juego.getResC2();
                                if(juego.getPreg3()!=null){
                                    rond++;
                                    resp = juego.getResC3();
                                }
                            }

                            if(respu.equals(resp)){
                                Intent intent = new Intent(Partida.this, resultados.class);
                                intent.putExtra("Usuario1",juego.getUsuario1());
                                intent.putExtra("Usuario2",juego.getUsuario1());
                                intent.putExtra("ronda",rond);
                                intent.putExtra("respuesta",1);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(Partida.this, resultados.class);
                                intent.putExtra("Usuario1",juego.getUsuario1());
                                intent.putExtra("Usuario2",juego.getUsuario1());
                                intent.putExtra("ronda",rond);
                                intent.putExtra("respuesta",0);
                                startActivity(intent);
                                finish();
                            }
                        } else if (data.getEstado()==1){
                            // no insert
                            Toast.makeText(getApplicationContext(),"__error__", Toast.LENGTH_SHORT).show();
                            System.exit(0);
                        }
                }
            }

            @Override
            public void onFailure(Call<Juego> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
            }
        });

        Intent inte = new Intent(this, resultados.class);
        startActivity(inte);
        finish();

    }

    public void loadJson(){

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
                            Juego_ juego = data.getJuego().get(0);
                            //Cargar ronda
                            int rond = 1;
                            if(juego.getPreg2()!=null){
                                rond++;
                                if(juego.getPreg3()!=null){
                                    rond++;
                                }
                            }
                            ronda.setText("Ronda "+rond);
                            //CargarPregunta
                            String preg=juego.getPreg1();
                            if(juego.getPreg2()!=null){
                                preg=juego.getPreg2();
                                if(juego.getPreg3()!=null){
                                    preg=juego.getPreg3();
                                }
                            }
                            pregunta.setText(preg);
                            //Cargar puntuacion
                            SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
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

                            //Respuetas

                            String[] arr = new String[3];

                            if(rond == 1){
                                arr[0]=juego.getResC1();
                                arr[1]=juego.getRes11();
                                arr[2]=juego.getRes21();
                            }else{
                                if(rond == 2){
                                    arr[0]=juego.getResC2();
                                    arr[1]=juego.getRes12();
                                    arr[2]=juego.getRes22();
                                }else{
                                    arr[0]=juego.getResC3();
                                    arr[1]=juego.getRes13();
                                    arr[2]=juego.getRes23();
                                }
                            }
                            int cont = 0;
                            while(arr[0]!=null||arr[1]!=null||arr[2]!=null){
                                int pos =(int)(Math.random()*3);
                                if(arr[pos]!=null){
                                    if(cont==0){
                                        btn1.setText(arr[pos]);
                                        arr[pos]=null;
                                        cont++;
                                    }else{
                                        if(cont==1){
                                            btn2.setText(arr[pos]);
                                            arr[pos]=null;
                                            cont++;
                                        }else{
                                            btn3.setText(arr[pos]);
                                            arr[pos]=null;
                                            cont++;
                                        }
                                    }
                                }
                            }

                        } else if (data.getEstado()==1){
                            // no insert
                            Toast.makeText(getApplicationContext(),"Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                }
            }

            @Override
            public void onFailure(Call<Juego> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
