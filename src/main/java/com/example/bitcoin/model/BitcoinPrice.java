package com.example.bitcoin.model;

import java.time.LocalDateTime;

public record BitcoinPrice(double price, String symbol, LocalDateTime timestamp) { }