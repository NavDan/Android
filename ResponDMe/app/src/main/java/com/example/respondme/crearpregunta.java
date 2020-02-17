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

public class crearpregunta extends AppCompatActivity implements View.OnClickListener{

    private Button btnVolver, btnCrear;
    private EditText preg, resc,res1,res2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearpregunta);

        btnCrear = findViewById(R.id.btncrearpregunta);
        btnCrear.setOnClickListener(this);

        btnVolver = findViewById(R.id.btncrearvolver);
        btnVolver.setOnClickListener(this);

        preg = findViewById(R.id.txtcrearpregunta);
        resc = findViewById(R.id.txtcrearrespc);
        res1 = findViewById(R.id.txtcrearres1);
        res2 = findViewById(R.id.txtcrearres2);
    }

    @Override
    public void onClick(View v) {
        if(btnVolver.getId()==v.getId()){
            btnVolver.setEnabled(false);
            btnCrear.setEnabled(false);
            Intent inte = new Intent(this, menuactivity.class);
            startActivity(inte);
            finish();
        }else{
            btnVolver.setEnabled(false);
            btnCrear.setEnabled(false);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bdexterna.000webhostapp.com/Proyecto/").addConverterFactory(GsonConverterFactory.create(gson)).build();
            Apiservice restClient = retrofit.create(Apiservice.class);
            Call<Usuario> call = restClient.addpreg(preg.getText().toString(),resc.getText().toString(),res1.getText().toString(),res2.getText().toString());
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    switch (response.code()){
                        case 200:
                            Usuario data = response.body();
                            if (data.getEstado()==1){

                                Toast.makeText(getApplicationContext(),"Pregunta creada correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(crearpregunta.this, menuactivity.class);
                                startActivity(intent);
                                finish();
                            } else if (data.getEstado()==2){
                                // no insert
                                Toast.makeText(getApplicationContext(),"Datos incorrectos", Toast.LENGTH_SHORT).show();
                                btnVolver.setEnabled(true);
                                btnCrear.setEnabled(true);
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
}
