package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping
    String gethome(Model model){
        return "home";
    }
    @GetMapping("/calc")
    public String calc(){
        return "calc";
    }

    @PostMapping("/calc")
    public String calculate(@RequestParam double num1,
                            @RequestParam double num2,
                            @RequestParam String operation,
                            Model model) {
        double result;
        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = (num2 != 0) ? num1 / num2 : Double.NaN;
                break;
            default:
                result = Double.NaN;
        }
        model.addAttribute("result", result);
        return "calc";
    }

    @GetMapping("/convert")
    public String convert() {
        return "convert";
    }

    @PostMapping("/convert")
    public String calc(Model model,
                       @RequestParam(name = "a", required = false, defaultValue = "0") double a,
                       @RequestParam(name = "num1") String val1,
                       @RequestParam(name = "num2") String val2) {

        double conversionRate = getConversionRate(val1, val2);
        double b = a * conversionRate;

        model.addAttribute("a", a);
        model.addAttribute("b", b);
        model.addAttribute("num1", val1);
        model.addAttribute("num2", val2);

        return "convert";
    }

    private double getConversionRate(String num1, String num2) {
        if (num1.equals(num2)) {
            return 1.0;
        }

        switch (num1 + "_" + num2) {
            case "RUB_USD": return 1 / 90.0;
            case "USD_RUB": return 90.0;
            case "RUB_EUR": return 1 / 100.0;
            case "EUR_RUB": return 100.0;
            case "RUB_BYN": return 1 / 30.0;
            case "BYN_RUB": return 30.0;
            case "USD_EUR": return 0.9;
            case "EUR_USD": return 1 / 0.9;
            case "USD_BYN": return 2.5;
            case "BYN_USD": return 1 / 2.5;
            case "EUR_BYN": return 2.7;
            case "BYN_EUR": return 1 / 2.7;
            default: return 0;
        }
    }

}
