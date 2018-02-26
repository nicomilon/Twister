package services;

import bdd.*;
import com.mongodb.Mongo;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendServices {

    public static JSONObject addComment(String valueKey, String valueText) {
        //TODO addComment
        return new JSONObject();
    }

    public static JSONObject addFriend(String key, String valueFriendID) {
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            if(UserTools.userExists(valueFriendID))
                return MsgTools.serviceRefused("Invalid friend ID", -1);
            int from_id = FriendTools.getID(key);
            int to_id = Integer.parseInt(valueFriendID);
            if(from_id == to_id)
                return MsgTools.serviceRefused("Can't friend yorself..", -1);

            //DB call
            FriendTools.follow(from_id,to_id);
            return MsgTools.serviceAccepted();
        }catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject removeFriend(String key, String valueFriendID) {
        try {
            boolean connected = ConnexionDB.isConnexion(key);
            if (!connected)
                return MsgTools.serviceRefused("Bad connexion key", -1);
            if(UserTools.userExists(valueFriendID))
                return MsgTools.serviceRefused("Invalid friend ID", -1);
            int from_id = FriendTools.getID(key);
            int to_id = Integer.parseInt(valueFriendID);
            if(from_id == to_id)
                return MsgTools.serviceRefused("Can't unfriend yorself..", -1);
            FriendTools.unfollow(from_id,to_id);
            return MsgTools.serviceAccepted();
        }catch (DBException e) {
            return MsgTools.serviceRefused(e.toString(), 1000);
        } catch (Exception e) {
            return MsgTools.serviceRefused(e.toString(), 10000);
        }
    }

    public static JSONObject search(String valueKey, String valueQuery, String valueFriends) {
        //TODO finir la partie Search
         return new JSONObject();
    }
}
