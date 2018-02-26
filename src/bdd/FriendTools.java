package bdd;

import java.sql.*;

public class FriendTools {

    public static void follow(int from_id, int to_id) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "INSERT into friends values(?, ?, ?)";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, from_id);
            ps.setInt(2, to_id);
            Timestamp tmp = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(3, tmp);
            int created = ps.executeUpdate();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible d'obtenir l'ID : " + e.getMessage().toString());
        }
    }

    public static void unfollow(int from_id, int to_id) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "DELETE FROM friends WHERE from_id=? and to_id=?";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, from_id);
            ps.setInt(2, to_id);
            int deleted = ps.executeUpdate();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible d'obtenir l'ID : " + e.getMessage().toString());
        }
    }
}
