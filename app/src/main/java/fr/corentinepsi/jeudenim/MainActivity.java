package fr.corentinepsi.jeudenim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    Player j1 = new Player(); //creation j1
    Player j2 = new Player(); //creation j2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox ui_checkBoxIA = findViewById(R.id.checkBoxIA); //checkboxIA
        final CheckBox ui_checkBoxFacile = findViewById(R.id.checkBoxFacile); //checkboxFacile
        final CheckBox ui_checkBoxImpossible = findViewById(R.id.checkBoxImpossible); //checkbox Impossile

        ui_checkBoxIA.setOnClickListener(new View.OnClickListener() { //attend une action
            @Override
            public void onClick(View v) {
                TextView ui_labelJ2name = findViewById(R.id.j2nameLabel); //champ pseudo j2

                if (!j1.playVsIA)
                { //on coche pour appliquer
                    ui_labelJ2name.setEnabled(false); //disable J2 label
                    ui_labelJ2name.setText(getString(R.string.robot)); //robot string
                    j1.playVsIA = true;
                    ui_checkBoxFacile.setVisibility(View.VISIBLE); //visible
                    ui_checkBoxImpossible.setVisibility(View.VISIBLE); //visible
                }
                else
                    { //si on decoche on peut retablir
                        ui_labelJ2name.setEnabled(true);
                        ui_labelJ2name.setText("");
                        j1.playVsIA = false;
                        ui_checkBoxFacile.setVisibility(View.INVISIBLE);
                        ui_checkBoxImpossible.setVisibility(View.INVISIBLE);
                        j1.difficultImpossible = false;
                        j1.difficultEasy = false;

                    }
            }
        });

        ui_checkBoxFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!j1.difficultEasy)
                {
                    ui_checkBoxImpossible.setChecked(false);
                    j1.difficultEasy = true;
                    j1.difficultImpossible = false;
                }
                else
                    {
                    j1.difficultEasy = false;
                    }
            }
        });

        ui_checkBoxImpossible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!j1.difficultImpossible) {
                    ui_checkBoxFacile.setChecked(false);
                    j1.difficultImpossible = true;
                    j1.difficultEasy = false;
                }
                else{
                    j1.difficultImpossible = false;
                }
            }
        });
    }

    public void startGame(View button) {
        TextView ui_labelJ1name = findViewById(R.id.j1nameLabel); //champ pseudo j1
        TextView ui_labelJ2name = findViewById(R.id.j2nameLabel); //champ pseudo j2
        TextView ui_infoLabel = findViewById(R.id.textViewInfo); //label info (rouge)
        j1.pseudo = ui_labelJ1name.getText().toString();
        j2.pseudo = ui_labelJ2name.getText().toString();

        if (j1.playVsIA && !j1.difficultEasy && !j1.difficultImpossible)
        {
            ui_infoLabel.setText(getString(R.string.nocoched));

        }
        else {
            if (j1.pseudo.length() < 3 || j2.pseudo.length() < 3)
            {
                ui_infoLabel.setText(getString(R.string.labelinfo));
            }

            else
                {
                    if (j1.pseudo.length() > 20 || j2.pseudo.length() > 20)
                    {
                        ui_infoLabel.setText(getString(R.string.labellenghInfo));
                    }
                    else
                        {
                            ui_infoLabel.setText("");
                            Intent intent = new Intent(this, GameActivity.class);
                            intent.putExtra("j1pseudo", j1.pseudo);
                            intent.putExtra("j2pseudo", j2.pseudo);
                            intent.putExtra("j1playVsIA", j1.playVsIA);
                            intent.putExtra("j1difficultEasy", j1.difficultEasy);
                            intent.putExtra("j1difficultImpossible", j1.difficultImpossible);
                            startActivity(intent);
                            CustomIntent.customType(this,"fadein-to-fadeout");
                        }
                }
        }
    }
}