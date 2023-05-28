package Model;

/*
    AdherentsModel (Model) qui contient les données à afficher.
 */

import View.ReservationView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Method.Connecter.Connecter;

public class ReservationModel {

    static Connection cnx;
    static PreparedStatement pst1, pst2;
    static ResultSet rs1, rs2;
    static DefaultTableModel df;

    // Method pour ajouter les donnees saisie par l'utilisatuer a la base de donnee
    public static void Ajouter() {
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("insert into reservation (cour,adherent,jour,horaire_de,horaire_a) values (?,?,?,?,?)");
            pst1.setString(1, ReservationView.getCoursbox().getSelectedItem().toString());
            pst1.setString(2, ReservationView.getAdherentbox().getSelectedItem().toString());
            pst1.setString(3, ReservationView.getJourbox().getSelectedItem().toString());
            pst1.setString(4, ReservationView.getTxtde().getText());
            pst1.setString(5, ReservationView.getTxta().getSelectedItem().toString());
            pst1.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Enregistrement Reussie");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour modifier les donnees saisie par l'utilisatuer a la base de donnee
    public static void Modifier() {
        int a = ReservationView.getjTableR().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("update reservation set cour = ?, adherent = ?, jour = ?, horaire_de = ?, horaire_a = ? where ref = ?");
            pst1.setString(1, ReservationView.getCoursbox().getSelectedItem().toString());
            pst1.setString(2, ReservationView.getAdherentbox().getSelectedItem().toString());
            pst1.setString(3, ReservationView.getJourbox().getSelectedItem().toString());
            pst1.setString(4, ReservationView.getTxtde().getText());
            pst1.setString(5, ReservationView.getTxta().getSelectedItem().toString());
            pst1.setInt(6, code);
            pst1.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Modification reussie");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour supprimer les donnees saisie par l'utilisatuer a la base de donnee
    public static void Supprimer() {
        int a = ReservationView.getjTableR().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("delete from reservation where ref = ?");
            pst1.setInt(1, code);
            pst1.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Suppression reussie");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour capter les donnees de la table
    public static void Mouse() {
        int a = ReservationView.getjTableR().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        ReservationView.getCoursbox().setSelectedItem(df.getValueAt(a, 1).toString());
        ReservationView.getAdherentbox().setSelectedItem(df.getValueAt(a, 2).toString());
        ReservationView.getCoursbox().setSelectedItem(df.getValueAt(a, 3).toString());
        ReservationView.getTxta().setSelectedItem(df.getValueAt(a, 4).toString());
    }

    // Method pour afficher les donnees de la base de donnee dans la table
    public static void Table() {
        int a;
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("select * from reservation");
            rs1 = pst1.executeQuery();
            ResultSetMetaData rd1 = rs1.getMetaData();
            a = rd1.getColumnCount();
            df = (DefaultTableModel)ReservationView.getjTableR().getModel();
            df.setRowCount(0);
            while(rs1.next()){
                Vector v1 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v1.add(rs1.getString("ref"));
                    v1.add(rs1.getString("cour"));
                    v1.add(rs1.getString("adherent"));
                    v1.add(rs1.getString("jour"));
                    v1.add(rs1.getString("horaire_de"));
                    v1.add(rs1.getString("horaire_a"));
                }
                df.addRow(v1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Method pour afficher tous les adherents de la base de donnee
    public static void Adherents() {
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("select nom from adherents");
            rs1 = pst1.executeQuery();
            while(rs1.next()) {
                ReservationView.getAdherentbox().addItem(rs1.getString("nom"));
            }
            cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method pour afficher tous les cours de la base de donnee
    public static void Cours() {
        ArrayList<String> all_cours = new ArrayList<String>();
        int length = 0;
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("select * from cours");
            rs1 = pst1.executeQuery();
            while(rs1.next()) {
                ReservationView.getCoursbox().addItem(rs1.getString("cours"));
                length = ReservationView.getCoursbox().getItemCount();
                for (int i = 0; i < length; i++) {
                    all_cours.add(rs1.getString("cours"));
                }
            }
            System.out.println(all_cours);
            ReservationView.getCoursbox().addItemListener(event -> {
                // The item affected by the event.
                String item = (String) event.getItem();
                if (all_cours.contains(item)) {
                    try {
                        Connecter();
                        pst2 = cnx.prepareStatement("select horaire from cours where cours = ?");
                        pst2.setString(1, item);
                        rs2 = pst2.executeQuery();
                        while(rs2.next()) {
                            ReservationView.getTxtde().setText(rs2.getString("horaire"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.getLogger(ReservationModel.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            });
            cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
