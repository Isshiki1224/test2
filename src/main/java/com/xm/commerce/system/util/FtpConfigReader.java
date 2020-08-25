package com.xm.commerce.system.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfigReader {

    private String ip;
    private String user;
    private String password;
    private String port;
    private String filePath;
    private String parentFilePath;
    private String prvKeyFilePath;
    private String pubKeyFilePath;
    private String domain;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getParentFilePath() {
        return parentFilePath;
    }

    public void setParentFilePath(String parentFilePath) {
        this.parentFilePath = parentFilePath;
    }

    public String getPrvKeyFilePath() {
        return prvKeyFilePath;
    }

    public void setPrvKeyFilePath(String prvKeyFilePath) {
        this.prvKeyFilePath = prvKeyFilePath;
    }

    public String getPubKeyFilePath() {
        return pubKeyFilePath;
    }

    public void setPubKeyFilePath(String pubKeyFilePath) {
        this.pubKeyFilePath = pubKeyFilePath;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>(6);
        map.put("ip", ip);
        map.put("user", user);
        map.put("password", password);
        map.put("port", port);
        map.put("filePath", filePath);
        map.put("parentFilePath", parentFilePath);
        map.put("prvKeyFilePath", prvKeyFilePath);
        map.put("pubKeyFilePath", pubKeyFilePath);
        map.put("domain", domain);
        return map;
    }
}
