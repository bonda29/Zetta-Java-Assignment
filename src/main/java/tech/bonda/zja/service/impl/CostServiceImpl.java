package tech.bonda.zja.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;
import tech.bonda.zja.util.CSVParser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static tech.bonda.zja.util.DateUtil.isAfter;
import static tech.bonda.zja.util.DateUtil.isBefore;

@Service
@Slf4j
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    private static final List<CostRecord> costRecords = new ArrayList<>();

    static {
        log.info("Loading cost records...");

        // Capture the start time
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) { // Load the records n times for testing
            costRecords.addAll(CSVParser.parseCostRecords("src/main/resources/costs_export.csv"));
        }
        //sort the records by id
        costRecords.sort(Comparator.comparing(CostRecord::getId));

        // Capture the end time
        long endTime = System.currentTimeMillis();

        // Calculate the elapsed time
        long elapsedTime = endTime - startTime;

        log.info("Cost records loaded: {}", costRecords.size());
        log.info("First cost record: {}", costRecords.get(60));
        log.info("Time taken to load cost records: {} ms", elapsedTime);
    }

    @Override
    public BigDecimal getTotalCost(Map<String, String> filters) {
        return costRecords.parallelStream()
                .filter(costRecord -> filters.get("startTime") == null || isAfter(costRecord.getUsageStartTime(), filters.get("startTime")))
                .filter(costRecord -> filters.get("endTime") == null || isBefore(costRecord.getUsageStartTime(), filters.get("endTime")))
                .filter(costRecord -> filters.get("location") == null || costRecord.getLocationLocation().equals(filters.get("location")))
                .filter(costRecord -> filters.get("skuId") == null || costRecord.getSkuId().equals(filters.get("skuId")))
                .map(CostRecord::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*    @Override
        public Map<String, BigDecimal> getCostGrouped(List<String> groupByFields) {
            Map<String, List<CostRecord>> groupedEntries = groupEntries(groupByFields);
            Map<String, BigDecimal> response = new ConcurrentHashMap<>();

            groupedEntries.entrySet().parallelStream().forEach(entry -> {
                BigDecimal groupCost = entry.getValue().parallelStream()
                        .map(CostRecord::getCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                response.put(entry.getKey(), groupCost);
            });

            return response;
        }*/
    /*
    private Map<String, List<CostRecord>> groupEntries(List<String> groupBy) {
        Map<String, Function<CostRecord, String>> groupByFunctions = new HashMap<>();
        groupByFunctions.put("date", costRecord -> costRecord.getUsageStartTime().toLocalDate().toString());
        groupByFunctions.put("country", CostRecord::getLocationCountry);
        groupByFunctions.put("service", CostRecord::getServiceId);

        Map<String, List<CostRecord>> groupedEntries = new ConcurrentHashMap<>();

        groupBy.parallelStream().forEach(group -> {
            Function<CostRecord, String> groupByFunction = groupByFunctions.get(group);
            if (groupByFunction != null) {
                Map<String, List<CostRecord>> groupedByCurrentField = costRecords.parallelStream()
                        .collect(Collectors.groupingBy(groupByFunction));
                groupedByCurrentField.forEach((key, value) -> groupedEntries.put(group + ":" + key, value));
            }
        });

        return groupedEntries;
    }
*/
    @Override
    public List<Map<List<String>, BigDecimal>> getCostGrouped(List<String> groupByFields, boolean isSorted) {
        var groupedEntries = groupEntries(groupByFields);
        List<Map<List<String>, BigDecimal>> response = new ArrayList<>();

        groupedEntries.entrySet().parallelStream().forEach(entry -> {
            BigDecimal groupCost = entry.getValue().parallelStream()
                    .map(CostRecord::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<List<String>, BigDecimal> group = new ConcurrentHashMap<>();
            group.put(entry.getKey(), groupCost);
            response.add(group);
        });

        if (isSorted) {
            return sortGroupedEntries(response);
        }

        return response;
    }

    private Map<List<String>, List<CostRecord>> groupEntries(List<String> groupBy) {
        if (groupBy == null || groupBy.isEmpty()) {
            Map<List<String>, List<CostRecord>> response = new ConcurrentHashMap<>();
            List<String> key = new ArrayList<>();
            key.add("cost");
            response.put(key, costRecords);
            return response;
        }

        Map<List<String>, List<CostRecord>> groupedEntries = new ConcurrentHashMap<>();

        List<Function<CostRecord, String>> groupByFunctions = new ArrayList<>();
        for (String group : groupBy) {
            switch (group) {
                case "date":
                    groupByFunctions.add(costRecord -> costRecord.getUsageStartTime().toLocalDate().toString());
                    break;
                case "country":
                    groupByFunctions.add(CostRecord::getLocationCountry);
                    break;
                case "service":
                    groupByFunctions.add(CostRecord::getServiceId);
                    break;
                default:
                    throw new IllegalArgumentException("Group by field " + group + " not supported");
            }
        }

        groupedEntries = costRecords.parallelStream().collect(
                Collectors.groupingBy(
                        costRecord -> groupByFunctions.stream()
                                .map(func -> func.apply(costRecord))
                                .collect(Collectors.toList()),
                        Collectors.toList()
                )
        );

        return groupedEntries;
    }

    private List<Map<List<String>, BigDecimal>> sortGroupedEntries(List<Map<List<String>, BigDecimal>> groupedEntries) {
        return groupedEntries.stream()
                .sorted((entry1, entry2) -> {
                    List<String> key1 = entry1.keySet().iterator().next();
                    List<String> key2 = entry2.keySet().iterator().next();
                    return key1.get(0).compareTo(key2.get(0));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CostRecord> searchByLabelAndCountry(String labelKey, String labelValue, String country) {
        List<CostRecord> filteredRecords = costRecords;
//        System.out.println(costRecords.stream().filter(
//                        costRecord -> costRecord.getId().equals("xIzECo8BV89PSUMNb_Xl")
//                ).collect(Collectors.toList())
//        );

        if (labelKey != null && labelValue != null)
            filteredRecords = filterByLabelKeyAndValue(costRecords, labelKey, labelValue);
        else if (country != null)
            filteredRecords = filterByCountry(filteredRecords, country);

        return filteredRecords;
    }

    @SuppressWarnings({"SameParameterValue"})
    private List<CostRecord> filterByLabelKeyAndValue(List<CostRecord> costRecords, String labelKey, String labelValue) {
        return costRecords.parallelStream()
                .filter(costRecord -> costRecord.getLabels().stream()
                        .anyMatch(label -> label.getKey().equals(labelKey) && label.getValue().equals(labelValue)))
                .collect(Collectors.toList());
    }

    private List<CostRecord> filterByCountry(List<CostRecord> costRecords, String country) {
        return costRecords.parallelStream()
                .filter(costRecord -> costRecord.getLocationCountry().equals(country))
                .collect(Collectors.toList());
    }

}
