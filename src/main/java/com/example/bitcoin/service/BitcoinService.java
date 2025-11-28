package com.example.bitcoin.service;

import com.example.bitcoin.model.BitcoinPrice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class BitcoinService {
    private final RestClient restClient = RestClient.create();

    @Value("${COINDESK_API_URL}")
    private String API_URL;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BitcoinPrice fetchCurrentPrice() {
        String rawJson;
        BitcoinPrice bitcoinPrice;
        try {
            rawJson = restClient
                    .get()
                    .uri(API_URL)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            System.err.println("Error while fetching Data: " + e.getMessage());
            return null;
        }

        if (rawJson == null) {
            return null;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(rawJson);

            double priceValue = rootNode
                    .path("price")
                    .asDouble();

            String symbol = rootNode
                    .path("symbol")
                    .asString();


            bitcoinPrice = new BitcoinPrice(priceValue, symbol, LocalDateTime.now());
            System.out.println(
                    "This is the current Price of Bitcoin: "
                            + bitcoinPrice.price()
                            + " with the symbol: "
                            + bitcoinPrice.symbol()
                            + " at the timestamp:"
                            + bitcoinPrice.timestamp()
            );
            return bitcoinPrice;
        } catch (Exception e) {
            System.err.println("Error while parsing JSON: " + e.getMessage());
            return null;
        }

    }
}
