package testing;

import java.sql.Connection;
import java.sql.SQLException;

import bdd.FriendTools;
import com.mongodb.Mongo;
import org.json.JSONObject;
import services.FriendServices;

public class TestMain {

    public static void main(String[] args) throws SQLException {
        Connection db = null;
        try {

            JSONObject loginTest = services.UserServices.login("test@loggin", "testPassword");
            String key = loginTest.getString("key");
            JSONObject logoutTest = services.UserServices.logout(key);
            System.out.println(loginTest);
            System.out.println(logoutTest);

            logoutTest = services.UserServices.logout(key);
            System.out.println(logoutTest);

            System.out.println("Fin tests login/out\n\n");

            System.out.println("Debuts tests MongoDB");

            //System.out.println(FriendServices.addComment("31", "99", "How can it be you are so fantastical ?"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}
