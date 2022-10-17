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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;

import java.util.HashMap;
import java.util.Map;

public class Alquilar extends AppCompatActivity {
    EditText alquilar_email, alquilar_password;
    Button  alquilar_btn;
    String idBook;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquilar);
        alquilar_email = findViewById(R.id.email_alquilar);
        alquilar_password = findViewById(R.id.pass_alquilar);
        alquilar_btn = findViewById(R.id.btn_alquilar);
        idBook = getIntent().getStringExtra("id");

        alquilar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarWebService();
            }
        });
    }

    private void CargarWebService() {
        String Email =  alquilar_email.getText().toString().trim().toLowerCase();
        String Password =  alquilar_password.getText().toString().trim();

        if(!Email.isEmpty() && !Password.isEmpty()){
            dialog = new ProgressDialog(this);
            dialog.setMessage("Cargando...");
            dialog.show();
            String url = "http://192.168.1.8/libros/compra.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("true")) {
                        Toast.makeText(Alquilar.this, "libro alquilado", Toast.LENGTH_LONG).show();
                        dialog.hide();
                        Intent intent = new Intent(Alquilar.this, Index.class);
                        startActivity(intent);
                    } else if (response.equalsIgnoreCase("No se pudo hacer la modificacion")) {
                        Toast.makeText(Alquilar.this, "error al alquilar", Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }else if(response.equalsIgnoreCase("el libro no existe")){
                        Toast.makeText(Alquilar.this, "El libro no existe", Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }else if(response.equalsIgnoreCase("el usuario o la contraseña son incorrecto")){
                        Toast.makeText(Alquilar.this, "la contraseña o el correo es incorrecto", Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }
                }
            },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Alquilar.this, "No se pudo conecatra al servidor", Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<>();

                    param.put("email",Email);
                    param.put("password",Password);
                    param.put("idBook",idBook);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Alquilar.this);
            requestQueue.add(request);
        }else{
            Toast.makeText(Alquilar.this,"No dejar ningun espacio vacio",Toast.LENGTH_LONG).show();
        }
    }
}