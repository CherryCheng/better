package top.cherrycheng.better.encryptdecrypt.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ChengRu
 * @date 2020/2/20 15:23
 * @Desc
 */
@Service
public class RsaServiceImpl {
    private String privateKey;
    private String publicKey;

    public RsaServiceImpl() {
    }

    /**
     * 根据名字加载对方公钥，一般此步骤放数据库配置
     */
    public RsaServiceImpl(String name) {
        privateKey = "";
        publicKey = "";
    }

    private String getConfigFileContent(String fileName) throws IOException {
        InputStream stream = null;
        String configFile = "config/" + fileName;
        // 从jar包外读取配置文件
        if (Files.exists(Paths.get(configFile))) {
            stream = new FileInputStream(configFile);
        } else {
            stream = this.getClass().getResourceAsStream("/" + fileName);
        }
        String str = StreamUtils.copyToString(stream, Charset.defaultCharset());
        stream.close();
        return str;
    }

    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
    }
}
