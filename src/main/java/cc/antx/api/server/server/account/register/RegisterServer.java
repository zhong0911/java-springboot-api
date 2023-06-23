package cc.antx.api.server.server.account.register;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import cc.antx.api.server.data.account.add.UserAddition;
import cc.antx.api.server.data.account.query.EmailQuery;
import cc.antx.api.server.data.account.query.UidQuery;
import cc.antx.api.server.data.account.query.UsernameQeury;
import cc.antx.api.server.email.SendEmail;
import cc.antx.utils.string.StringUtils;
import cc.antx.api.server.config.server.Server;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.antx.api.server.data.code.query.RegisterAddition;
import cc.antx.utils.time.TimeUtils;

/**
 * 注册服务端类
 *
 * @author zhong
 * @date 2023-01-30 10:04
 */

public class RegisterServer implements Runnable {
    public static void main(String[] args) throws IOException {
        new RegisterServer();
    }

    /**
     * 注册服务器的端口
     */
    private final int port = Server.REGISTER_SERVER_PORT;
    /**
     * ServerSocket对象
     */
    private final ServerSocket serverSocket = new ServerSocket(port);
    /**
     * socket对象
     */
    private Socket socket;
    /**
     * Date对象
     */
    private final Date date = new Date();


    public RegisterServer() throws IOException {
        this.server();
    }

    /**
     * Server method
     */
    private void server() {
        while (true) {
            try {
                socket = this.serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[2048];
                int length = inputStream.read(bytes);
                String data = new String(bytes, 0, length, StandardCharsets.UTF_8).trim();
                System.out.printf("%s: %s\n", socket.getInetAddress(), data);
                executeData(data);
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 执行客户端发来的数据
     *
     * @param data 用户发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void executeData(String data) throws IOException {
        String event = JSONObject.parseObject(data).getString("event");
        switch (event) {
            case "sendCode" -> sendCode(data);
            case "requestsRegister" -> requestsRegister(data);
            case "verifyCode" -> verifyCode(data);
            case "getUsernameExists" -> getUsernameExists(data);
            case "getEmailExists" -> getEmailExists(data);
            case "startRegister" -> startRegister(data);
            default -> System.out.println("Other event " + event);
        }
    }


    /**
     * 将用户的标识码添加到标识码列表中
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void startRegister(String data) throws IOException {

    }


    /**
     * 验证验证码是否存在
     * 如果客户端的标识码不存在将无法返回
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void verifyCode(String data) throws IOException {
        String msg;
        try {
            JSONObject jsonObject = JSON.parseObject(data);
            String code = jsonObject.getString("code");
            String email = jsonObject.getString("email");
            if (RegisterAddition.Register.getCodeValid(email) && RegisterAddition.Register.getCode(email).equals(code)) {
                msg = "{\"success\": true, \"message\": \"验证验证码成功\"}";
            } else {
                msg = "{\"success\": false, \"message\": \"验证码错误或已失效\"}";
            }
        } catch (NullPointerException e) {
            msg = "{\"success\": false, \"message\": \"系统错误\"}";
        }
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 获取指定邮箱地址是否存在并返回给客户端
     * 如果客户端的标识码不存在将无法返回
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void getEmailExists(String data) throws IOException {
        JSONObject jsonObject = JSON.parseObject(data);
        String email = jsonObject.getString("email");
        String msg = String.format("{\"success\": true, \"isExists\": %b, \"message\": \"查询成功\"}", EmailQuery.getExists(email));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 发送验证码并返回发送结果
     * 如果客户端的标识码不存在将无法返回
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void sendCode(String data) throws IOException {
        JSONObject jsonObject = JSON.parseObject(data);
        String msg;
        String email = jsonObject.getString("email");
        String code = StringUtils.getRandomNumber(6);
        try {
            SendEmail.sendRegisterRandomCode(email, code);
            msg = "{\"success\": true, \"message\": \"验证码发送成功\"}";
            cc.antx.api.server.data.code.add.RegisterAddition.addItem(StringUtils.getRandomString(8), email, code, TimeUtils.getNowFormatTime(), TimeUtils.dateFormat(new Date(new Date().getTime() + (60 * 1000 * 5))));
        } catch (Exception e) {
            msg = "{\"success\": false, \"message\": \"无法发送验证码原因" + e.getMessage() + "\"}";
        }
        OutputStream outputStream = this.socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取用户名是否存在并返回给客户端
     * 如果客户端的标识码不存在将无法返回
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void getUsernameExists(String data) throws IOException {
        JSONObject jsonObject = JSON.parseObject(data);
        String username = jsonObject.getString("username");
        String msg = String.format("{\"success\": true, \"isExists\": %b, \"message\": \"查询成功\"}", UsernameQeury.getExists(username));
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 执行注册用户
     * 并在数据库中添加其信息
     * 再返回给客户端是否注册成功
     * 如果客户端的标识码不存在将无法注册
     *
     * @param data 客户端发来的数据
     * @throws IOException 抛出IOException异常
     */
    private void requestsRegister(String data) throws IOException {
        JSONObject jsonObject = JSON.parseObject(data);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        String phone = jsonObject.getString("phone");
        int uid = UidQuery.getNewUid();
        String msg;
        try {
            UserAddition.addUser(uid, username, password, email, phone);
            msg = "{\"success\": true, \"message\": \"注册成功\"}";
        } catch (Exception e) {
            msg = String.format(" {\"success\": false, \"message\": \"%s\"}", e.getMessage());
        }
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        this.server();
        this.server();
        this.server();
        this.server();
        this.server();
    }
}
