package com.example.biblioteca.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.interfaces.Sign_in;
import com.example.biblioteca.interfaces.sign_up;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Config extends Fragment{


    View view;
    EditText email,password,password2;
    Button btn;
    ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_config, container, false);

        email = view.findViewById(R.id.email_config);
        password = view.findViewById(R.id.password_config);
        password2 = view.findViewById(R.id.password2_config);
        btn = view.findViewById(R.id.btn_info);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarWebService();
            }
        });
        return view;
    }

    private void CargarWebService() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Password2 = password2.getText().toString().trim();
        if (!Email.isEmpty() && !Password.isEmpty()) {
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Cargando...");
            dialog.show();
            String url = "http://192.168.1.8/libros/updatePASS.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("true")) {
                        Toast.makeText(getContext(), "el usuario registrado", Toast.LENGTH_LONG).show();
                        dialog.hide();
                        Intent intent = new Intent(getContext(), Sign_in.class);
                        startActivity(intent);
                    } else if (response.equalsIgnoreCase("false")) {
                        Toast.makeText(getContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "No se pudo conecatra al servidor", Toast.LENGTH_SHORT).show();
                    dialog.hide();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("email", Email);
                    param.put("password", Password);
                    param.put("upassword",Password2);
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
        }else{
            Toast.makeText(getContext(), "No dejar ningun espacio en blanco", Toast.LENGTH_SHORT).show();
        }
    }
}