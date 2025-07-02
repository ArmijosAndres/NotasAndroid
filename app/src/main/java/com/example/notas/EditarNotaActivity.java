package com.example.notas;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            String titulNota = titulo.getText().toString();
            String contenidoNota = contenido.getText().toString();

            if(TextUtils.isEmpty(titulNota) || TextUtils.isEmpty(contenidoNota)){
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }else {
                Map<String, Object> nota = new HashMap<>();
                nota.put("titulo",  titulNota);
                nota.put("contenido", contenidoNota);

                FirebaseDatabase.getInstance().getReference().child("notas").child(currentUserId).child(IdNota).updateChildren(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditarNotaActivity.this, "Nota editada con éxito", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditarNotaActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        };
                    }
                });
            };
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