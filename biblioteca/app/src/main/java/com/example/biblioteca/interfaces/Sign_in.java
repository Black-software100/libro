package com.example.biblioteca.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;


import org.json.JSONArray;
import org.json.JSONObject;

public class Sign_in extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    ProgressDialog dialog;
    Button btnSign_in, btnSign_up;
    String text_label = "";
    EditText EdtName, Edtpass;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EdtName = findViewById(R.id.email_Sing_in);
        Edtpass = findViewById(R.id.pass_Sign_in);
        requestQueue = Volley.newRequestQueue(this);
        btnSign_in = findViewById(R.id.btn_sign_in);
        btnSign_up = findViewById(R.id.btn_sign_up);

        btnSign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarWebService();
            }
        });

        btnSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_in.this, sign_up.class);
                startActivity(intent);
            }
        });
    }

    private void CargarWebService() {
      String  email = EdtName.getText().toString().trim() ;
      String password = Edtpass.getText().toString().trim();
      if(!email.isEmpty() && !password.isEmpty()){
          dialog = new ProgressDialog(this);
          dialog.setMessage("Consultando usuario");
          dialog.show();
          try{
              String url = "http://192.168.1.8/libros/consultaUser.php?email="+email+"&password="+password+"";
              jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
              requestQueue.add(jsonObjectRequest);
          }catch (Exception e){
              e.toString();
          }
      }else{
          Toast.makeText(this,"no dejar ningun espacio blnaco",Toast.LENGTH_LONG).show();
      }

    }
    @Override
    public void onResponse(JSONObject response) {
        JSONArray json =response.optJSONArray("user");
        try {
            if(json.length()>-1){
                Intent intent = new Intent(Sign_in.this,index.class);
                startActivity(intent);
                dialog.hide();
                EdtName.setText("");
                Edtpass.setText("");

            }
        }catch (Exception e) {
            e.toString();
            dialog.hide();
        }
    }





    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        dialog.hide();
    }


}