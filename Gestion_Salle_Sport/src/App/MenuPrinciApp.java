package App;

/*
    Main class pour menu
 */

import Controller.MenuController;

public class MenuPrinciApp {

    public static void main(String args[]) {
        MenuController mc = new MenuController();
        mc.initView();
    }
}