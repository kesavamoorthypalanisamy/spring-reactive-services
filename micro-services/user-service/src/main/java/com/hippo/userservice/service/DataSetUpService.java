package com.hippo.userservice.service;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataSetUpService implements CommandLineRunner {
    @Value("classpath:h2/init-data.sql")
    private Resource initSql;
    
    private final R2dbcEntityTemplate entityTemplate;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println("init sql query \n" + query);
        entityTemplate.getDatabaseClient().sql(query).then().subscribe();
    }
    
}
