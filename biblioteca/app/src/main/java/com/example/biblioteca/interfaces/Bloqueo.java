package com.example.biblioteca.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.Out;
import com.example.biblioteca.R;
import com.example.biblioteca.interfaces.Index;

public class Bloqueo extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener{

    RequestQueue requestQueue;
    StringRequest request;
    String id;
    ConnectivityManager compat = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo);
        compat = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = compat.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            CargarWebService();
        }else{
            Intent intent = new Intent(this, Out.class);
            startActivity(intent);
            finish();
        }
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
            startActivity(intent);
        }
        finish();
    }}