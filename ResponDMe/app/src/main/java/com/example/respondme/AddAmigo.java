package com.example.respondme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class AddAmigo extends AppCompatActivity implements View.OnClickListener{

    private EditText txtnom;
    private Button btnVolver,btnbuscar;
    private RecyclerView reciadd;
    private AdapAddAmigo adapadd;
    private List<ModeloAddAmigo> amigo;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amigo);

        sp = getSharedPreferences("datos", Context.MODE_PRIVATE);

        reciadd = findViewById(R.id.recicleradd);
        reciadd.setLayoutManager(new LinearLayoutManager(this));
        txtnom=findViewById(R.id.addtxtnom);

        amigo = new ArrayList<>();

        btnVolver = findViewById(R.id.btnaddvolver);
        btnVolver.setOnClickListener(this);

        btnbuscar = findViewById(R.id.btnaddbuscar);
        btnbuscar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==btnVolver.getId()) {
            btnVolver.setEnabled(false);
            btnbuscar.setEnabled(false);
            Intent inte = new Intent(this, menuactivity.class);
            startActivity(inte);
            finish();
        }else{
            obAdd();
        }
    }

    public void obAdd(){
        final String id = sp.getString("actId","0");
        final String nom = txtnom.getText().toString();
        final Activity act = this;
        if(nom.trim().equals("")){
            Toast.makeText(this, "Introduce el texto a buscar.", Toast.LENGTH_SHORT).show();
        }else{
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Usuario> call = restClient.busc(id,nom);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    switch (response.code()){
                        case 200:
                            Usuario data = response.body();
                            if (data.getEstado()==0){
                                // recibimos datos de usuario
                                for( Usuario_ user : data.getUsuarios()){
                                    amigo.add(new ModeloAddAmigo(user.getUsuario()+"",user.getNombre()+""));
                                }
                                adapadd = new AdapAddAmigo(amigo,id,act);
                                reciadd.setAdapter(adapadd);
                            }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"No se puedo conectar",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
