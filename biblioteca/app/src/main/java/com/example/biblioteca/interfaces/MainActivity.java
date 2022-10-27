package com.example.biblioteca.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.Out;
import com.example.biblioteca.R;
import com.example.biblioteca.dato.Login;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    StringRequest request;
    String id;

    ConnectivityManager compat = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                cargar();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea,1000);
    }


    private void  cargar(){
        compat = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = compat.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){

            Login login = new Login(MainActivity.this);
            id = login.activo(MainActivity.this);
            Intent intent;
            if(!id.equals("null")){
                intent = new Intent(MainActivity.this, Index.class);
                startActivity(intent);
            }else{
                intent = new Intent(MainActivity.this, Sign_in.class);
                startActivity(intent);
            }
        }else {
            Intent intent = new Intent(this, Out.class);
            startActivity(intent);
        }
        finish();
    }





}



