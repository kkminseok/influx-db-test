package com.my.influxDB;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.my.influxDB.config.InfluxDBProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
public class InfluxDBCreateMeasurementTest {

    @Autowired
    private InfluxDBProperties properties;

    private InfluxDBClient client;

    @BeforeEach
    public void setup() {
        // InfluxDB 클라이언트 설정
        client = InfluxDBClientFactory.create(
                properties.getUrl(),
                properties.getToken().toCharArray(),
                properties.getOrg(),
                properties.getBucket());
    }

    @Test
    void testPing() {
        // InfluxDB /health 엔드포인트에 핑을 보내서 연결 상태를 확인
        Boolean ping = client.ping();

        // InfluxDB 서버가 정상적으로 작동 중인지 확인
        System.out.println("ping is " + ping);
        assertTrue(ping, "InfluxDB 서버가 정상적으로 작동 중입니다.");
    }

    @Test
    void newMeasurementWriteTest() {
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        Point point = Point.measurement("stock")
                .addTag("company", "toss")
                .addField("current_price", 150)
                .addField("high_price",197.4100)
                .addField("low_price", 197.2800)
                .addField("close_price", 197.4100)
                .addField("open_price", 197.3400)
                .time(Instant.now(), WritePrecision.NS);
        writeApi.writePoint(point);

    }





}
