package tech.bonda.zja.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.bonda.zja.models.CostRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CostServiceImplTest {
/*
    @Test
    public void testGetTotalCost_NoFilters() {
        // Arrange
        List<CostRecord> costRecords = new ArrayList<>();
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(10)));
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(20)));
        CostServiceImpl service = new CostServiceImpl();
        CostServiceImpl.costRecords = costRecords;

        // Act
        BigDecimal totalCost = service.getTotalCost(null);

        // Assert
        assertEquals(BigDecimal.valueOf(30), totalCost);
    }

    @Test
    public void testGetTotalCost_WithFilters() {
        // Arrange
        List<CostRecord> costRecords = new ArrayList<>();
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(10)).setUsageStartTime(LocalDateTime.parse("2024-01-01T00:00:00")));
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(20)).setUsageStartTime(LocalDateTime.parse("2024-02-01T00:00:00")));
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(5)).setLocationLocation("NY"));
        CostServiceImpl service = new CostServiceImpl();
        CostServiceImpl.costRecords = costRecords;

        Map<String, String> filters = new HashMap<>();
        filters.put("startTime", "2024-01-01T00:00:00");
        filters.put("endTime", "2024-01-31T00:00:00");

        // Act
        BigDecimal totalCost = service.getTotalCost(filters);

        // Assert
        assertEquals(BigDecimal.valueOf(10), totalCost);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTotalCost_InvalidFilter() {
        // Arrange
        CostServiceImpl service = new CostServiceImpl();

        Map<String, String> filters = new HashMap<>();
        filters.put("invalidKey", "someValue");

        // Act
        service.getTotalCost(filters);
    }

    @Test
    public void testGetCostGrouped_NoGroupBy() {
        // Arrange
        List<CostRecord> costRecords = new ArrayList<>();
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(10)));
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(20)));
        CostServiceImpl service = new CostServiceImpl();
        CostServiceImpl.costRecords = costRecords;

        // Act
        List<Map<List<String>, BigDecimal>> groupedCost = service.getCostGrouped(null, false);

        // Assert
        assertEquals(1, groupedCost.size());
        assertEquals(BigDecimal.valueOf(30), groupedCost.get(0).get(Collections.emptyList()));
    }

    @Test
    public void testGetCostGrouped_WithGroupBy() {
        // Arrange
        List<CostRecord> costRecords = new ArrayList<>();
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(10)).setUsageStartTime(LocalDateTime.parse("2024-01-01T00:00:00")).setLocationCountry("US"));
        costRecords.add(new CostRecord().setCost(BigDecimal.valueOf(20)).setUsageStartTime(LocalDateTime.parse("2024-02-01T00:00:00")).setLocationCountry("UK"));
        CostServiceImpl service = new CostServiceImpl();
        CostServiceImpl.costRecords = costRecords;

        List<String> groupByFields = new ArrayList<>();
        groupByFields.add("country");

        // Act
        List<Map<List<String>, BigDecimal>> groupedCost = service.getCostGrouped(groupByFields, false);
    }*/
}


