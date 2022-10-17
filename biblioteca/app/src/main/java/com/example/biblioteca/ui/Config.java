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
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.dato.Login;
import com.example.biblioteca.interfaces.MainActivity;
import com.example.biblioteca.interfaces.Sign_in;

import java.util.HashMap;
import java.util.Map;


public class Config extends Fragment{


    View view;
    EditText email,password,password2;
    ImageButton btn_salir;
    ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_config, container, false);

        btn_salir = view.findViewById(R.id.config_salir);

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                salir();
            }
        });

        return view;
    }

    public void  salir(){
        boolean resultado;
        Login login = new Login(getContext());
        resultado =  login.sign_out(getContext());
        if(resultado){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
        }

    }

}