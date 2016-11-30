package com.pt.jpo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bladeknight on 24/11/2016.
 */

public class DataBase extends SQLiteOpenHelper{

    public SQLiteDatabase db;

    //name -> nom du fichier de bdd

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE visiteurs( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "type TEXT, nom TEXT, prenom TEXT, lycee TEXT, jour TEXT, attente TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS visiteurs;");
        onCreate(sqLiteDatabase);
    }

    public void insert_visiteur(String type, String nom, String prenom, String lycee, String jour, String attente){
        String requeteInsert = "INSERT INTO visiteurs VALUES ("+type+","+nom
                +","+prenom+","+lycee+","+jour+","+attente+");";
        db.execSQL(requeteInsert);

    }
}
