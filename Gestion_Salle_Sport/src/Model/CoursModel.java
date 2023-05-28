package Model;

/*
    CoursModel (Model) qui contient les données à afficher.
 */

import View.CoursView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

import static Method.Connecter.Connecter;

public class CoursModel {

    static Connection cnx;
    static PreparedStatement pst;
    static ResultSet rs;
    static DefaultTableModel df;

    // Method pour ajouter les donnees saisie par l'utilisatuer a la base de donnee
    public static void Ajouter() {
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("insert into cours (cours,horaire) values (?,?)");
            pst.setString(1, CoursView.getTxtcours().getText());
            pst.setString(2, CoursView.getTxthoraire().getSelectedItem().toString());
            pst.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Enregistrement Reussie");
            Actualiser();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour modifier les donnees saisie par l'utilisatuer a la base de donnee
    public static void Modifier() {
        int a = CoursView.getjTable2().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("update cours set cours = ?, horaire = ? where ref = ?");
            pst.setString(1, CoursView.getTxtcours().getText());
            pst.setString(2, CoursView.getTxthoraire().getSelectedItem().toString());
            pst.setInt(3, code);
            pst.executeUpdate();
            cnx.close();
            Actualiser();
            Table();
            JOptionPane.showMessageDialog(null, "Modification reussie");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour supprimer les donnees saisie par l'utilisatuer a la base de donnee
    public static void Supprimer() {
        int a = CoursView.getjTable2().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("delete from cours where ref = ?");
            pst.setInt(1, code);
            pst.executeUpdate();
            cnx.close();
            Actualiser();
            Table();
            JOptionPane.showMessageDialog(null, "Suppression reussie");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour capter les donnees de la table
    public static void Mouse() {
        int a = CoursView.getjTable2().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        CoursView.getTxtcours().setText(df.getValueAt(a, 1).toString());
        CoursView.getTxthoraire().setSelectedItem(df.getValueAt(a, 2).toString());
    }

    // Method pour actualiser les champs
    private static void Actualiser() {
        CoursView.getTxtcours().setText("");
    }

    // Method pour afficher les donnees de la base de donnee dans la table
    public static void Table() {
        int a;
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("select * from cours");
            rs = pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel)CoursView.getjTable2().getModel();
            df.setRowCount(0);
            while(rs.next()){
                Vector v2 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v2.add(rs.getString("ref"));
                    v2.add(rs.getString("cours"));
                    v2.add(rs.getString("horaire"));
                }
                df.addRow(v2);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
