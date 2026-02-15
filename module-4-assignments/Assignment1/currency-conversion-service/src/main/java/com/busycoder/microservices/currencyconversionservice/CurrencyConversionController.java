package com.busycoder.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {
        // Step 2: Use RestTemplate
        // Ideally this should use Eureka service name if RestTemplate is load balanced, 
        // but for Step 2 "initially" instructions usually imply hardcoded URL or simple RestTemplate.
        // However, Step 4 says "Update Feign/RestTemplate calls to use service names".
        // I will keep the Feign version as the primary one since Step 3 overrides Step 2.
        
        // Step 3: Feign
        CurrencyConversion conversion = proxy.retrieveExchangeValue(from, to);
        
        return new CurrencyConversion(conversion.getId(), from, to, quantity, 
                conversion.getConversionMultiple(), 
                quantity.multiply(conversion.getConversionMultiple()), 
                conversion.getEnvironment() + " feign");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {
         CurrencyConversion conversion = proxy.retrieveExchangeValue(from, to);
        
        return new CurrencyConversion(conversion.getId(), from, to, quantity, 
                conversion.getConversionMultiple(), 
                quantity.multiply(conversion.getConversionMultiple()), 
                conversion.getEnvironment() + " feign");
    }
}
