package cc.antx.api.controller.qrcode;

import cc.antx.utils.qrcode.QRCodeUtils;
import cc.antx.utils.string.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author zhong
 * @date 2023-03-05 20:10
 */
@RestController
public class QRCodeController {

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public Object getImage(HttpServletResponse response, String t) {
        try {
            response.setContentType("image/png");
            if (!t.equals("")) {
                String path = "C:/www/temp/api/image/qr/";
                String name = path + StringUtils.getStringSHA256(t) + ".png";
                File file = new File(name);
                if (!file.exists() || !file.isFile()) {
                    name = QRCodeUtils.generateQRCode(t);
                }
                return ImageIO.read(new FileInputStream(name));
            } else {
                return "{\"success\": false, \"message\": \"The text is null\"}";
            }
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }

    }

}