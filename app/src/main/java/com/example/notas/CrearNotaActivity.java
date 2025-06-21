package com.example.notas;

import android.content.Intent;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CrearNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //instansear cajas de texto
        EditText titulo=findViewById(R.id.txt_titulo);
        EditText contenido=findViewById(R.id.txt_contenido);

        Button guardar=findViewById(R.id.btn_guardar);

        String currentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        //funcion de guardar
        guardar.setOnClickListener(v -> {

            //
            String tituloNota=titulo.getText().toString();
            String contenidoNota=contenido.getText().toString();

            if(TextUtils.isEmpty(tituloNota) || TextUtils.isEmpty(contenidoNota)){

                //enviamos un mensaje del error
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();

            }else {

                //guardar en bd
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                //variable para almacenar en db
                String IdNota = db.child("notas").push().getKey();

                //mapeo
                Map<String, String>nota = new HashMap<>();
                //alistamos los datos
                nota.put("titulo", tituloNota);
                nota.put("contenido", contenidoNota);

                //metodo
                db.child("notas").child(currentUserId).child(IdNota).setValue(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            //mensaje de creado exitosamente
                            Toast.makeText(CrearNotaActivity.this, "Nota creada con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CrearNotaActivity.this, InicioActivity.class);

                            startActivity(intent);

                        }else {

                            Toast.makeText(CrearNotaActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        };

                    };
                });

                FirebaseDatabase.getInstance().getReference().child("notas").child(currentUserId);

            };

        });

    }

    //

}