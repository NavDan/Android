package com.example.respondme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class resultados extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        btnVolver = findViewById(R.id.btnresultadosvolver);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        btnVolver.setEnabled(false);
        Intent inte = new Intent(this, menuactivity.class);
        startActivity(inte);
        finish();
    }
}
