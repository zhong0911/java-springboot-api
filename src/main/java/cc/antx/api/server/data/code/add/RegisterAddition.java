package cc.antx.api.server.data.code.add;

import cc.antx.api.server.data.Database;

/**
 * @author zhong
 * @date 2023-03-04 18:54
 */
public class RegisterAddition {
    public static boolean addItem(String id, String email, String code, String generationTime, String expirationTime) {
        return Database.executeSql(String.format("INSERT INTO code VALUES('%s', '%s', 'register', '%s', '%s', '%s')", id, email, code, generationTime, expirationTime));
    }
}
