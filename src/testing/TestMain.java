package testing;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONObject;

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}
