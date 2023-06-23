package cc.antx.api.controller.ip;

import cc.antx.utils.ip.IpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @date 2023-03-05 9:38
 */
@RestController
public class QueryIpController {
    @RequestMapping("/ip")
    public Object test(HttpServletRequest request, String ip) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (ip == null) {
            try {
                String addr = request.getHeader("x-forwarded-for");
                if (!checkIP(addr)) {
                    addr = request.getHeader("Proxy-Client-IP");
                }
                if (!checkIP(addr)) {
                    addr = request.getHeader("WL-Proxy-Client-IP");
                }
                if (!checkIP(addr)) {
                    addr = request.getRemoteAddr();
                }
                result.put("success", true);
                result.put("addr", addr);
            } catch (Exception e) {
                result.put("success", false);
                result.put("message", "Server Error");
            }
            return result;
        } else {
            try {
                result = IpUtils.getInfo(ip);
            } catch (Exception e) {
                result.put("success", false);
                result.put("message", "IP addr error");
            }
        }
        return result;
    }

    public static boolean checkIP(String ip) {
        return ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)
                && ip.split("").length == 4;
    }
}
