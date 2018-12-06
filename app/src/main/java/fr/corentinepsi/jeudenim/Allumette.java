package fr.corentinepsi.jeudenim;

public class Allumette {

    private int __nbAllumette = 20;


    public int getAllumette() { return __nbAllumette; }


    public void soustractAllumette(int nbAllu) {
        __nbAllumette -= nbAllu;

        if (__nbAllumette <= 0) {
            __nbAllumette = 1;
        }
    }
}
