package com.example.chatizv.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatizv.modelo.PaqueteDatos;
import com.example.chatizv.R;

import java.util.List;

public class RecyclerChat extends RecyclerView.Adapter<RecyclerChat.RecyclerHolder>{

    private List<PaqueteDatos> mensajes;

    public RecyclerChat(List<PaqueteDatos> listMsg){
        this.mensajes = listMsg;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        PaqueteDatos msg = mensajes.get(position);

        holder.txtUser.setText(msg.getUsuario());
        holder.txtMsg.setText(msg.getMensaje());

    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView txtUser;
        TextView txtMsg;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            //Se enlaza los elementos del layout
            txtUser = (TextView) itemView.findViewById(R.id.txtUser);
            txtMsg = (TextView) itemView.findViewById(R.id.txtMsg);
        }
    }
}
