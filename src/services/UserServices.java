package services;

import org.json.JSONException;
import org.json.JSONObject;

import bdd.*;

public class UserServices {

    public static JSONObject login(String login, String pswd) {
        if (login == null || pswd == null) {
            return MsgTools.serviceRefused("Bad credentials", -1);
        }
        try {
            boolean user_exists = UserTools.userExists(login);
            if (!user_exists)
                return MsgTools.serviceRefused("Bad credentials", -1);
            boolean pswd_ok = UserTools.passwordCheck(login, pswd);
            if (!pswd_ok)
                return MsgTools.serviceRefused("Bad credentials", -1);
            int user_id = UserTools.getUserId(login);
            String key = ConnexionDB.insertConnexion(user_id);
            JSONObject json = new JSONObject();
            json.put("id", user_id);
            json.put("login", login);
            json.put("key", key);
            return json;
        } catch (JSONException j_err) {
            return MsgTools.serviceRefused(j_err.toString(), 100);
        }catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject logout(String key) {
        if (key == null)
            return MsgTools.serviceRefused("No key", 1);
        try {
            if (!ConnexionDB.isConnexion(key))
                return MsgTools.serviceRefused("Unknown key", -1);
            ConnexionDB.removeConnexion(key);
            return MsgTools.serviceAccepted();
        } catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        }
    }

    public static JSONObject createUser(String nom, String prenom, String login, String pswd) {
        if (login == null || pswd == null || prenom == null || nom == null) {
            return MsgTools.serviceRefused("Manque une valeur", -1);
        }
        try {
            boolean user_exists = UserTools.userExists(login);
            if (user_exists)
                return MsgTools.serviceRefused("User already exists", -1);
            UserTools.createUser(nom, prenom, login, pswd);
            return MsgTools.serviceAccepted();
        }catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

}
