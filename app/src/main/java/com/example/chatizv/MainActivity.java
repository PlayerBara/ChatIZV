package com.example.chatizv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private String ip;
    private int puerto;

    TextView txtNewIp, txtNewPuerto, txtNewUser;
    Button bNewConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obtencion_datos);

        txtNewIp = (TextView) findViewById(R.id.txtNewIp);
        txtNewPuerto = (TextView) findViewById(R.id.txtNewPuerto);
        txtNewUser = (TextView) findViewById(R.id.txtNewUser);

        bNewConnect = (Button) findViewById(R.id.bNewConnect);

        try {
            ip = txtNewIp.getText().toString();
            puerto = Integer.parseInt(txtNewPuerto.getText().toString());
        } catch (NumberFormatException ex){
            Log.d(ex.toString(), "Error de formato de puerto, debe ser un int");
        }


        bNewConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Chat.class);

                i.putExtra("ip", ip);
                i.putExtra("puerto", puerto);
                i.putExtra("user", txtNewUser.getText().toString());

                startActivity(i);
            }
        });
    }
}