package com.example.biblioteca.interfaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.dato.Login;
import com.example.biblioteca.entidades.User;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.EOFException;
import java.util.HashMap;
import java.util.Map;

public class Sign_in extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

     ProgressDialog dialog;
     Button btnSign_in, btnSign_up;
     EditText edtEmail, edtpass;
     RequestQueue requestQueue;
     String name,id;
     User user = null;
    JsonRequest stringRequest ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //obtiene la información del la layout
        edtEmail = findViewById(R.id.email_Sing_in);

       //obtiene la información del la layout
        edtpass = findViewById(R.id.pass_Sign_in);

        //le damos el contexto de la clase
        requestQueue = Volley.newRequestQueue(this);

        //obtiene la infromacion de layout
        btnSign_in = findViewById(R.id.btn_sign_in);

        //obtiene la infromacion de layout
        btnSign_up = findViewById(R.id.btn_sign_up);

        //botón al hacer click ingresar
        btnSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {CargarWebService();}catch (EOFException e){}
            }
        });

        //botón al hacer click update
        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_in.this, Sign_up.class);
                startActivity(intent);
            }
        });

    }

    private void CargarWebService() throws EOFException {
      //
      String  email = edtEmail.getText().toString().trim() ;
      //
      String password = edtpass.getText().toString().trim();
      //
      if(email.isEmpty() && password.isEmpty()){
          Toast.makeText(this,"no dejar ningun espacio blanco",Toast.LENGTH_LONG).show();
          throw  new EOFException();
      }
      dialog = new ProgressDialog(this);

      dialog.setMessage("Consultando usuario");

      dialog.show();

      final String url = "http://192.168.1.8/libros/";

        stringRequest = new JsonObjectRequest(Request.Method.POST,url,null,this,this){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("name",email);
                param.put("old_year",password);
                return param;
            }
        };
        //le damos el método para buscar
        requestQueue.add(stringRequest);


    }
    @Override
    public void onResponse(JSONObject response) {

        //le decimos el nombre del array
        JSONArray json =response.optJSONArray("user");


        // hacemos un try catch ya que
        try {

            //hacemos un codicional if para saber si el usuario existe
            if(json.length()>-1){

                JSONObject jsonObject = null;

                jsonObject= json.getJSONObject(0);

                user = new User();

                //obtenemos la información de la imagen
                user.setId(jsonObject.optString("idUser"));

                //
                user.setName(jsonObject.optString("name"));

                //
                proceso();

            }
        }catch (Exception e) {

            e.toString();

            dialog.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //mostramos un mensaje del servidor
        Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        // cerramos la dialogo
        dialog.hide();
    }

    public void proceso (){
        Login login = new Login(this);

        boolean respuesta;

        name = user.getName();

        //
        id = user.getId();

       respuesta = login.ingresar(this,name,id);

        if(respuesta){
            edtEmail.setText("");
            edtpass.setText("");
            dialog.hide();
            Intent intent = new Intent(Sign_in.this,Index.class);
            startActivity(intent);

        }else{
            Toast.makeText(Sign_in.this,"problesmas",Toast.LENGTH_LONG).show();
        }
    }
}