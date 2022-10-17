package com.example.biblioteca.dato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Login extends Bd {
    Context context;

    public Login(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public String activo(Context context) {
       String Id;
        Bd conexion = new Bd(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("select Id from usuario where activo = 1", null);
        if (cursor.getCount() > 0){
            cursor.moveToNext();
            Id = cursor.getString(0);
            return Id;
        }else {
            return Id = "null";
        }
    }

    public boolean ingresar(Context context, String name, String id) {
        boolean resultado = false;

        Context CNX;

        String Name, Id;


        CNX = context;

        Name = name;

        Id = id;

        Bd conexion = new Bd(context);

        SQLiteDatabase db = conexion.getReadableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM usuario WHERE Id = '" + id + "'", null);

        if (cursor.getCount() > 0) {

            resultado = update(CNX, Id);

        } else {
            resultado = Insert(CNX, Name, Id);
        }

        return resultado;
    }

    private boolean Insert(Context cnx, String name, String id) {
        Bd conexion = new Bd(cnx);
        long resultado;
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Id", id);
        values.put("name", name);
        resultado = db.insert("usuario", null, values);

        if (resultado > 0)
            return true;
        else
            return false;

    }


    public boolean update(Context context, String id) {
        Bd conexion = new Bd(context);
        int resultado;
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("activo", 1);

        resultado = db.update("usuario", values, "Id = '" + id + "'", null);

        if (resultado > 0)
            return true;
        else
            return false;
    }

    public boolean sign_out(Context context) {
        int resultado;

        Bd conexion = new Bd(context);

        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("activo", 0);

        resultado = db.update("usuario", values, "Id ='" + 1 + "'", null);
        if (resultado > 0)
            return true;
        else
            return false;
    }

}
