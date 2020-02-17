package com.example.respondme;

import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroactivity extends AppCompatActivity implements View.OnClickListener{

    Button volver, complregistro;
    EditText txtnom,txtuser,txtpass,txtrep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroactivity);

        volver = findViewById(R.id.btnvolver);
        complregistro = findViewById(R.id.btncompleregistro);

        txtnom=findViewById(R.id.registronom);
        txtuser = findViewById(R.id.registrouser);
        txtpass = findViewById(R.id.registropass);
        txtrep = findViewById(R.id.registrorpass);

        volver.setOnClickListener(this);
        complregistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(volver.getId()==v.getId()){
            finish();
        }else{
            if(txtpass.getText().toString().equals(txtrep.getText().toString())){
                loadJSON();
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadJSON(){
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();

        Apiservice restClient = retrofit.create(Apiservice.class);

        Call<Usuario> call = restClient.registro(txtuser.getText().toString(),txtpass.getText().toString(),txtnom.getText().toString());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                switch (response.code()){
                    case 200:
                        Usuario data = response.body();
                        if (data.getEstado()==1){
                            // insert
                            SharedPreferences sp =getSharedPreferences("datos", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("sesion",false);
                            edit.putString("usuario",txtuser.getText().toString());
                            edit.putString("contraseña",txtpass.getText().toString());
                            edit.commit();
                            Toast.makeText(getApplicationContext(),"Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registroactivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (data.getEstado()==2){
                            // no insert
                            Toast.makeText(getApplicationContext(),"Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"No funciona",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
