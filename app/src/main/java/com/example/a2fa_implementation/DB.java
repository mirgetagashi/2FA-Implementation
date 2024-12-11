package com.example.a2fa_implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class DB extends SQLiteOpenHelper {


    public DB(@Nullable Context context){
        super(context,"login.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table users(email text primary key, firstname text, lastname text, password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    public  boolean insertData(String email, String firstname, String lastname, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        String hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt());
        contentValues.put("email",email);
        contentValues.put("password",hashedPassword);
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);

        long result= db.insert("users",null, contentValues);
        return result!=-1;

    }

    public boolean checkEmail(String email){
        SQLiteDatabase db= this.getReadableDatabase();
        db.execSQL("delete from users where email='mirgetaagashi15@gmail.com'");
        Cursor cursor= db.rawQuery("select * from users where email=?", new String[] {email});
        return cursor.getCount()>0;
    }

    public Boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM users WHERE email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(0);
            cursor.close();
            return BCrypt.checkpw(password, storedHashedPassword);
        }
        cursor.close();
        return false;
    }
}
