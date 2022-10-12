package com.example.biblioteca.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biblioteca.R;
import com.example.biblioteca.adapter.BookAdapter;
import com.example.biblioteca.entidades.Book;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_home, container, false);
         ListaBook = new ArrayList<>();
         recyclerView = (RecyclerView) view.findViewById(R.id.recycleBook);
         recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
         recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
         requestQueue = Volley.newRequestQueue(getContext());
         CargarWebService();
         return view;
    }

    private void CargarWebService() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Consultando Imagenes");
        dialog.show();

        String url = "http://192.168.1.8/libros/LIBROS.PHP";
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