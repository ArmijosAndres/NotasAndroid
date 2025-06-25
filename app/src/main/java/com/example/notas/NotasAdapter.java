package com.example.notas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//adaptador para el registro
public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolderNotas> {

    List<Nota> notasList;
    public NotasAdapter(List<Nota> notas){
        this.notasList = notas;
    }

    @NonNull
    @Override
    public NotasAdapter.ViewHolderNotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota_display_layout, parent, false);
        ViewHolderNotas holder = new ViewHolderNotas(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotasAdapter.ViewHolderNotas holder, int position) {

        Nota nota = notasList.get(position);
        holder.titulo.setText(nota.getTitulo());
        holder.contenido.setText(nota.getContenido());

    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class ViewHolderNotas extends RecyclerView.ViewHolder{

        TextView titulo, contenido;

        public ViewHolderNotas(@NonNull View itemView) {
            super(itemView);

            titulo=itemView.findViewById(R.id.txt_titulo_nota);
            contenido=itemView.findViewById(R.id.txt_contenido_nota);

        }
    }

}
