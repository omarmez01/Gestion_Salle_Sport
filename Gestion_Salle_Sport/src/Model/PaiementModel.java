package Model;

/*
    PaiementModel (Model) qui contient les données à afficher.
 */

import View.PaiementView;
import View.ReductionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

import static Method.Connecter.Connecter;

public class PaiementModel {

    static Connection cnx;
    static PreparedStatement pst1, pst2, pst3;
    static ResultSet rs1, rs2;
    static DefaultTableModel df;

    // Method pour saisir un paiement dans la base de donnee
    public static void Paiement() {
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("insert into paiement (date,nom,montant) values (?,?,?)");
            pst1.setString(1, PaiementView.getFormattedTextField().getText());
            pst1.setString(2, PaiementView.getTxtnom().getSelectedItem().toString());
            pst1.setString(3, PaiementView.getTxtpaiement().getText());
            pst1.executeUpdate();
            pst2 = cnx.prepareStatement("select count from adherents");
            rs2 = pst2.executeQuery();
            int count = Integer.parseInt(rs2.getString("count"));
            count++;
            System.out.print(count);
            pst3 = cnx.prepareStatement("update adherents set count = ? where nom = ?");
            pst3.setString(1, String.valueOf(count));
            pst3.setString(2, PaiementView.getTxtnom().getSelectedItem().toString());
            pst3.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Enregistrement Reussie");
            Actualiser();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method qui appele la classe reduction
    public static void Reduction() {
        ReductionView reductionView = new ReductionView();
        reductionView.setVisible(true);
    }

    // Method pour modifier les donnees saisie par l'utilisatuer a la base de donnee
    public static void Modifier() {
        int a = PaiementView.getjTableP().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("update paiement set date = ?, nom = ?, montant = ? where ref = ?");
            pst1.setString(1, PaiementView.getFormattedTextField().getText());
            pst1.setString(2, PaiementView.getTxtnom().getSelectedItem().toString());
            pst1.setString(3, PaiementView.getTxtpaiement().getText());
            pst1.setInt(4, code);
            pst1.executeUpdate();
            cnx.close();
            Table();
            JOptionPane.showMessageDialog(null, "Modification reussie");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur " + e.getMessage());
        }
    }

    // Method pour capter les donnees de la table
    public static void Mouse() {
        int a = PaiementView.getjTableP().getSelectedRow();
        int code = Integer.parseInt(df.getValueAt(a, 0).toString());
        PaiementView.getFormattedTextField().setText(df.getValueAt(a, 1).toString());
        PaiementView.getTxtnom().setSelectedItem(df.getValueAt(a, 2).toString());
        PaiementView.getTxtpaiement().setText(df.getValueAt(a, 3).toString());
    }

    // Method pour actualiser les champs
    public static void Actualiser() {
        PaiementView.getFormattedTextField().setText("");
        PaiementView.getTxtpaiement().setText("");
    }

    // Method pour afficher les donnees de la base de donnee dans la table
    public static void Table() {
        int a;
        try {
            cnx = Connecter();
            pst1 = cnx.prepareStatement("select * from paiement");
            rs1 = pst1.executeQuery();
            ResultSetMetaData rd = rs1.getMetaData();
            a = rd.getColumnCount();
            df = (DefaultTableModel)PaiementView.getjTableP().getModel();
            df.setRowCount(0);
            while(rs1.next()){
                Vector v1 = new Vector();
                for (int i = 1; i <= a; i++) {
                    v1.add(rs1.getString("ref"));
                    v1.add(rs1.getString("date"));
                    v1.add(rs1.getString("nom"));
                    v1.add(rs1.getString("montant"));
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
            pst1 = cnx.prepareStatement("select Distinct nom from adherents");
            rs1 = pst1.executeQuery();
            while(rs1.next()) {
                PaiementView.getTxtnom().addItem(rs1.getString("nom"));
            }
            cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
