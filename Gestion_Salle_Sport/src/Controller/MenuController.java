package Controller;

/*
    MenuController (Controller) qui traite les actions de l'utilisateur, modifie les données du modèle et de la vue.
 */

import View.MenuView;

public class MenuController {

    public MenuController() {
        initView();
    }

    // Method pour initialiser la vue.
    public void initView() {
        new MenuView().setVisible(true);
    }
}
