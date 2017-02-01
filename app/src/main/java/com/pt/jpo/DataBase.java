package com.pt.jpo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.StringTokenizer;

//    /data/data/com.pt.jpo/databases/jpoBDD

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
                                "nom TEXT, prenom TEXT, mail TEXT, departement TEXT, bacVis TEXT, motivation TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS visiteurs;");
        onCreate(sqLiteDatabase);
    }

    public void insert_visiteur(String nom, String prenom, String mail, String departement, String bacVis, String motivation){
        /*String requeteInsert = "INSERT INTO visiteurs VALUES ("+type+","+nom
                +","+prenom+","+lycee+","+jour+","+attente+");";
        db.execSQL(requeteInsert);*/

        SQLiteDatabase bdd = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("mail", mail);
        values.put("departement", departement);
        values.put("bac", bacVis);
        values.put("motivation", motivation);

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
