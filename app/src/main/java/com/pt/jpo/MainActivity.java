package com.pt.jpo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonFormulaire,buttonPresentationmmi,buttonValidForm,buttonProfSalle,buttonVideos,buttoGeolocalisation;

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
                default:
                    setContentView(R.layout.presentation_mmi);
                    initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
                    break;
                }
        }
        else{
            setContentView(R.layout.presentation_mmi);
            initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
        }

        bddHelper = new DataBase(this);
        bddHelper.getWritableDatabase();

        initButton();
    }

    public void initButton() {
        buttonFormulaire = (Button) findViewById(R.id.formulaire);
        buttonFormulaire.setOnClickListener(this);
        buttonPresentationmmi = (Button) findViewById(R.id.presentationmmi);
        buttonPresentationmmi.setOnClickListener(this);
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
            case R.id.presentationmmi: {
                setContentView(R.layout.presentation_mmi);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
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
        String nom = ((EditText)findViewById(R.id.nom)).getText().toString();
        String prenom = ((EditText)findViewById(R.id.prenom)).getText().toString();
        String lycee = ((EditText)findViewById(R.id.lycee)).getText().toString();
        String attentes = ((EditText)findViewById(R.id.attentes)).getText().toString();
        // le type de visiteur :
        RadioGroup type = (RadioGroup) findViewById(R.id.Type);
        int selectedId = type.getCheckedRadioButtonId();
        RadioButton typeChoisi= (RadioButton) findViewById(selectedId);
        String typeVis = typeChoisi.getText().toString();
        // jour venue
        RadioGroup types = (RadioGroup) findViewById(R.id.Date);
        int selectedIds = types.getCheckedRadioButtonId();
        RadioButton dateChoisie= (RadioButton) findViewById(selectedIds);
        String jour = dateChoisie.getText().toString();


        ContentValues values = new ContentValues();
        values.put("type", typeVis);
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("lycee", lycee);
        values.put("jour", jour);
        values.put("attente", attentes);

        bddHelper.insert_visiteur(typeVis, nom, prenom, lycee, jour, attentes);
    }


    /**
    public String getNom(){
        return ((EditText)findViewById(R.id.nom)).getText().toString();
    }
    public String getPrenom(){
        return ((EditText)findViewById(R.id.prenom)).getText().toString();
    }
    public String getLycee(){
        return ((EditText)findViewById(R.id.lycee)).getText().toString();
    }
    public String getAttentes(){
        return ((EditText)findViewById(R.id.attentes)).getText().toString();
    }
    public String getType(){
        RadioGroup type = (RadioGroup) findViewById(R.id.Type);
        int selectedId = type.getCheckedRadioButtonId();
        RadioButton typeChoisi= (RadioButton) findViewById(selectedId);

        return typeChoisi.getText().toString();
    }
    public String getDate(){
        RadioGroup type = (RadioGroup) findViewById(R.id.Date);
        int selectedId = type.getCheckedRadioButtonId();
        RadioButton dateChoisi= (RadioButton) findViewById(selectedId);

        return dateChoisi.getText().toString();
    }
     **/


}
