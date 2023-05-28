package Model;

/*
    AdherentsModel (Model) qui contient les données à afficher.
 */

import View.ReductionView;

import javax.swing.table.DefaultTableModel;
import java.security.SecureRandom;
import java.sql.*;
import java.util.logging.*;

import static Method.Connecter.Connecter;

public class ReductionModel {

    static Connection cnx;
    static PreparedStatement pst;
    static ResultSet rs;
    static DefaultTableModel df;

    // Method qui affiche tous les adherents de la base de donnee et genere un code promo si il a paye 5 mois
    public static void Ok() {
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("select count from adherents where nom = ?");
            pst.setString(1, ReductionView.getTxtnom().getSelectedItem().toString());
            rs = pst.executeQuery();
            int count = Integer.parseInt(rs.getString("count"));
            System.out.print(count);
            if (count >= 10) {
//                String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//                SecureRandom rnd = new SecureRandom();
//                StringBuilder sb = new StringBuilder(12);
//                for(int i = 0; i < 12; i++)
//                    sb.append(AB.charAt(rnd.nextInt(AB.length())));
//                String code = sb.toString();
                ReductionView.getjTextField1().setText("Vous avez une reduction de 10%");
            }
            else if (count >= 5) {
                ReductionView.getjTextField1().setText("Vous avez une reduction de 5%");
            }
            else {
                ReductionView.getjTextField1().setText("Pas de reduction.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReductionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method pour afficher tous les adherents de la base de donnee
    public static void Adherents() {
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("select Distinct nom from adherents");
            rs = pst.executeQuery();
            while(rs.next()) {
                ReductionView.getTxtnom().addItem(rs.getString("nom"));
            }
            cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
