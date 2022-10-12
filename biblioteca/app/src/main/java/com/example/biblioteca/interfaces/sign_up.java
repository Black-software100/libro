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
import java.util.Locale;
import java.util.Map;


public class sign_up extends AppCompatActivity {
    EditText name,old_year,email,addres,password,password2;
    Button btnSign_up,btnsing_in;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name_sign_up);
        email = findViewById(R.id.email_sing_up);
        addres = findViewById(R.id.addres_sing_up);
        old_year = findViewById(R.id.old_year_sign_up);
        password = findViewById(R.id.password_sign_up);
        password2 = findViewById(R.id.password2_sign_up);
        btnSign_up = findViewById(R.id.btnSign_up);
        btnsing_in = findViewById(R.id.btnSign_in);

        btnsing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_up.this,Sign_in.class);
                startActivity(intent);
            }
        });

        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarwebservice();
            }
        });
    }

    public void cargarwebservice(){
      String Name =  name.getText().toString().trim();
      String Old_year = old_year.getText().toString();
      String Email =  email.getText().toString().trim().toLowerCase();
      String Addres =  addres.getText().toString().trim();
      String Password =  password.getText().toString().trim();
      String Password2 =  password2.getText().toString().trim();

        if(!Name.isEmpty()&& !Old_year.isEmpty()&& !Email.isEmpty()&& !Addres.isEmpty()&& !Password.isEmpty()&& !Password2.isEmpty()){
            if(Password.equals(Password2)){
                dialog = new ProgressDialog(this);
                dialog.setMessage("Cargando...");
                dialog.show();
                String url = "http://192.168.1.8/libros/register.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("true")) {
                            Toast.makeText(sign_up.this, "el usuario registrado", Toast.LENGTH_LONG).show();
                            dialog.hide();
                            Intent intent = new Intent(sign_up.this, Sign_in.class);
                            startActivity(intent);
                        } else if (response.equalsIgnoreCase("false")) {
                            Toast.makeText(sign_up.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(sign_up.this, "No se pudo conecatra al servidor", Toast.LENGTH_SHORT).show();
                        dialog.hide();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();

                        param.put("name",Name);
                        param.put("old_year",Old_year);
                        param.put("addres",Addres);
                        param.put("email",Email);
                        param.put("password",Password);
                        return param;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(sign_up.this);
                requestQueue.add(request);
            }else{
                Toast.makeText(this,"La contraseña no coniciden",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "No dejar ningun espacio en blanco", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}