package com.matrix.updownload.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @注解：读取配置信息封装成实体类
 * https://www.cnblogs.com/liaojie970/p/8043150.html
 */
@Component
@ConfigurationProperties(prefix = "dev")
public class FileConfiguration {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
