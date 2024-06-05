package tech.bonda.zja.service;


import tech.bonda.zja.models.CostRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CostService {
    BigDecimal getTotalCost(Map<String, String> filters);

    List<Map<List<String>, BigDecimal>> getCostGrouped(List<String> groupByFields);

    List<CostRecord> searchByLabelAndCountry(String label, String country, int page, int size);
}
