package Model;

/*
    AdherentsModel (Model) qui contient les données à afficher.
 */

import View.AdherentsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

import static Method.Connecter.Connecter;

public class AdherentsModel {

    static Connection cnx;
    static PreparedStatement pst;
    static ResultSet rs;
    static DefaultTableModel df;

    // Method pour ajouter les donnees saisie par l'utilisatuer a la base de donnee
    public static void Ajouter() {
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("insert into adherents (nom,numero,adresse,age,sexe,count) values (?,?,?,?,?,?)");
            pst.setString(1, AdherentsView.getTxtnom().getText());
            pst.setString(2, AdherentsView.getTxtnum().getText());
            pst.setString(3, AdherentsView.getTxtadresse().getText());
            pst.setString(4, AdherentsView.getTxtage().getText());
            pst.setString(5, AdherentsView.getTxtsexe().getSelectedItem().toString());
            pst.setInt(6, 0);
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
        int a = AdherentsView.getjTable1().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("update adherents set nom = ?, numero = ?, adresse = ?, age = ?, sexe = ? where ref = ?");
            pst.setString(1, AdherentsView.getTxtnom().getText());
            pst.setString(2, AdherentsView.getTxtnum().getText());
            pst.setString(3, AdherentsView.getTxtadresse().getText());
            pst.setString(4, AdherentsView.getTxtage().getText());
            pst.setString(5, AdherentsView.getTxtsexe().getSelectedItem().toString());
            pst.setInt(6, code);
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
        int a = AdherentsView.getjTable1().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("delete from adherents where ref = ?");
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
        int a = AdherentsView.getjTable1().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        AdherentsView.getTxtnom().setText(df.getValueAt(a, 1).toString());
        AdherentsView.getTxtnum().setText(df.getValueAt(a, 2).toString());
        AdherentsView.getTxtadresse().setText(df.getValueAt(a, 3).toString());
        AdherentsView.getTxtage().setText(df.getValueAt(a, 4).toString());
        AdherentsView.getTxtsexe().setSelectedItem(df.getValueAt(a, 5).toString());
    }

    // Method pour actualiser les champs
    private static void Actualiser() {
        AdherentsView.getTxtnom().setText("");
        AdherentsView.getTxtnum().setText("");
        AdherentsView.getTxtadresse().setText("");
        AdherentsView.getTxtage().setText("");
    }

    // Method pour afficher les donnees de la base de donnee dans la table
    public static void Table() {
        int a;
        try {
            cnx = Connecter();
            pst = cnx.prepareStatement("select * from adherents");
            rs = pst.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel) AdherentsView.getjTable1().getModel();
            df.setRowCount(0);
            while(rs.next()){
                Vector v1 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v1.add(rs.getString("ref"));
                    v1.add(rs.getString("nom"));
                    v1.add(rs.getString("numero"));
                    v1.add(rs.getString("adresse"));
                    v1.add(rs.getString("age"));
                    v1.add(rs.getString("sexe"));
                }
                df.addRow(v1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
