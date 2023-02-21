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
    public void cargarwebservice() {
        String Name = name.getText().toString().trim();
        //
        String Old_year = old_year.getText().toString();
        //
        String Email = email.getText().toString().trim().toLowerCase();
        //
        String Addres = addres.getText().toString().trim();
        //
        String Password = password.getText().toString().trim();
        //
        String Password2 = password2.getText().toString().trim();
        //
        String Documento = documento.getText().toString().trim();

        Name_error.setErrorEnabled(false);

        Old_error.setErrorEnabled(false);

        Doc_error.setErrorEnabled(false);

        Adress_error.setErrorEnabled(false);

        Email_error.setErrorEnabled(false);

        Password_error.setErrorEnabled(false);

        Password2_error.setErrorEnabled(false);

        if(Name.isEmpty()){

            Name_error.setError(Strings.NAME_ERROR);

        }else if(Old_year.isEmpty()){

            Old_error.setError(Strings.OLD_ERROR);

        }else if(Documento.isEmpty()){

            Doc_error.setError(Strings.DOC_ERROR);

        }else if(Email.isEmpty()){
            Email_error.setError(Strings.EMAIL_ERROR);


        }else if(Addres.isEmpty()){

            Adress_error.setError(Strings.ADRESS_ERROR);

        }else if(Password.isEmpty()){

            Password_error.setError(Strings.PASSWORD_ERROR);

        }else if(Password2.isEmpty()){

            Password2_error.setError(Strings.PASSWORD_ERROR);

        }else if(!PatternsCompat.EMAIL_ADDRESS.matcher(Email).matches()){

            Email_error.setError(Strings.EMAIL_INALIDE);
        }else if(!Patronnes.Passwordpatter.matcher(Password).matches()) {
            Email_error.setError(Strings.PASSWORD_INVALIDE);
        }
        else{

            if (Password.equals(Password2)) {

                dialog = new ProgressDialog(this);

                dialog.setMessage("Cargando...");

                dialog.show();

                final  String url = "http://192.168.1.8/libros/movil/register.php";

                stringRequest = new StringRequest(Request.Method.POST,url,this,this){
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
                //le damos el método para buscar
                requestQueue.add(stringRequest);
            }else {
                Password2_error.setError(Strings.PASSWORD_ERROR);
            }
        }
    }

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