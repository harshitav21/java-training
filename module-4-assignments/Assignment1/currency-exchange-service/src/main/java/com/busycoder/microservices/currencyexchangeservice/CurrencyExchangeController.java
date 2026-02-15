package com.busycoder.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        // Hardcoded values as requested in Step 1
        ExchangeValue exchangeValue = new ExchangeValue(1001L, from, to, BigDecimal.valueOf(65));
        
        // Dynamic environment port
        String port = environment.getProperty("local.server.port");
        exchangeValue.setEnvironment(port);
        
        return exchangeValue;
    }
}
