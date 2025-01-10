package com.xiaopeng.ai.influxdb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
    @Value("${spring.influx.user:}")
    private String userName;
    @Value("${spring.influx.password:}")
    private String password;
    @Value("${spring.influx.url}")
    private String url;
    @Value("${spring.influx.database}")
    private String database;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
