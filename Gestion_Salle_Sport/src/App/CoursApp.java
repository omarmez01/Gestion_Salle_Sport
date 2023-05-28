package App;

/*
    Main class pour cours
 */

import Controller.CoursController;

public class CoursApp {

    public static void main(String args[]) {
        CoursController cc = new CoursController();
        cc.initView();
    }
}
