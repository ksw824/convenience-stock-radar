package org.example.convradar.global.util;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.store.entity.Store;
import org.example.convradar.domain.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PublicDataApiService {

    private final StoreRepository storeRepository;

    @Value("${api.service-key}")
    private String serviceKey;

    public void saveNearbyStores(double lat, double lon) {
//      반경내 상가업소 조회 API
        String url = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInRadius?" +
                "serviceKey=" + serviceKey +
                "&radius=2000" +
                "&cx=" + lon +
                "&cy=" + lat +
                "&indsMclsCd=G204" +
                "&type=json";

        try {
            RestTemplate restTemplate = new RestTemplate();

            // String이 아닌 URI 객체로 변환하여 전달 (RestTemplate의 자동 인코딩 방지)
            URI uri = new URI(url);
            String response = restTemplate.getForObject(uri, String.class);

            System.out.println("응답 데이터 수신 성공!");
            parseAndSave(response);

        } catch (Exception e) {
            System.err.println("API 호출 중 상세 오류 발생: " + e.getMessage());
        }
    }

    private void parseAndSave(String jsonData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonData);
            JsonNode items = root.path("body").path("items");

            if (items.isMissingNode() || !items.isArray()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }

            for (JsonNode item : items) {
                String bizesNm = item.path("bizesNm").asText();
                if (bizesNm.contains("GS25") || bizesNm.contains("CU") || bizesNm.contains("세븐일레븐")) {
                    Store store = Store.builder()
                            .storeName(bizesNm)
                            .brandName(extractBrand(bizesNm))
                            .address(item.path("lnoAdr").asText())
                            .latitude(item.path("lat").asDouble())
                            .longitude(item.path("lon").asDouble())
                            .build();
                    storeRepository.save(store);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractBrand(String name) {
        if (name.contains("GS25")) return "GS25";
        if (name.contains("CU")) return "CU";
        return "SEVEN_ELEVEN";
    }
}