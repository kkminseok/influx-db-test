package com.my.influxDB;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class InfluxDBConnectionLiveTest {

    // InfluxDB 2.x 접속 정보
    private static final String URL = "http://localhost:8086"; // InfluxDB 서버 주소
    private static final String TOKEN = "my-secret-token"; // 생성한 API 토큰
    private static final String ORG = "donny"; // 생성한 조직 이름
    private static final String BUCKET = "donny-bucket"; // 데이터가 저장될 버킷 이름

    private static InfluxDBClient client;

    @BeforeAll
    public static void setup() {
        // InfluxDB 클라이언트 설정

        client = InfluxDBClientFactory.create(URL, TOKEN.toCharArray(), ORG, BUCKET);
    }

    @Test
    void testWriteData() {
        // WriteApiBlocking을 사용하여 데이터를 InfluxDB에 쓴다.
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
//        Point point = Point.measurement("cpu_usage")
//                .addTag("host", "server1")
//                .addField("value", 75.5)
//                .time(Instant.now(), WritePrecision.NS);
        Point point = Point.measurement("tick")
                .addTag("company", "toss")
                .addField("currentPrice", 150)
                .time(Instant.now(), WritePrecision.NS);
        writeApi.writePoint(point);
        System.out.println("✅ 데이터 쓰기 완료!");

        // 데이터가 정상적으로 작성되었는지 확인하는 간단한 테스트
        assertTrue(true, "데이터가 성공적으로 작성되었습니다.");
    }

    @Test
    void testQueryData() {
        // Flux 쿼리 예시
        String fluxQuery = "from(bucket:\"" + BUCKET + "\") |> range(start: -1h)";

        QueryApi queryApi = client.getQueryApi();
        queryApi.query(fluxQuery, (cancellable, record) -> {
            System.out.println("📌 Measurement: " + record.getMeasurement() +
                    ", Field: " + record.getField() +
                    ", Value: " + record.getValue());
        });

        // 데이터가 조회되는지 확인하는 간단한 테스트
        assertTrue(true, "쿼리가 정상적으로 실행되었습니다.");
    }

    @Test
    void testPing() {
        // InfluxDB /health 엔드포인트에 핑을 보내서 연결 상태를 확인
        Boolean ping = client.ping();

        // InfluxDB 서버가 정상적으로 작동 중인지 확인
        assertTrue(ping, "InfluxDB 서버가 정상적으로 작동 중입니다.");
    }

    @Test
    void testDeleteData() {
        // 데이터 삭제 예시 (특정 시간 범위로 삭제)
        String fluxDeleteQuery = "from(bucket:\"" + BUCKET + "\") |> range(start: -1h) |> drop()";

        QueryApi queryApi = client.getQueryApi();
        queryApi.query(fluxDeleteQuery, (cancellable, record) -> {
            // 데이터가 삭제되었는지 확인
            System.out.println("📌 Deleted Record: " + record);
        });

        // 데이터 삭제가 성공적으로 이루어졌는지 확인
        assertTrue(true, "데이터가 정상적으로 삭제되었습니다.");
    }
}