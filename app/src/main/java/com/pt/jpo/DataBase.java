package com.pt.jpo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bladeknight on 24/11/2016.
 */

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context) {
        super(context, "jpoBDD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE if not exists visiteurs( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "type TEXT, nom TEXT, prenom TEXT, lycee TEXT, jour TEXT, attente TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS visiteurs;");
        onCreate(sqLiteDatabase);
    }

    public void insert_visiteur(String type, String nom, String prenom, String lycee, String jour, String attente){
        /*String requeteInsert = "INSERT INTO visiteurs VALUES ("+type+","+nom
                +","+prenom+","+lycee+","+jour+","+attente+");";
        db.execSQL(requeteInsert);*/

        SQLiteDatabase bdd = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("lycee", lycee);
        values.put("jour", jour);
        values.put("attente", attente);

        bdd.insert("visiteurs", null, values);
        bdd.close();
    }

    //return la liste de tous les visiteurs
    public Cursor getAllVisiteurs(){
        String query = "SELECT  * FROM visiteurs";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
