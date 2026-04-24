package org.example.convradar.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.convradar.domain.store.entity.Store;
import org.example.convradar.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreImportService {

    private final StoreRepository storeRepository;

    @Transactional
    public void importGyeonggiData() {
        String fileName = "gyeonggi_store_data.csv";
        List<Store> storeList = new ArrayList<>();

        // "A,B" , "C,D" -> 큰따옴마 안의 쉼표는 취급 안하기 위함
        String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(fileName),
                        StandardCharsets.UTF_8))) {

            String line;
            br.readLine(); // 헤더 skip

            while ((line = br.readLine()) != null) {
                // 정규식을 이용한 파싱
                String[] data = line.split(csvSplitBy, -1);

                // 필드 개수 부족한 비정상 데이터 skip
                if (data.length < 14) continue;

                // 1. 영업 상태 필터링 (폐업 매장 거르기)
                if (!"영업".equals(data[3].trim())) continue;

                // 2. 위도/경도 값이 비어있는지 체크
                String latStr = data[12].trim();
                String lonStr = data[13].trim();

                if (latStr.isEmpty() || lonStr.isEmpty()) {
                    log.warn("##### 좌표 데이터가 누락된 매장을 건너뜁니다: {} #####", data[1]);
                    continue;
                }

                try {
                    String fullBizName = data[1].trim();
                    String address = data[9].replace("\"", "").trim();

                    Store store = Store.builder()
                            .cityName(data[0].trim())
                            .storeName(fullBizName)
                            .brandName(extractBrand(fullBizName))
                            .address(address)
                            .latitude(Double.parseDouble(latStr))
                            .longitude(Double.parseDouble(lonStr))
                            .build();

                    storeList.add(store);

                } catch (NumberFormatException e) {
                    log.error("##### [숫자 파싱 에러 (데이터 확인 필요): {}] #####", line);
                    continue;
                }
                if (storeList.size() >= 1000) {
                    storeRepository.saveAll(storeList);
                    storeList.clear();
                    log.info("##### [중간 저장 완료: 1000건 적재...] #####");
                }
            }

            if (!storeList.isEmpty()) {
                storeRepository.saveAll(storeList);
            }
            log.info("##### [경기도 편의점 데이터 적재 완료] #####");

        } catch (Exception e) {
            log.error("데이터 적재 중 오류 발생 (CSV 형식 확인): ", e);
        }
    }

    private String extractBrand(String bizName) {
        String name = bizName.toUpperCase();
        if (name.contains("GS") || name.contains("지에스")) return "GS25";
        if (name.contains("CU") || name.contains("씨유")) return "CU";
        if (name.contains("세븐일레븐")) return "SEVEN_ELEVEN";
        if (name.contains("이마트24") || name.contains("EMART")) return "EMART24";
        if (name.contains("미니스톱")) return "MINISTOP";
        return "OTHER";
    }
}