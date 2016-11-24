package com.pt.jpo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import static com.pt.jpo.R.layout.formulaire;

/**
 * Created by Bladeknight on 24/11/2016.
 */

public class DataBase extends SQLiteOpenHelper{
    public static final String id = "id";
    public static final String type = "type";
    public static final String nom = "nom";
    public static final String prenom = "prenom";
    public static final String lycee = "lycee";
    public static final String date = "date";
    public static final String attente = "attente";

    public static final String createTable = "CREATE TABLE JPO (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                    type + " TEXT, " +
                                                                    nom + " TEXT, " +
                                                                    prenom + " TEXT, " +
                                                                    lycee + " TEXT, " +
                                                                    date + " TEXT, " +
                                                                    attente + " TEXT );";

    public static final String METIER_TABLE_DROP = "DROP TABLE IF EXISTS JPO;";


    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(METIER_TABLE_DROP);
        onCreate(db);
    }

    public void sendForm() {

    }
}
