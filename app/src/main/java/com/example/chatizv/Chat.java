package com.example.chatizv;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatizv.controlador.RecyclerChat;
import com.example.chatizv.modelo.PaqueteDatos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    TextView txtUserInfo, txtSendMsg;
    Button bSend;

    Thread hEnviarMsg;
    Thread hServidor;

    RecyclerView contenedor;
    RecyclerChat recAdapter;
    ArrayList<PaqueteDatos> listMsg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        txtUserInfo = (TextView) findViewById(R.id.txtUserInfo);
        txtSendMsg = (TextView) findViewById(R.id.txtSendMsg);

        bSend = (Button) findViewById(R.id.bSend);

        contenedor = (RecyclerView)findViewById(R.id.contenedor);



        Intent i = getIntent();

        txtUserInfo.setText(i.getStringExtra("user"));

        i.putExtra("msg", txtSendMsg.getText());

        //Servidor
        hServidor = new Thread(new Runnable() {
            @Override
            public void run() {

                Intent i = getIntent();

                try {
                    String ip = i.getStringExtra("ip");

                    int puerto = Integer.parseInt(i.getStringExtra("puerto"));

                    ServerSocket ss = new ServerSocket(puerto);

                    while (true){
                        Socket s = ss.accept();

                        ObjectInputStream msgIn = new ObjectInputStream(s.getInputStream());

                        PaqueteDatos paqueteDatos = (PaqueteDatos) msgIn.readObject();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listMsg.add(paqueteDatos);
                                mostrarMsg();
                            }
                        });

                        //Cliente
                        Socket sCliente = new Socket(ip, puerto);

                        ObjectOutputStream msgOut = new ObjectOutputStream(sCliente.getOutputStream());

                        msgOut.writeObject(paqueteDatos);

                        s.close();

                        msgIn.close();

                        s.close();

                        ss.close();
                    }

                } catch (NumberFormatException | IOException | ClassNotFoundException ex){

                }
            }
        });

        hEnviarMsg = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = getIntent();

                try{
                    String ip = i.getStringExtra("ip");
                    int puerto = Integer.parseInt(i.getStringExtra("puerto"));

                    String usuario = i.getStringExtra("usuario");
                    String msg = i.getStringExtra("msg");

                    Socket s = new Socket(ip, puerto);

                    PaqueteDatos paqueteDatos = new PaqueteDatos(usuario, msg);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listMsg.add(paqueteDatos);
                            mostrarMsg();
                        }
                    });

                    ObjectOutputStream msgOut = new ObjectOutputStream(s.getOutputStream());



                    msgOut.writeObject(paqueteDatos);

                    msgOut.close();

                    s.close();

                } catch (NumberFormatException | IOException ex){

                }
            }
        });

        hServidor.start();

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hEnviarMsg.start();
            }
        });

    }

    private void mostrarMsg(){

        recAdapter = new RecyclerChat(listMsg);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        contenedor.setAdapter(recAdapter);

        contenedor.setLayoutManager(layoutManager);
    }
}
