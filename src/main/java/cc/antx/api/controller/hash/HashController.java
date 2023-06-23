package cc.antx.api.controller.hash;

import cc.antx.utils.string.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HashController {
    @GetMapping(value = "/hash")
    public Object getHash(String s) {
        Map<String, Object> info = new HashMap<>();
        if (!s.equals("")) {
            try {
                info.put("success", true);
                info.put("string", s);
                info.put("md5", StringUtils.getStringMD5(s));
                info.put("sha1", StringUtils.getStringSHA1(s));
                info.put("sha256", StringUtils.getStringSHA256(s));
                info.put("sha512", StringUtils.getStringSHA512(s));
            } catch (Exception e) {
                info.put("success", false);
                info.put("message", e.getMessage());
            }
        }
        return  info;
    }

}
