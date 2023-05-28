package Controller;

/*
    ReservationController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import Model.ReservationModel;
import View.ReservationView;

public class ReservationController {

    public ReservationController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new ReservationView().setVisible(true);
        ReservationModel.Adherents();
        ReservationModel.Cours();
        ReservationModel.Table();
    }
}
