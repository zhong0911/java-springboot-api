package cc.antx.api.server.email;
import cc.antx.api.server.config.email.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendEmail {
    public static void main(String[] args) {
        System.out.println(sendLoginRandomCode("zhong_jia_hao@163.com", "1250"));
        System.out.println(sendForgetPasswordRandomCode("zhong_jia_hao@163.com", "1250"));
        System.out.println(sendRegisterRandomCode("zhong_jia_hao@163.com", "1250"));
    }

    public static boolean sendRegisterRandomCode(String toEmail, String code) {
        String msg = "【JitSaint】验证码：" + code + "。此验证码用只于账号注册，5分钟内有效。";
        return sendMessage(toEmail, msg);
    }

    public static boolean sendForgetPasswordRandomCode(String toEmail, String code) {
        String msg = "【JitSaint】验证码：" + code + "。此验证码用只于修改密码，5分钟内有效。";
        return sendMessage(toEmail, msg);
    }

    public static boolean sendLoginRandomCode(String toEmail, String code) {
        String msg = "【JitSaint】验证码：" + code + "。此验证码用只于账号登录，5分钟内有效。";
        return sendMessage(toEmail, msg);
    }

    public static boolean sendMessage(String toEmail, String msg) {
        String from = Email.from;
        String host = Email.host;
        String password = Email.password;
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("JitSain");
            message.setText(msg);
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
