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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btninicio, btnregistro;
    EditText txtuser, txtpass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);

        txtuser = findViewById(R.id.userTxt);
        txtpass = findViewById(R.id.passTxt);

        recuperarLogin();

        btninicio = findViewById(R.id.btniniciomain);
        btnregistro = findViewById(R.id.btnregistromain);

        btninicio.setOnClickListener(this);
        btnregistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btnregistro.getId()==v.getId()){
            Intent intent = new Intent(this, registroactivity.class);
            startActivity(intent);
        }else{
            loadJSON();
        }
    }

    public void guardarLogin(){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("usuario",txtuser.getText().toString());
        editor.putString("contraseña",txtpass.getText().toString());
        editor.putBoolean("sesion",true);

        editor.commit();
    }

    public void recuperarLogin(){
        if(sharedPreferences.getBoolean("sesion",false)){
            txtuser.setText(sharedPreferences.getString("usuario",""));
            txtpass.setText(sharedPreferences.getString("contraseña",""));
            if(!sharedPreferences.getString("usuario","").equals("")){
                loadJSON();
            }
        }
    }

    public void loadJSON(){

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();

        Apiservice restClient = retrofit.create(Apiservice.class);

        Call<Usuario> call = restClient.login(txtuser.getText().toString(), txtpass.getText().toString());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                switch (response.code()){
                    case 200:
                        Usuario data = response.body();
                        if (data.getEstado()==0){
                            // recibimos datos de usuario
                            for( Usuario_ user : data.getUsuarios()){
                                guardarLogin();
                                Intent intent = new Intent(MainActivity.this, menuactivity.class);
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString("actUsuario",user.getUsuario()+"");
                                edit.putString("actNom",user.getNombre()+"");
                                edit.putString("actNivel",user.getNivel()+"");
                                edit.putString("actId",user.getIdUsuario());
                                edit.commit();
                                startActivity(intent);
                                finish();
                            }
                        } else if(data.getEstado()==1){
                            // mensaje de credenciales incorrectas
                            Toast.makeText(getApplicationContext(),"Datos de inicio de sesión incorrectos",Toast.LENGTH_SHORT).show();
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
