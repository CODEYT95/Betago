package com.bnw.beta.controller.admin;

import com.bnw.beta.service.admin.sales.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sales")
@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    //월간 (일일 단위 매출조회)
    @GetMapping("/dailyList")
    public String selectDailySales() {
        return "admin/game/gameSales";
    }

    //년간 (월 단위 매출조회)
    @GetMapping("/monthlyList")
    public String selectMonthlySales() {
        return "admin/game/gameSales";
    }
}
