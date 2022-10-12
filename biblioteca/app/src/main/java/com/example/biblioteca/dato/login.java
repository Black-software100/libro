package com.example.biblioteca.dato;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class login extends bd{
    Context context;

    public login(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public Cursor activo(Context context){

        bd conexion = new bd(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select*from user where activo = 1",null);
        if(cursor.getCount()>0)
            return cursor;
        else
            return cursor = null;
    }

    public Cursor ingresar(Context contex){


         return null;
    }

}
