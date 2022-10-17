package com.example.biblioteca.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.entidades.Book;

import org.json.JSONArray;
import org.json.JSONObject;

public class InfoBook extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    //Constante editext
    TextView info_name, info_valor;

    //Cosntante imagen
    ImageView info_img;

    //Texto del id del libro
    String id;

    // Contante de dialogo
    ProgressDialog dialog;

    //Contante http
    RequestQueue requestQueue;

    //Método para enviar un array
    JsonObjectRequest jsonObjectRequest;

    //Botones para volver y alquilar
    Button info_compra, info_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobook);

        //obtiene la información del la layout
        info_volver = findViewById(R.id.volver_info);

        //obtiene la información del la layout
        info_compra = findViewById(R.id.compra_info);

        //obtiene el id del libro
        id = getIntent().getStringExtra("id");

        //obtine la infromacion de layout
        info_name = findViewById(R.id.name_infobook);

        //obtiene la infromacion de layout
        info_valor = findViewById(R.id.info_valor);

        //obtiene la infromacion de layout
        info_img = findViewById(R.id.img_infobook);

        //le damos el contexto de la clase
        requestQueue = Volley.newRequestQueue(this);

        //abrimos el método
        CargarWebService();

        // botón al hacer click
        info_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoBook.this, Index.class);
                startActivity(intent);
            }
        });
        // botón al hacer click
        info_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoBook.this, Alquilar.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    private void CargarWebService() {

        //le damos el contexto de dialogo
        dialog = new ProgressDialog(this);

        //le damos la información a mostrar
        dialog.setMessage("Consultando Imágenes");

        //abrimos el dialogo
        dialog.show();
        try{
            //inscribimos la URL de servidor
            String url = "http://192.168.1.8/libros/consultBook.php?id="+id+"";

            //le decimos el método a enviar
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

            //le damos el método para buscar
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            //
            e.toString();
        }
    }

    @Override
    public void onResponse(JSONObject response) {

        //clase para separar los datos
        Book book = null;

        //le decimos el nombre del array
        JSONArray json =response.optJSONArray("Book");

        //creamos un método para obtener el valor el array
        JSONObject jsonObject = null;

        try{
            //creamos el método para separar el array para JAVA
            book = new Book();

            //le colocamos que el array está el en índice cero
            jsonObject= json.getJSONObject(0);

            //obtenemos la información de la imagen
            book.setDato(jsonObject.optString("img"));

            //obtenemos la información del nombre
            book.setName(jsonObject.optString("name"));

            //obtenemos la información de la precio
            book.setPrice(jsonObject.optInt("price"));

            //le mandamos la imagen a layout
            info_img.setImageBitmap(book.getImg());

            //le mandamos el nombre al layout
            info_name.setText(book.getName());

            //le mandamos el precio al layout
            info_valor.setText(book.getPrice().toString());

            //cerramos el dialogo
            dialog.hide();

        }catch (Exception e){
            //sia hay un error nos muestra
            e.toString();

            //mostramos un mensaje que no se pudieron mostrar los datos
            Toast.makeText(this, "error al mostrar los datos ", Toast.LENGTH_SHORT).show();

            //cerramos el dialogo
            dialog.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        //mostramos un error del servidor
        Toast.makeText(this, "Error con el servidor", Toast.LENGTH_SHORT).show();

        //cerramos los dialogo
        dialog.hide();
    }
}