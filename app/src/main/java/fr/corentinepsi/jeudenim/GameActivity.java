package fr.corentinepsi.jeudenim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity {

    Allumette allumette = new Allumette(); //creation de l'objet allumette
    Player j1 = new Player(); //creation de j1
    Player j2 = new Player(); //creation de j2

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView ui_j1TextHeader = findViewById(R.id.j1pseudop2); //pseudo J1 header
        TextView ui_j2TextHeader = findViewById(R.id.j2pseudop2); //pseudo J2 header
        TextView ui_currentPlay = findViewById(R.id.infoGamePlayer); //label : tour de
        TextView ui_currentAllumette = findViewById(R.id.nbAllumette); //nb allumette restantes
        j1.hasPlayed = false;
        j2.hasPlayed = true;

        Intent i = getIntent();
        if (i != null)
        {
            j1.pseudo = i.getStringExtra("j1pseudo"); //recup pseudo J1
            j2.pseudo = i.getStringExtra("j2pseudo"); //recup pseudo J2
            j1.playVsIA = i.getBooleanExtra("j1playVsIA", j1.playVsIA); //recup si case vsIA cochee
            j1.difficultEasy = i.getBooleanExtra("j1difficultEasy", j1.difficultEasy); //recup si case Easy cochee
            j1.difficultImpossible = i.getBooleanExtra("j1difficultImpossible", j1.difficultImpossible); //recup si case Impossible cochee
            ui_j1TextHeader.setText(j1.pseudo); //affichage du nom J1 header
            ui_j2TextHeader.setText(j2.pseudo); //affichage du nom J2 header
            ui_currentAllumette.setText(String.format(Locale.FRANCE, "%d", allumette.getAllumette())); //affiche nb allumettes

            if (j1.playVsIA && j1.difficultImpossible)
            {
                j1.hasPlayed = true;
                playIA(1);
            }
            if (j1.playVsIA && j1.difficultEasy)
            {
                j1.hasPlayed = false;
                j2.hasPlayed = true;
            }
            if (!j1.hasPlayed)
            {
                ui_currentPlay.setText(j1.pseudo); //j1 commence
            }
            else
                {
                    ui_currentPlay.setText(j2.pseudo); //j2 commence
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void buttonOk(View button) {

        TextView ui_currentAllumette = findViewById(R.id.nbAllumette); //nb allumette restantes
        TextView ui_inputNumber = findViewById(R.id.numberText); //label de saisie Nombre
        TextView ui_errorLabel = findViewById(R.id.errorLabel); //label error longueur
        TextView ui_lastChoiceJ1 = findViewById(R.id.choixJ1); //dernier choix J1
        TextView ui_lastChoiceJ2 = findViewById(R.id.choixJ2); //dernier choix J2
        TextView ui_currentPlay = findViewById(R.id.infoGamePlayer); //label : tour de


        if (ui_inputNumber.getText().toString().length() < 1) //Si champ vide
        {
            ui_errorLabel.setText(getString(R.string.errorLong)); //erreur champ vide
        }
        else
            {
                int __numberChosen = Integer.parseInt(ui_inputNumber.getText().toString()); //convertit le nombre choisi en int
                String __displayINT = String.format(Locale.FRANCE, "%d", __numberChosen); //affiche le string du nb choisi

                if (__numberChosen > 3 || __numberChosen <= 0) //si le nb est ]1;3[
                {
                    ui_errorLabel.setText(getString(R.string.errorLong)); //erreur longueur
                }
                else
                    {
                        allumette.soustractAllumette(__numberChosen); //soustraction des allumettes dans la classe
                        ui_currentAllumette.setText(String.format(Locale.FRANCE, "%d", allumette.getAllumette())); //update le nb restant
                        ui_inputNumber.setText(""); //clear label saisie
                        ui_errorLabel.setText(""); //clear errorLabel
                        updateImg(); //update les allumettes
                        checkVictory();

                        if (!j1.hasPlayed) {
                            ui_lastChoiceJ1.setText(__displayINT);
                            ui_currentPlay.setText(j2.pseudo);
                            j1.hasPlayed = true;
                            j2.hasPlayed = false;
                            if (j1.playVsIA)
                            {
                                if (j1.difficultImpossible)
                                {
                                    playIA(__numberChosen);
                                }
                                if (j1.difficultEasy)
                                {
                                    int random = ThreadLocalRandom.current().nextInt(1, 4);
                                    playIA(random);
                                }
                            }
                        }
                        else
                            {
                                ui_currentPlay.setText(j1.pseudo);
                                ui_lastChoiceJ2.setText(__displayINT);
                                j2.hasPlayed = true;
                                j1.hasPlayed = false;
                            }
                    }
            }
    }


    public void playIA(int __nbAlluLast) {

        int __numberIA = 4 - __nbAlluLast;
        TextView ui_currentAllumette = findViewById(R.id.nbAllumette); //nb allumette restantes
        TextView ui_lastChoiceJ2 = findViewById(R.id.choixJ2); //dernier choix J2
        TextView ui_currentPlay = findViewById(R.id.infoGamePlayer); //label : tour de
        String __displayINT = String.format(Locale.FRANCE, "%d", __numberIA); //affiche le string du nb choisi

        allumette.soustractAllumette(__numberIA);
        ui_currentAllumette.setText(String.format(Locale.FRANCE, "%d", allumette.getAllumette())); //update le nb restant
        updateImg(); //update les allumettes
        ui_currentPlay.setText(j1.pseudo);
        ui_lastChoiceJ2.setText(__displayINT);
        checkVictory();
        j2.hasPlayed = true;
        j1.hasPlayed = false;
    }

    public void checkVictory() {
        TextView ui_errorLabel = findViewById(R.id.errorLabel); //label error longueur
        TextView ui_inputNumber = findViewById(R.id.numberText); //label de saisie Nombre
        Button ui_replayBtn = findViewById(R.id.ui_replayBtn); //bouton rejouer
        Button button = findViewById(R.id.button);

        if (allumette.getAllumette() == 1)
        {
            if (!j1.hasPlayed)
            {
                ui_errorLabel.setTextColor(Color.rgb(0,204,0));
                ui_errorLabel.setText(String.format("%s%s", j1.pseudo, " " + getString(R.string.win)));
            }
            if (!j2.hasPlayed)
            {
                ui_errorLabel.setTextColor(Color.rgb(0,204,0));
                ui_errorLabel.setText(String.format("%s%s", j2.pseudo, " " + getString(R.string.win)));

            }
            ui_inputNumber.setEnabled(false);
            button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
            ui_replayBtn.setVisibility(View.VISIBLE);
        }
    }


    public void buttonReplay (View button) {
        finish();
    }

    public void updateImg() {
        ImageView img2 = findViewById(R.id.imageView2); //import image
        ImageView img3 = findViewById(R.id.imageView3);
        ImageView img4 = findViewById(R.id.imageView4);
        ImageView img5 = findViewById(R.id.imageView5);
        ImageView img6 = findViewById(R.id.imageView6);
        ImageView img7 = findViewById(R.id.imageView7);
        ImageView img8 = findViewById(R.id.imageView8);
        ImageView img9 = findViewById(R.id.imageView9);
        ImageView img10 = findViewById(R.id.imageView10);
        ImageView img11 = findViewById(R.id.imageView11);
        ImageView img12 = findViewById(R.id.imageView12);
        ImageView img13 = findViewById(R.id.imageView13);
        ImageView img14 = findViewById(R.id.imageView14);
        ImageView img15 = findViewById(R.id.imageView15);
        ImageView img16 = findViewById(R.id.imageView16);
        ImageView img17 = findViewById(R.id.imageView17);
        ImageView img18 = findViewById(R.id.imageView18);
        ImageView img19 = findViewById(R.id.imageView19);
        ImageView img20 = findViewById(R.id.imageView20);

        //Methode pas du tout optimisée (tentative avec switch case mais erreurs)
        if (allumette.getAllumette() < 20) {
            img20.setImageResource(0);
        }
        if (allumette.getAllumette() < 19) {
            img19.setImageResource(0);
        }
        if (allumette.getAllumette() < 18) {
            img18.setImageResource(0);
        }
        if (allumette.getAllumette() < 17) {
            img17.setImageResource(0);
        }
        if (allumette.getAllumette() < 16) {
            img16.setImageResource(0);
        }
        if (allumette.getAllumette() < 15) {
            img15.setImageResource(0);
        }
        if (allumette.getAllumette() < 14) {
            img14.setImageResource(0);
        }
        if (allumette.getAllumette() < 13) {
            img13.setImageResource(0);
        }
        if (allumette.getAllumette() < 12) {
            img12.setImageResource(0);
        }
        if (allumette.getAllumette() < 11) {
            img11.setImageResource(0);
        }
        if (allumette.getAllumette() < 10) {
            img10.setImageResource(0);
        }
        if (allumette.getAllumette() < 9) {
            img9.setImageResource(0);
        }
        if (allumette.getAllumette() < 8) {
            img8.setImageResource(0);
        }
        if (allumette.getAllumette() < 7) {
            img7.setImageResource(0);
        }
        if (allumette.getAllumette() < 6) {
            img6.setImageResource(0);
        }
        if (allumette.getAllumette() < 5) {
            img5.setImageResource(0);
        }
        if (allumette.getAllumette() < 4) {
            img4.setImageResource(0);
        }
        if (allumette.getAllumette() < 3) {
            img3.setImageResource(0);
        }
        if (allumette.getAllumette() < 2) {
            img2.setImageResource(0);
        }
    }
}
