package com.example.bitcoin.controller;

import com.example.bitcoin.model.BitcoinPrice;
import com.example.bitcoin.service.BitcoinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinController {
    private final BitcoinService bitcoinService;

    public BitcoinController(BitcoinService bitcoinService) {
        this.bitcoinService = bitcoinService;
    }

    @GetMapping("/price")
    public BitcoinPrice getBitcoinPrice() {
        return this.bitcoinService.fetchCurrentPrice();
    }
}
