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

public class MainActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener{

    RequestQueue requestQueue;
    StringRequest request;
    String id;
    SwipeRefreshLayout swipeRefreshLayout;
    ConnectivityManager compat = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.resfresh);

        requestQueue = Volley.newRequestQueue(this);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                cargar();
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargar();
                swipeRefreshLayout.setRefreshing(false);
            }
        });




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

                CargarWebService();
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


    private void CargarWebService() {

        try{
            //inscribimos la URL de servidor
            String url = "http://192.168.1.8/libros/bloqueado.php?id="+id+"";
            //le decimos el método a enviar
            request = new StringRequest(Request.Method.GET,url,this,this);
            //le damos el método para buscar
            requestQueue.add(request);

        }catch (Exception e){
            // Mostrar el error
            e.toString();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "error con el servidor intente mas tarde", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        Intent intent ;
        if (response.equalsIgnoreCase("true")){
             intent = new Intent(this, Index.class);
        }else{
             intent = new Intent(this,Bloqueo.class);
        }
        startActivity(intent);

        finish();
    }
}



