package App;

/*
    Main class pour reduction
 */

import Controller.ReductionController;

public class ReductionApp {

    public static void main(String[] args) {
        ReductionController rc = new ReductionController();
        rc.initView();
    }
}
