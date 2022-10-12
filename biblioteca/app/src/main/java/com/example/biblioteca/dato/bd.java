package com.example.biblioteca.dato;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bd extends SQLiteOpenHelper {
    static final String DATA_NAME = "book";
    static final int DATA_VERSION =1;

    public bd(@Nullable Context context) {
        super(context, DATA_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usuario = "create table user(email varchar(255),password varchar(255), activo bit)";
        db.execSQL(usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table usuario");
        onCreate(db);
    }
}
