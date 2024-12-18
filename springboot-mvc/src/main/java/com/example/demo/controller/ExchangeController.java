
package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ExchangeController {


	
    @GetMapping("/exchange")
    public String showExchangeForm(Model model) {
        model.addAttribute("currencies", List.of("USD", "EUR", "JPY", "TWD"));
        return "exchange"; // 對應 exchange.jsp
    }

    @PostMapping("/calculate")
    public String calculateExchange(
            @RequestParam double amount,
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            Model model) {
    	

        // 手動定義的匯率表
        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0); // 1 USD
        exchangeRates.put("EUR", 0.85); // 1 USD to EUR
        exchangeRates.put("JPY", 110.0); // 1 USD to JPY
        exchangeRates.put("TWD", 30.5); // 1 USD to TWD

        if (exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
            double rate = exchangeRates.get(toCurrency);
            double result = amount * rate;
            model.addAttribute("amount", amount);
            model.addAttribute("fromCurrency", fromCurrency);
            model.addAttribute("toCurrency", toCurrency);
            model.addAttribute("result", result);
            return "ExchangeResult"; // 對應 ExchangeResult.jsp
        } else {
            model.addAttribute("error", "無法獲取有效的匯率數據");
            return "exchange";
        }
    }
}
