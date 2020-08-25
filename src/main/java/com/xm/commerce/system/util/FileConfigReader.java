package com.xm.commerce.system.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileConfigReader {

    private String[] suffix;

    public String[] getSuffix() {
        return suffix;
    }

    public void setSuffix(String[] suffix) {
        this.suffix = suffix;
    }
}
