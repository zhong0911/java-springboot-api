package cc.antx.api.server.data.account.add;

import cc.antx.api.server.data.Database;

/**
 * @author zhong
 * @date 2023-03-04 19:40
 */
public class UserAddition {
    public static boolean addUser(int uid, String username, String password, String email, String phone) {
        boolean result = false;
        String addAccountTableSql = String.format("INSERT INTO account VALUES(%d, '%s', '%s', '%s', '%s')", uid, username, password, email, phone);
        if (Database.executeSql(addAccountTableSql)) {
            result = true;
        }
        return result;
    }
}
