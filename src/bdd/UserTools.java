package bdd;

import java.sql.*;

public class UserTools {

    public static boolean passwordCheck(String login, String pswd) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql ="SELECT user_id FROM Users WHERE login=? AND PASSWORD(?) = password";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pswd);
            ResultSet res = preparedStatement.executeQuery();
            if(!res.next())
                return false;
            res.close();
            preparedStatement.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible de vérifier le mot de passe : " + e.getMessage());
        }
        return true;
    }

    public static int getUserId(String login) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql ="SELECT user_id FROM Users WHERE login=?";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, login);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            int user_id = res.getInt("user_id");
            res.close();
            preparedStatement.close();
            connexion.close();
            return user_id;
        } catch (SQLException e) {
            throw new DBException("Impossible de récupérer l'ID : " + e.getMessage().toString());
        }
    }

    public static void createUser(String nom, String prenom, String login, String pswd) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String query = "INSERT into users values(NULL, ?, ?, ?, PASSWORD(?))";
            PreparedStatement preparedStatement = connexion.prepareStatement(query, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, pswd);
            int created = preparedStatement.executeUpdate();
            preparedStatement.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible de creer l'utilisateurt : " + e.getMessage().toString());
        }
    }

    /**
     * Check if user exists in the database
     * @param ID the ID as an integer
     * @return true if user exists
     * @throws DBException
     */
    public static boolean userExists(int ID) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "SELECT user_id FROM Users WHERE user_id = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, ID);
            ResultSet res = preparedStatement.executeQuery();
            boolean exists = res.next();
            res.close();
            preparedStatement.close();
            connexion.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException("Impossible de vérifier l'identifiant : " + e.getMessage() + ", cause : " + e.getCause());
        }
    }

    /**
     * Check if user exists in the database
     * @param login the login string
     * @return true if user exists
     * @throws DBException
     */
    public static boolean userExists(String login) throws DBException {
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "SELECT user_id FROM Users WHERE login = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, login);
            ResultSet res = preparedStatement.executeQuery();
            boolean exists = res.next();
            res.close();
            preparedStatement.close();
            connexion.close();
            return exists;
        } catch (SQLException e) {
            throw new DBException("Impossible de vérifier l'identifiant : " + e.getMessage() + ", cause : " + e.getCause());
        }
    }

    public static int getID(String key) throws DBException {
        int user_id = -1;
        try {
            Connection connexion = Database.getMySQLConnection();
            String sql = "SELECT * FROM connection WHERE connectionKey=?";
            PreparedStatement ps = connexion.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, key);
            ResultSet res = ps.executeQuery();
            res.next();
            user_id = res.getInt("id");
            res.close();
            ps.close();
            connexion.close();
        } catch (SQLException e) {
            throw new DBException("Impossible d'obtenir l'ID : " + e.getMessage().toString());
        }
        return user_id;
    }
}
