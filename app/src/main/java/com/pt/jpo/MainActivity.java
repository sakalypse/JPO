package com.pt.jpo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presentation_mmi);
        initButton();
        initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
    }

    public void initButton() {
        Button buttonFormulaire = (Button) findViewById(R.id.formulaire);
        buttonFormulaire.setOnClickListener(this);
        Button buttonPresentationmmi = (Button) findViewById(R.id.presentationmmi);
        buttonPresentationmmi.setOnClickListener(this);
        Button buttonProfSalle = (Button) findViewById(R.id.profsalle);
        buttonProfSalle.setOnClickListener(this);
        Button buttonVideos = (Button) findViewById(R.id.videos);
        buttonVideos.setOnClickListener(this);
        Button buttoGeolocalisation = (Button) findViewById(R.id.localisation);
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
            case  R.id.formulaire: {
                setContentView(R.layout.formulaire);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutFormulaire));
                break;
            }
            case  R.id.presentationmmi: {
                setContentView(R.layout.presentation_mmi);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutPresentationMMI));
                break;
            }
            case  R.id.profsalle: {
                setContentView(R.layout.profsalle);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutProfSalle));
                break;
            }
            case  R.id.videos: {
                setContentView(R.layout.videos);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutVideos));
                break;
            }
            case  R.id.localisation: {
                setContentView(R.layout.localisation);
                initButton();
                initMarginAllLayout(findViewById(R.id.layoutLocalisation));
                break;
            }
        }
    }
}
