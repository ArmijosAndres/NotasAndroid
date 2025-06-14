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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //instansear cajas de texto

        EditText username=findViewById(R.id.txt_username_registrar);
        EditText email=findViewById(R.id.txt_email_registrar);
        EditText password=findViewById(R.id.txt_password_registrar);


        //instansear boton

        Button btn_crear_account=findViewById(R.id.btn_registrarse);
        TextView login=findViewById(R.id.btn_registrarse);


        //boton con el metodo

        login.setOnClickListener(v ->{
            Intent intent=new Intent(RegistroActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btn_crear_account.setOnClickListener(v ->{
            String username1=username.getText().toString();
            String email1=email.getText().toString();
            String password1=password.getText().toString();

            if (TextUtils.isEmpty(username1) || TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)){
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //task es el resultado

                        if (task.isSuccessful()){
                            String currenUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Map<String, String> user = new HashMap<>();
                            user.put("username", username1);
                            user.put("email", email1);
                            FirebaseDatabase.getInstance().getReference().child("users").child(currenUserId).setValue(user);
                            Intent intent=new Intent(RegistroActivity.this, InicioActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        };

                    };
                });

            };
        });

    }

    //

}