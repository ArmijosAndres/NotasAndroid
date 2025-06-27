package com.example.notas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditarNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText titulo=findViewById(R.id.txt_titulo_edit);
        EditText contenido=findViewById(R.id.txt_contenido_edit);
        Button guardar=findViewById(R.id.btn_editar);
        String currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String IdNota = getIntent().getStringExtra("IdNota");
        obtenerDatos(IdNota, currentUserId, titulo, contenido);

        guardar.setOnClickListener(v -> {

        });

    }

    private void obtenerDatos (String id, String userId, EditText titulo, EditText contenido){

        FirebaseDatabase.getInstance().getReference().child("notas").child(userId).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String tituloNota = snapshot.child("titulo").getValue(String .class);
                    String contenidoNota= snapshot.child("contenido").getValue(String.class);

                    titulo.setText(tituloNota);
                    contenido.setText(contenidoNota);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    };

}