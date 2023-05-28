package Controller;

/*
    PaiementController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import Model.PaiementModel;
import View.PaiementView;

public class PaiementController {

    public PaiementController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new PaiementView().setVisible(true);
        PaiementModel.Table();
        PaiementModel.Adherents();
    }
}
