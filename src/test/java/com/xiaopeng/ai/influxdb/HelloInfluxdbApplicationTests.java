package com.xiaopeng.ai.influxdb;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.influxdb.impl.TimeUtil.fromInfluxDBTimeFormat;

@SpringBootTest(
        classes = HelloInfluxdbApplication.class
)
@ExtendWith(SpringExtension.class)
@Slf4j
class HelloInfluxdbApplicationTests {

    @Autowired
    private InfluxDBClient influxDBClient;

    @Test
    void contextLoads() {
    }


    @Test
    public void testQueryInfluxDB() {
        String sql = "select * from xp_ngc_memory_perception";
        String database = "ngc_memory";
        List<Map<String, Object>> queryResult = influxDBClient.query(sql, database);
        log.info("queryResult:{}", JSON.toJSONString(queryResult));
    }
}
