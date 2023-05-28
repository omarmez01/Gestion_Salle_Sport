package App;

/*
    Main class pour paiement
 */

import Controller.PaiementController;

public class PaiementApp {

    public static void main(String[] args) {
        PaiementController pc = new PaiementController();
        pc.initView();
    }
}
