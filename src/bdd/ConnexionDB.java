package bdd;

import java.sql.*;

public class ConnexionDB {

    public static String insertConnexion(int user_id) throws DBException {
        String key = String.valueOf(user_id);
        boolean root = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "INSERT into connection values(?, ?, ?, ?)";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, key);
            ps.setInt(2, user_id);
            ps.setTimestamp(3, timestamp);
            ps.setBoolean(4, root);
            int res = ps.executeUpdate();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible d'inserer la connection : " + e.getMessage().toString());
        }
        return key;
    }

    public static boolean isConnexion(String key) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "SELECT * FROM connection WHERE connectionKey=?";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, key);
            ResultSet res = ps.executeQuery();
            if (res.next())
                return true;
            res.close();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible de verifier la connection : " + e.getMessage().toString());
        }
        return false;
    }

    public static void removeConnexion(String key) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "DELETE FROM connection WHERE connectionKey=?";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, key);
            int res = ps.executeUpdate();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible de supprimer la connection : " + e.getMessage().toString());
        }
    }

}
