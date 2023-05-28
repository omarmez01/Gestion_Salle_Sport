package App;

/*
    Main class pour adherent
 */

import Controller.AdherentsController;

public class AdherentsApp {

    public static void main(String args[]) {
        AdherentsController ac = new AdherentsController();
        ac.initView();
    }
}
