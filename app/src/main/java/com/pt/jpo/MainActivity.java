package com.pt.jpo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonAccueil,buttonFormulaire,buttonPresentationmmi,buttonValidForm,buttonProfSalle,buttonVideos,buttoGeolocalisation;

    DataBase bddHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String extraValue = extras.getString(MapActivity.LAYOUT_MESSAGE);
            switch (extraValue) {
                case "layoutFormulaire":
                    setContentView(R.layout.formulaire);
                    initMarginAllLayout(findViewById(R.id.layoutFormulaire));

                    buttonValidForm = (Button) findViewById(R.id.validButtonForm);
                    buttonValidForm.setOnClickListener(this);
                    break;
                case "layoutPresentationMMI":
                    setContentView(R.layout.presentation_mmi);
                    initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
                    break;
                case "layoutProfSalle":
                    setContentView(R.layout.profsalle);
                    initMarginAllLayout(findViewById(R.id.layoutProfSalle));
                    break;
                case "layoutVideos":
                    setContentView(R.layout.videos);
                    initMarginAllLayout(findViewById(R.id.layoutVideos));
                    break;
                case "layoutAccueil":
                    setContentView(R.layout.accueil);
                    initMarginAllLayout(findViewById(R.id.layoutAccueil));
                    break;
                default:
                    setContentView(R.layout.accueil);
                    initMarginAllLayout(findViewById(R.id.layoutAccueil));
                    break;
                }
        }
        else{
            setContentView(R.layout.accueil);
            initMarginAllLayout(findViewById(R.id.layoutAccueil));
        }

        //initialise la base de donnée-helper
        bddHelper = new DataBase(this);
        bddHelper.getWritableDatabase();

        initButton();
    }

    public void initButton() {
        buttonAccueil = (Button) findViewById(R.id.accueil);
        buttonAccueil.setOnClickListener(this);
        buttonFormulaire = (Button) findViewById(R.id.formulaire);
        buttonFormulaire.setOnClickListener(this);
        buttonProfSalle = (Button) findViewById(R.id.profsalle);
        buttonProfSalle.setOnClickListener(this);
        buttonVideos = (Button) findViewById(R.id.videos);
        buttonVideos.setOnClickListener(this);
        buttoGeolocalisation = (Button) findViewById(R.id.localisation);
        buttoGeolocalisation.setOnClickListener(this);
    }

    /*
    * Initialiste le marginBottom de tous les layouts pour que le menu ne se positionne pas par dessus
    * N'est pas le même pour tous les écrans
     */
    private void initMarginAllLayout(View layout) {
        //recupere le height du menu
        LinearLayout layoutMenu = (LinearLayout)findViewById(R.id.layoutMenu);
        layoutMenu.measure(0,0);
        int heightMenu = layoutMenu.getMeasuredHeight();

        //ajoute un padding bottom a la vue
        LinearLayout linearLayout = (LinearLayout) layout;
        linearLayout.setPadding(0,0,0,heightMenu);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.formulaire: {
                setContentView(R.layout.formulaire);
                buttonValidForm = (Button) findViewById(R.id.validButtonForm);
                buttonValidForm.setOnClickListener(this);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutFormulaire));
                break;
            }
            case R.id.accueil: {
                setContentView(R.layout.accueil);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutAccueil));
                break;
            }
            case R.id.profsalle: {
                setContentView(R.layout.profsalle);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutProfSalle));
                break;
            }
            case R.id.videos: {
                setContentView(R.layout.videos);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutVideos));
                break;
            }
            case R.id.localisation: {
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.validButtonForm:{
                validForm();

                Toast.makeText(this,"Merci pour votre participation",Toast.LENGTH_SHORT).show();
                setContentView(R.layout.formulaire);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutFormulaire));
                break;

            }
        }
    }


    public void validForm() {
        String nom = ((EditText) findViewById(R.id.nom)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.prenom)).getText().toString();
        String lycee = ((EditText) findViewById(R.id.lycee)).getText().toString();
        String attentes = ((EditText) findViewById(R.id.attentes)).getText().toString();
        // le type de visiteur :
        RadioGroup type = (RadioGroup) findViewById(R.id.Type);
        int selectedId = type.getCheckedRadioButtonId();
        RadioButton typeChoisi = (RadioButton) findViewById(selectedId);
        String typeVis = typeChoisi.getText().toString();
        // jour venue
        RadioGroup types = (RadioGroup) findViewById(R.id.Date);
        int selectedIds = types.getCheckedRadioButtonId();
        RadioButton dateChoisie = (RadioButton) findViewById(selectedIds);
        String jour = dateChoisie.getText().toString();


        ContentValues values = new ContentValues();
        values.put("type", typeVis);
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("lycee", lycee);
        values.put("jour", jour);
        values.put("attente", attentes);

        bddHelper.insert_visiteur(typeVis, nom, prenom, lycee, jour, attentes);
        /*
        Cursor allVisiteurs = bddHelper.getAllVisiteurs();
        allVisiteurs.moveToFirst();
        Toast.makeText(this
                ,allVisiteurs.getString(allVisiteurs.getColumnIndex("nom"))+""
                        +allVisiteurs.getString(allVisiteurs.getColumnIndex("prenom")),
                Toast.LENGTH_SHORT).show();*/
    }
}
