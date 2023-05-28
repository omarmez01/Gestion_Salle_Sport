package Controller;

/*
    AdherentsController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import Model.AdherentsModel;
import View.AdherentsView;

public class AdherentsController {

    public AdherentsController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new AdherentsView().setVisible(true);
        AdherentsModel.Table();
    }
}
