package tech.bonda.zja.service.impl;

import jakarta.annotation.PostConstruct;
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
import java.util.stream.Collectors;

import static tech.bonda.zja.util.DateUtil.isAfter;
import static tech.bonda.zja.util.DateUtil.isBefore;

/**
 * Service implementation for handling cost records.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    /**
     * List to store the loaded from file cost records.
     */
    private static final List<CostRecord> costRecords = new ArrayList<>();
    private static final String CSV_FILE_PATH = "src/main/resources/costs_export.csv";

    /**
     * Loading the cost records from the file after the Spring context is fully initialized.
     */
    @PostConstruct
    public void loadCostRecords() {
        log.info("Loading cost records...");

        long startTime = System.currentTimeMillis();
        costRecords.addAll(CSVParser.parseCostRecords(CSV_FILE_PATH));
        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("Cost records loaded: {}", costRecords.size());
        log.info("Time taken to load cost records: {} ms", elapsedTime);
    }

    /**
     * Calculate the total cost based on provided filters.
     *
     * @param filters Map of filters to apply on the cost records.
     *                Supported filters:
     *                - startTime: Filter by the usage start time (inclusive).
     *                - endTime: Filter by the usage end time (inclusive).
     *                - location: Filter by the location.
     *                - skuId: Filter by the SKU ID.
     * @return The total cost as a BigDecimal.
     */
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

    /**
     * Group cost records based on provided fields and sort them if required.
     *
     * @param groupByFields Fields to group the cost records by.
     *                      Supported fields:
     *                      - date: Group by the usage start date (format: yyyy-MM-dd)
     *                      - country: Group by the location country code.
     *                      - service: Group by the service ID.
     * @param isSorted      Should the result be sorted.
     * @return A list of maps, each containing a group of cost records and their total cost.
     */
    @Override
    public List<Map<List<String>, BigDecimal>> getCostGrouped(List<String> groupByFields, boolean isSorted) {
        Map<List<String>, List<CostRecord>> groupedEntries = groupEntries(groupByFields);

        List<Map<List<String>, BigDecimal>> response = groupedEntries.entrySet().parallelStream()
                .map(entry -> {
                    BigDecimal groupCost = entry.getValue().parallelStream()
                            .map(CostRecord::getCost)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return Map.of(entry.getKey(), groupCost);
                })
                .collect(Collectors.toList());

        if (isSorted) { // I didn't know if I should sort the elements
            response.sort(Comparator.comparing(entry -> entry.keySet().iterator().next().get(0)));
        }

        return response;
    }

    /**
     * Helper method witch groups cost records based.
     *
     * @param groupBy List of fields to group the cost records by.
     * @return A map of grouped cost records.
     */
    private Map<List<String>, List<CostRecord>> groupEntries(List<String> groupBy) {
        if (groupBy == null || groupBy.isEmpty()) {
            return Map.of(List.of("cost"), costRecords);
        }

        return costRecords.parallelStream().collect(
                Collectors.groupingBy(
                        costRecord -> groupBy.stream()
                                .map(group -> switch (group) {
                                    case "date" -> costRecord.getUsageStartTime().toLocalDate().toString();
                                    case "country" -> costRecord.getLocationCountry();
                                    case "service" -> costRecord.getServiceId();
                                    default ->
                                            throw new IllegalArgumentException("Group by field " + group + " not supported");
                                })
                                .collect(Collectors.toList())
                )
        );
    }

    /**
     * Search cost records by label and country.
     *
     * @param labelKey   The label key to search by.
     * @param labelValue The label value to search by.
     * @param country    The country to search by.
     * @return A list of cost records that match the criteria.
     */
    @Override
    public List<CostRecord> searchByLabelAndCountry(String labelKey, String labelValue, String country) {
        List<CostRecord> filteredRecords = costRecords;

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
