package fr.corentinepsi.jeudenim;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {


    Player j1 = new Player();


    @Test
    public void startGame() {

        assertEquals("",j1.pseudo);
        assertFalse(j1.hasPlayed);
        assertFalse(j1.playVsIA);
        assertFalse(j1.difficultEasy);
        assertFalse(j1.difficultImpossible);
        j1.pseudo = "Blablabla";
        assertEquals("Blablabla", j1.pseudo);
        j1.hasPlayed = true;
        j1.playVsIA = true;
        j1.difficultEasy = true;
        j1.difficultImpossible = true;
        assertTrue(j1.hasPlayed);
        assertTrue(j1.playVsIA);
        assertTrue(j1.difficultEasy);
        assertTrue(j1.difficultImpossible);


    }
}




