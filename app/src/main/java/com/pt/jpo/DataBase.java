package com.pt.jpo;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import static com.pt.jpo.R.layout.formulaire;

/**
 * Created by Bladeknight on 24/11/2016.
 */

public class DataBase extends SQLiteOpenHelper{
    public MainActivity main;

    public static final String id = "id";
    public static final String type = "type";
    public static final String nom = "nom";
    public static final String prenom = "prenom";
    public static final String lycee = "lycee";
    public static final String date = "date";
    public static final String attente = "attente";

    public String typeValue;
    public String nomValue;
    public String prenomValue;
    public String lyceeValue;
    public String dateValue;
    public String attenteValue;

    public String createTable = "CREATE TABLE JPO (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                    type + " TEXT, " +
                                                                    nom + " TEXT, " +
                                                                    prenom + " TEXT, " +
                                                                    lycee + " TEXT, " +
                                                                    date + " TEXT, " +
                                                                    attente + " TEXT );";
    public String insertTable = "INSERT INTO JPO ("+type+","+nom+","+prenom
            +","+lycee+","+date+","+attente+") VALUES ("+typeValue+","+nomValue
            +","+prenomValue+","+lyceeValue+","+dateValue+","+attenteValue+");";

    public static final String JPO_TABLE_DROP = "DROP TABLE IF EXISTS JPO;";


    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version, MainActivity main) {
        super(context, name, factory, version);
        this.main = main;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(JPO_TABLE_DROP);
        onCreate(db);
    }

    public void sendForm() {
        //recupere toutes les valeurs des textEdits
        nomValue = main.getNom();
        prenomValue = main.getPrenom();
        lyceeValue = main.getLycee();
        attenteValue = main.getAttentes();
        typeValue = main.getType();
        dateValue = main.getDate();
    }
}
