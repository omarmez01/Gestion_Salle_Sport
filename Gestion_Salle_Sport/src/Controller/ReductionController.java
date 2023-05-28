package Controller;

/*
    ReductionController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import Model.ReductionModel;
import View.ReductionView;

public class ReductionController {

    public ReductionController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new ReductionView().setVisible(true);
        ReductionModel.Adherents();
    }
}
