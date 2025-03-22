package com.my.influxDB.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "influx.db")
public class InfluxDBProperties {
    private String bucket;
    private String org;
    private String url;
    private String token;
}
