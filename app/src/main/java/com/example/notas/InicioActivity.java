package com.example.notas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //conexion a base de datos
        String CurrenUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //con esto validamos el inicion de seesion

        //instanseasiones
        ArrayList<Nota> notasList = new ArrayList<>();

        NotasAdapter adapter = new NotasAdapter(notasList);

        LinearLayoutManager lm = new LinearLayoutManager(this);

        Button crearNota=findViewById(R.id.btn_crear_nota);

        RecyclerView recyclerViewNotas=findViewById(R.id.rv_notas);

        //

        recyclerViewNotas.setLayoutManager(lm);

        recyclerViewNotas.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("notas").child(CurrenUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    notasList.clear(); //para que no se monten los datos encima
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                        Nota nota = dataSnapshot.getValue(Nota.class);

                        notasList.add(nota);

                        adapter.notifyDataSetChanged();

                        Collections.reverse(notasList);
                    };
                };
            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            };
        });

        crearNota.setOnClickListener(v -> {
            //asignar evento de ir del inicio a crear nota
            Intent intent=new Intent(InicioActivity.this, CrearNotaActivity.class);
            startActivity(intent);

        });

    }

    //

}