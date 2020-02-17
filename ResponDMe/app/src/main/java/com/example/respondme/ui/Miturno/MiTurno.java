package com.example.respondme.ui.Miturno;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.respondme.AdapAmigos;
import com.example.respondme.AdapTurno;
import com.example.respondme.ModeloSolicitud;
import com.example.respondme.ModeloTurno;
import com.example.respondme.R;

import java.util.ArrayList;
import java.util.List;

public class MiTurno extends Fragment {

    private RecyclerView reciturno;
    private AdapTurno adapturno;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.miturno, container, false);

        reciturno = root.findViewById(R.id.reciclerturno);
        reciturno.setLayoutManager(new LinearLayoutManager(getContext()));

        adapturno = new AdapTurno(obTurno());
        reciturno.setAdapter(adapturno);

        return root;
    }

    public List<ModeloTurno> obTurno(){

        String[] users = {"Pepe","Pepita","Victor"};
        int[] rond = {2,1,3};

        List<ModeloTurno> amigo = new ArrayList<>();
        for (int i=0;i<users.length;i++){
            amigo.add(new ModeloTurno(users[i],rond[i]));
        }
        return amigo;
    }

}