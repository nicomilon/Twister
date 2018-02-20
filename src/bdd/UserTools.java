package bdd;

import java.sql.*;

public class UserTools {

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

}
