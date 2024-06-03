package tech.bonda.zja.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    @Override
    public BigDecimal getTotalCost(Map<String, String> filters) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> getCostGrouped(List<String> groupByFields) {
        return null;
    }

    @Override
    public List<CostRecord> searchByLabelAndCountry(String label, String country, int page, int size) {
        return null;
    }
}
