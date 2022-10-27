package com.example.biblioteca.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioteca.R;
import com.example.biblioteca.entidades.Book;
import com.example.biblioteca.interfaces.InfoBook;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    List<Book> listaBook;
    Context context;
    public BookAdapter(List<Book> listaBook, Context context) {
        this.listaBook = listaBook;
        this.context = context;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new BookHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {

        holder.imageView.setImageBitmap(listaBook.get(position).getImg());

        holder.nameView.setText(listaBook.get(position).getName());

        holder.PriceView.setText(listaBook.get(position).getPrice().toString());
        try {
            holder.llBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int book = listaBook.get(position).getId();
                    String post = String.valueOf(book);
                    Intent intent = new Intent(context, InfoBook.class);
                    intent.putExtra("id",post);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.toString();
        }

    }

    @Override
    public int getItemCount() {
        return listaBook.size();
    }

    public  class  BookHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView,PriceView;
        LinearLayout llBook;
        public BookHolder (View iteamView){
            super(iteamView);
            imageView = iteamView.findViewById(R.id.fotoImageView);
            nameView =  iteamView.findViewById(R.id.nombreTexview);
            PriceView = iteamView.findViewById(R.id.valorTexview);
            llBook = iteamView.findViewById(R.id.llBook);
        }
    }
}
