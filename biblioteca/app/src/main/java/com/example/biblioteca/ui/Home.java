package com.example.biblioteca.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.adapter.BookAdapter;
import com.example.biblioteca.entidades.Book;
import com.example.biblioteca.interfaces.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Home extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    View view;

    RecyclerView recyclerView;
    ArrayList<Book>ListaBook;

    ProgressDialog dialog;

    RequestQueue requestQueue;

    JsonObjectRequest jsonObjectRequest;


    ConnectivityManager compat = null;

    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = view.findViewById(R.id.sign_out);

        imageView.setVisibility(View.INVISIBLE);

        ListaBook = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleBook);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);

        recyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(getContext());





        compat = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = compat.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){
            CargarWebService();
            imageView.setVisibility(View.INVISIBLE);
        }else{
            imageView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "error en la conexion", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void CargarWebService() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Consultando Imagenes");
        dialog.show();

        String url = "http://192.168.1.8/libros/libros.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Book book = null;
        JSONArray json =response.optJSONArray("Books");

        try{
            for(int i=0;i<json.length();i++){
                book = new Book();
                JSONObject jsonObject = null;
                jsonObject= json.getJSONObject(i);
                book.setId((jsonObject.optInt("idBook")));
                book.setDato(jsonObject.optString("img"));
                book.setName(jsonObject.optString("name"));
                book.setPrice(jsonObject.optInt("price"));
                ListaBook.add(book);
            }
            dialog.hide();
            BookAdapter adapter = new BookAdapter(ListaBook,getContext());
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.toString();
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            dialog.hide();
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "vali madre", Toast.LENGTH_SHORT).show();
        dialog.hide();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}