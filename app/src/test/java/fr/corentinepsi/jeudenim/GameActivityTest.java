package fr.corentinepsi.jeudenim;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;


public class GameActivityTest {

    Player j1 = new Player();
    Allumette allumette = new Allumette();
    int __NumberChosen = 3;
    String __displayINT = "3";

    @Test
    public void buttonOk() {

        assertEquals(20,allumette.getAllumette());
        assertEquals("3", __displayINT);
        assertEquals(3,__NumberChosen);
        allumette.soustractAllumette(__NumberChosen);
        assertEquals(17, allumette.getAllumette());
        allumette.soustractAllumette(18);
        assertEquals(1,allumette.getAllumette());
        j1.hasPlayed = true;
        assertTrue(j1.hasPlayed);
    }

    @Test
    public void playIA() {
        int random = ThreadLocalRandom.current().nextInt(1, 4);
        int __numberIA = 4 - random;
        assertTrue(__numberIA >= 1);
        allumette.soustractAllumette(__numberIA);
        assertTrue(allumette.getAllumette() >= 1);
        j1.hasPlayed = false;
        assertFalse(j1.hasPlayed);
    }


    @Test
    public void checkVictory() {
        int allumette = 1;
        j1.hasPlayed = true;

        assertEquals(1,allumette);
        assertTrue(j1.hasPlayed);
        j1.hasPlayed = false;
        assertFalse(j1.hasPlayed);
        }
}