package Method;

/*
    Méthode pour se connecter à la base de donnée et return une connection
 */

import java.sql.*;

public class Connecter {

    private static Connection cnx;

    public static Connection Connecter() {
        // Gérer les exceptions
        try {
            Class.forName("org.sqlite.JDBC");
            cnx = DriverManager.getConnection("jdbc:sqlite:base_de_donne.db");
            // JOptionPane.showMessageDialog(null, "Connection Reussie");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return cnx;
    }
}