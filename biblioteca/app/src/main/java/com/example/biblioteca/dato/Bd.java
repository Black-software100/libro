package com.example.biblioteca.dato;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Bd extends SQLiteOpenHelper {
    static final String DATA_NAME = "book";
    static final int DATA_VERSION =1;

    public Bd(@Nullable Context context) {
        super(context, DATA_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usuario = "CREATE TABLE usuario(Id text PRIMARY KEY, name text,  activo bit default 1)";
        String mensaje = "create TABLE mensaje(activo bit default 0)";
        db.execSQL(usuario);
        db.execSQL(mensaje);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE mensaje");
        onCreate(db);
    }
}
