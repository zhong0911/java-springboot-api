package cc.antx.api.server.data.account.query;

import cc.antx.api.server.data.Database;

/**
 * @author zhong
 * @date 2023-03-04 19:00
 */
public class UsernameQeury {

    public static void main(String[] args) {
        String[] us = new String[]{"zhong", "Adisaint"};
        for (String u : us) {
            System.out.println("UID: " + getUid(u));
            System.out.println("Username: " + u);
            System.out.println("Email: " + getEmail(u));
            System.out.println("Password: " + getPassword(u));
            System.out.println("Phone： " + getPhone(u));
            System.out.println("Exists: " + getExists(u));
            System.out.println("Status: " + getStatus(u));
            System.out.println("----------------------------------");
        }
    }

    public static String getUid(String username) {
        return String.valueOf(Database.executeQuery("SELECT uid FROM account WHERE username='" + username + "'", "uid:int"));
    }

    public static String getEmail(String username) {
        return (String) Database.executeQuery("SELECT email FROM account WHERE username='" + username + "'", "email:string");
    }

    public static String getPassword(String username) {
        return (String) Database.executeQuery("SELECT password FROM account WHERE username='" + username + "'", "password:string");
    }

    public static String getPhone(String username) {
        return (String) Database.executeQuery("SELECT phone FROM account WHERE username='" + username + "'", "phone:string");
    }

    public static Boolean getExists(String username) {
        Object res = Database.executeQuery("SELECT username FROM account WHERE username='" + username + "'", "username:string");
        return res != null && ((String) res).equalsIgnoreCase(username);
    }

    public static Boolean getStatus(String username) {
        Object res = Database.executeQuery("SELECT status FROM account_status WHERE username='" + username + "'", "status:boolean");
        return (res != null) ? (Boolean) res : null;
    }
}
