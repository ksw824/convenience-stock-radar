package org.example.convradar;

import lombok.extern.slf4j.Slf4j;
import org.example.convradar.domain.stock.service.StockSimulator;
import org.example.convradar.domain.store.service.StoreImportService;
import org.example.convradar.global.util.PublicDataApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class ConvRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConvRadarApplication.class, args);
    }

/**
    경기도 내 운영 중인 편의점 데이터 적재 용도 (최초 1번만 실행)
 */
//    @Bean
//    public CommandLineRunner insert(StoreImportService importService) {
//        return args -> {
//            log.info("경기도 운영 중인 편의점 csv 데이터 적재 시작-----");
//            importService.importGyeonggiData();
//        };
//    }

}
