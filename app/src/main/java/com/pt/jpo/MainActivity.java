package com.pt.jpo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonAccueil,buttonFormulaire,buttonPresentationmmi,buttonValidForm,buttonProfSalle,buttonVideos,buttoGeolocalisation;

    DataBase bddHelper;

    //variable pour le count down
    private Date endDate, startDate;
    private long j,h,m,s, reste;
    CountDownTimer mCountDownTimer;

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
                    launchCountDown();
                    break;
                default:
                    setContentView(R.layout.accueil);
                    initMarginAllLayout(findViewById(R.id.layoutAccueil));
                    launchCountDown();
                    break;
                }
        }
        else{
            setContentView(R.layout.accueil);
            initMarginAllLayout(findViewById(R.id.layoutAccueil));
            launchCountDown();
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
        String mail = ((EditText) findViewById(R.id.mail)).getText().toString();
        String departement = ((EditText) findViewById(R.id.departement)).getText().toString();
        String motivation = ((EditText) findViewById(R.id.motivation)).getText().toString();
        // le bac du visiteur :
        RadioGroup bac = (RadioGroup) findViewById(R.id.bac);
        int selectedId = bac.getCheckedRadioButtonId();
        RadioButton typeChoisi = (RadioButton) findViewById(selectedId);
        String bacVis = typeChoisi.getText().toString();


        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("mail", mail);
        values.put("departement", departement);
        values.put("bac", bacVis);
        values.put("motivation", motivation);

        bddHelper.insert_visiteur(nom, prenom, mail, departement, bacVis, motivation);
        /*
        Cursor allVisiteurs = bddHelper.getAllVisiteurs();
        allVisiteurs.moveToFirst();
        Toast.makeText(this
                ,allVisiteurs.getString(allVisiteurs.getColumnIndex("nom"))+""
                        +allVisiteurs.getString(allVisiteurs.getColumnIndex("prenom")),
                Toast.LENGTH_SHORT).show();*/
    }

    public void launchCountDown(){
        //prepare la date
        Calendar c = Calendar.getInstance();
        startDate = new Date();
        startDate.setTime(c.getTimeInMillis());
        c.set(Calendar.YEAR, 2017);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 4);
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        endDate = new Date();
        endDate.setTime(c.getTimeInMillis());

        //calcule pour avoir les nombres restant
        final long diffInMs = endDate.getTime() - startDate.getTime();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
        j = diffInSec/(3600*24);
        reste = diffInSec%(3600*24);
        h = reste/3600;
        reste = reste%3600;
        m = reste/60;
        s = reste%60;

        //lance le count down
        mCountDownTimer = new CountDownTimer(diffInMs, 1000) {
            TextView txt = (TextView) findViewById(R.id.txtCountDown);
            StringBuilder time = new StringBuilder();
            @Override
            public void onFinish() {
                txt.setText("Les JPO ont débuté");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //recupere le textView
                txt = (TextView) findViewById(R.id.txtCountDown);

                long secUntilFinished = millisUntilFinished/1000;
                j = secUntilFinished/(3600*24);
                reste = secUntilFinished/(3600*24);

                long secondsDay = secUntilFinished % (3600*24);
                s = secondsDay % 60;
                m = (secondsDay / 60) % 60;
                h = (secondsDay / 3600);

                if (txt != null) {
                    txt.setText(j + "J " + h + "H " + m + "M " + s + "S");
                }
            }
        }.start();
    }
}
