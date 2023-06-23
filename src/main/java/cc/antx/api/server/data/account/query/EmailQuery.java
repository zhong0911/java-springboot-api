package cc.antx.api.server.data.account.query;

import cc.antx.api.server.data.Database;

/**
 * @author zhong
 * @date 2023-03-04 19:00
 */
public class EmailQuery {
    public static void main(String[] args) {
        System.out.println(getExists("zhong_jia_fhao@163.com"));
    }

    public static Boolean getExists(String email) {
        Object res = Database.executeQuery("SELECT email FROM account WHERE email='" + email + "'", "email:string");
        return res != null && ((String) res).equalsIgnoreCase(email);
    }
}
