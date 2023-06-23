package cc.antx.api.controller.host;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @date 2023-04-02 13:55
 */
@RestController
public class HostController {

    @GetMapping("/host")
    public Map<String, Object> host(String host) {
        Map<String, Object> result = new HashMap<>();
        try {
            InetAddress inetAddr = InetAddress.getByName(host);
            result.put("success", true);
            result.put("addr", inetAddr.getHostAddress());
            result.put("host", inetAddr.getHostName());
            result.put("domain", inetAddr.getCanonicalHostName());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "The host does not exist");
        }
        return result;
    }
}
