package App;

/*
    Main class pour reservation
 */

import Controller.ReservationController;

public class ReservationApp {

    public static void main(String[] args) {
        ReservationController rc = new ReservationController();
        rc.initView();
    }
}
