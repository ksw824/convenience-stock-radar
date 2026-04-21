package org.example.convradar;

import org.example.convradar.domain.stock.service.StockSimulator;
import org.example.convradar.global.util.PublicDataApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConvRadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConvRadarApplication.class, args);
    }
    @Bean
    public CommandLineRunner test(PublicDataApiService apiService, StockSimulator simulator) {
        return args -> {
            // 테스트용 좌표: 강남역 부근
            double testLat = 37.4979;
            double testLon = 127.0276;

            System.out.println("데이터 수집 시작...");
            apiService.saveNearbyStores(testLat, testLon);
            simulator.initStock();

        };
    }

}
