package com.example.notas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //instansear txt
        EditText email=findViewById(R.id.txt_email);
        EditText password=findViewById(R.id.txt_password);

        //instansear botones
        Button btn_login=findViewById(R.id.btn_acceder);
        TextView registro=findViewById(R.id.btn_crear_cuenta);

        registro.setOnClickListener(v ->{

            Intent intent=new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);

        });

        btn_login.setOnClickListener(v ->{
            String email1=email.getText().toString();
            String password1=password.getText().toString();

            if (TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)){
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(MainActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        };
                    };
                });
            };

        });

        //Lineas para validar la sesion o sea si ya inicio

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(MainActivity.this, InicioActivity.class);
            startActivity(intent);
            finish();
        }else {

        };

    }

    //

}