package com.xiaopeng.ai.influxdb;

import com.alibaba.fastjson.JSON;
import com.xiaopeng.ai.influxdb.config.InfluxDBConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.influxdb.impl.TimeUtil.fromInfluxDBTimeFormat;

@Service
@Slf4j
public class InfluxDBClient {
    @Autowired
    private InfluxDBConfig influxDBConfig;
    private InfluxDB influxDB;

    @PostConstruct
    public void initInfluxDB() {
        this.influxDB = InfluxDBFactory.connect(influxDBConfig.getUrl(), influxDBConfig.getUserName(), influxDBConfig.getPassword());
        log.info("influxDB init success ====================");
    }


    /**
     * 查询方法
     *
     * @param sql
     * @param database
     * @return
     */
    public List<Map<String, Object>> query(String sql, String database) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            Query query = new Query(sql, database);
            QueryResult result = influxDB.query(query);
            if (result.hasError()) {
                throw new RuntimeException("Error executing query: " + result.getError());
            }
            if (result.getResults().isEmpty() || result.getResults().get(0).getSeries() == null) {
                return resultList;
            }
            QueryResult.Series series = result.getResults().get(0).getSeries().get(0);
            List<String> columns = series.getColumns();
            List<List<Object>> valuesList = series.getValues();
            for (List<Object> value : valuesList) {
                Map<String, Object> resultMap = new HashMap<>();
                for (int i = 0; i < columns.size(); i++) {
                    if ("time".equals(columns.get(i))) {
                        resultMap.put(columns.get(i), fromInfluxDBTimeFormat((String) value.get(i)));
                    } else {
                        resultMap.put(columns.get(i), value.get(i));
                    }
                }
                resultList.add(resultMap);
            }
            return resultList;
        } catch (Exception e) {
            log.error("query error", e);
            return resultList;
        }
    }

    /**
     * 批量插入方法
     */
}
