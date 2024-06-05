package tech.bonda.zja.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cost")
public class CostController {

    private final CostService costService;

    @GetMapping("/total-cost")
    public ResponseEntity<BigDecimal> getTotalCost(@RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime,
                                                   @RequestParam(required = false) String location,
                                                   @RequestParam(required = false) String skuId) {

        Map<String, String> filters = new HashMap<>();
        filters.put("startTime", startTime);
        filters.put("endTime", endTime);
        filters.put("location", location);
        filters.put("skuId", skuId);

        return ResponseEntity.ok(costService.getTotalCost(filters));
    }

    @GetMapping("/cost-grouped")
    public ResponseEntity<?> getCostGrouped(@RequestParam List<String> groupByFields) {//todo: return type
        return ResponseEntity.ok(costService.getCostGrouped(groupByFields));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CostRecord>> searchByLabelAndCountry(@RequestParam String label, @RequestParam String country, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(costService.searchByLabelAndCountry(label, country, page, size));
    }

}