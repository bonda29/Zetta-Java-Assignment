package tech.bonda.zja.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;
import tech.bonda.zja.util.CSVParser;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static tech.bonda.zja.util.DateUtil.isAfter;
import static tech.bonda.zja.util.DateUtil.isBefore;

@Service
@Slf4j
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    private static final List<CostRecord> costRecords;

    static {
        costRecords = CSVParser.parseCostRecords("src/main/resources/costs_export.csv");
        log.info("Cost records loaded: {}", costRecords.size());
        log.info("First cost record: {}", costRecords.get(0));
    }


    @Override
    public BigDecimal getTotalCost(Map<String, String> filters) {
        return costRecords.stream()
                .filter(costRecord -> filters.get("startTime") == null || isAfter(costRecord.getUsageStartTime(), filters.get("startTime")))
                .filter(costRecord -> filters.get("endTime") == null || isBefore(costRecord.getUsageStartTime(), filters.get("endTime")))
                .filter(costRecord -> filters.get("location") == null || costRecord.getLocationLocation().equals(filters.get("location")))
                .filter(costRecord -> filters.get("skuId") == null || costRecord.getSkuId().equals(filters.get("skuId")))
                .map(CostRecord::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Map<String, BigDecimal> getCostGrouped(List<String> groupByFields) {
        Map<String, List<CostRecord>> groupedEntries = groupEntries(groupByFields);
        Map<String, BigDecimal> response = new LinkedHashMap<>();

        for (Map.Entry<String, List<CostRecord>> entry : groupedEntries.entrySet()) {
            BigDecimal groupCost = entry.getValue().stream()
                    .map(CostRecord::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.put(entry.getKey(), groupCost);

        }

        return response;
    }

    private Map<String, List<CostRecord>> groupEntries(List<String> groupBy) {
        // i can use Collectors.groupingBy()
        return null;
    }

    @Override
    public List<CostRecord> searchByLabelAndCountry(String label, String country, int page, int size) {
        return null;
    }


}
