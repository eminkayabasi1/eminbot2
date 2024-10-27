package com.app.fku.trendyol.controller;

import com.app.fku.trendyol.fonksiyon.service.TrendyolHesapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trendyolhesap")
public class TrendyolHesapController {

    @Autowired
    TrendyolHesapService trendyolHesapService;
}