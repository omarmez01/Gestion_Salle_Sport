package Controller;

/*
    CoursController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import Model.CoursModel;
import View.CoursView;

public class CoursController {

    public CoursController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new CoursView().setVisible(true);
        CoursModel.Table();
    }
}
