package com.example.biblioteca.interfaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
import com.example.biblioteca.Tools.Patronnes;
import com.example.biblioteca.Tools.Strings;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Sign_up extends AppCompatActivity  implements Response.Listener<String>, Response.ErrorListener{
    //------------------------- INICIO JVES1.0  ----------------------------------------------------
    EditText name,oldYear,email,addres,password,password2, document;

    TextInputLayout nameError,documentError,oldError,adressError,emailError,passwordError
            ,password2Error;

    Button btnSignUp,btnsingIn;

    ProgressDialog dialog;

    StringRequest  stringRequest ;

    RequestQueue requestQueue;
    // -------------------------------FIN JVES1.0  -------------------------------------------------

    // -------------------------------------- Inicio JVFN1.0 ----------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameError = findViewById(R.id.Name_Error_Sign_up);

        documentError = findViewById(R.id.Doc_Error_Sign_up);

        oldError = findViewById(R.id.Old_year_Error_Sign_up);

        emailError = findViewById(R.id.Email_Error_Sign_up);

        adressError = findViewById(R.id.Addres_Error_Sign_up);

        passwordError = findViewById(R.id.Password_Error_Sign_up);

        password2Error = findViewById(R.id.Password2_Error_Sign_up);

        name = findViewById(R.id.name_sign_up);

        email = findViewById(R.id.email_sing_up);

        document = findViewById(R.id.doc_sign_up);

        addres = findViewById(R.id.addres_sing_up);

        oldYear = findViewById(R.id.old_year_sign_up);

        password = findViewById(R.id.password_sign_up);

        password2 = findViewById(R.id.password2_sign_up);

        btnSignUp = findViewById(R.id.btnSign_up);

        btnsingIn = findViewById(R.id.btnSign_in);

        requestQueue = Volley.newRequestQueue(this);

        //--------------------------- Inicio JVES1.0 -----------------------------------------------
        btnsingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this,Sign_in.class);
                startActivity(intent);

            }
        });
        // -----------------------------FIN JVES1.0 ------------------------------------------------


        // -----------------------------Inicio JVES1.0 ---------------------------------------------
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarwebservice();
            }
        });
    }
    //------------------------- FIN JVFN1.0 --------------------------------------------------------

    // -------------------------- Inicio JVFN1.0 ---------------------------------------------------
    public void cargarwebservice() {
        String nameString = name.getText().toString().trim();
        //
        String oldYearString = oldYear.getText().toString();
        //
        String emailString = email.getText().toString().trim().toLowerCase();
        //
        String addresString = addres.getText().toString().trim();
        //
        String passwordString = password.getText().toString().trim();
        //
        String password2String = password2.getText().toString().trim();
        //
        String documentoString = document.getText().toString().trim();

        nameError.setErrorEnabled(false);

        oldError.setErrorEnabled(false);

        documentError.setErrorEnabled(false);

        adressError.setErrorEnabled(false);

        emailError.setErrorEnabled(false);

        passwordError.setErrorEnabled(false);

        password2Error.setErrorEnabled(false);

        if(nameString.isEmpty()){
            nameError.setError(Strings.NAME_ERROR);
            throw  null;

        }if(oldYearString.isEmpty()){
            oldError.setError(Strings.OLD_ERROR);
            throw  null;

        }if(documentoString.isEmpty()){
            documentError.setError(Strings.DOC_ERROR);
            throw  null;

        }if(emailString.isEmpty()){
            emailError.setError(Strings.EMAIL_ERROR);
            throw  null;

        }if(addresString.isEmpty()){
            adressError.setError(Strings.ADRESS_ERROR);
            throw  null;

        }if(passwordString.isEmpty()){
            passwordError.setError(Strings.PASSWORD_ERROR);
            throw  null;

        }if(password2String.isEmpty()){
            password2Error.setError(Strings.PASSWORD_ERROR);
            throw  null;

        }if(!PatternsCompat.EMAIL_ADDRESS.matcher(emailString).matches()){
            emailError.setError(Strings.EMAIL_INALIDE);
            throw  null;

        }if(!Patronnes.Passwordpatter.matcher(passwordString).matches()) {
            emailError.setError(Strings.PASSWORD_INVALIDE);
            throw  null;
        }

        if (!passwordString.equals(password2String)) {
            password2Error.setError(Strings.PASSWORD_ERROR);
            throw  null;
        }


            dialog = new ProgressDialog(this);

            dialog.setMessage("Cargando...");

            dialog.show();

            final  String url = "http://192.168.1.8/libros/movil/register.php";

            stringRequest = new StringRequest(Request.Method.POST,url,this,this){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<>();
                    param.put("name",nameString);
                    param.put("old_year",oldYearString);
                    param.put("addres",addresString);
                    param.put("email",emailString);
                    param.put("password",passwordString);
                    return param;
                }
            };
            //le damos el método para buscar
            requestQueue.add(stringRequest);

    }
    // --------------------------------------- FIN JVFN1.0 -----------------------------------------
    @Override
    public void onResponse(String response) {
        if (response.equalsIgnoreCase("true")) {

            Toast.makeText(Sign_up.this, "el usuario registrado", Toast.LENGTH_LONG).show();

            dialog.hide();

            Intent intent = new Intent(Sign_up.this, Sign_in.class);

            startActivity(intent);

            finish();

        } else if (response.equalsIgnoreCase("false")) {

            Toast.makeText(Sign_up.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

            dialog.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(Sign_up.this, "No se pudo conecatra al servidor", Toast.LENGTH_SHORT).show();
        dialog.hide();
    }




}