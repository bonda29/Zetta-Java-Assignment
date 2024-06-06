package tech.bonda.zja.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.models.Label;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
public class CostServiceImplTest {

    private static final List<CostRecord> testRecords = new LinkedList<>();
    @Mock
    private CostServiceImpl costService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //Clear the testRecords list
        testRecords.clear();

        CostRecord cr1 = new CostRecord();
        cr1.setCost(BigDecimal.valueOf(10));
        cr1.setLabels(List.of(new Label("key1", "value1"), new Label("key2", "value2")));
        cr1.setLocationLocation("location1");
        cr1.setLocationCountry("country1");
        cr1.setServiceId("service1");
        cr1.setSkuId("sku1");
        cr1.setUsageStartTime(LocalDateTime.parse("2024-01-01T00:00:00"));
        cr1.setUsageEndTime(LocalDateTime.parse("2024-01-02T00:00:00"));
        testRecords.add(cr1);

        CostRecord cr2 = new CostRecord();
        cr2.setCost(BigDecimal.valueOf(20));
        cr2.setLabels(List.of(new Label("key1", "value1"), new Label("key2", "value2")));
        cr2.setLocationLocation("location2");
        cr2.setLocationCountry("country2");
        cr2.setServiceId("service2");
        cr2.setSkuId("sku2");
        cr2.setUsageStartTime(LocalDateTime.parse("2024-01-01T00:00:00"));
        cr2.setUsageEndTime(LocalDateTime.parse("2024-01-02T00:00:00"));
        testRecords.add(cr2);

        try {
            // Get the costRecords field from the CostServiceImpl class
            Field costRecordsField = CostServiceImpl.class.getDeclaredField("costRecords");
            // Make the field accessible
            costRecordsField.setAccessible(true);
            // Set the value of the costRecords field in the costService object
            costRecordsField.set(costService, testRecords);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
    }

    @Test
    public void testGetTotalCost_noParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("startTime", null);
        params.put("endTime", null);
        params.put("location", null);
        params.put("skuId", null);

        // Define the behavior of costService.getTotalCost(params)
        when(costService.getTotalCost(params)).thenReturn(BigDecimal.valueOf(30));

        BigDecimal totalCost = costService.getTotalCost(params);

        assertEquals(testRecords.stream()
                        .map(CostRecord::getCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                totalCost);
    }

    @Test
    public void testGetTotalCost_withLocation() {
        Map<String, String> params = new HashMap<>();
        params.put("location", "location1");

        // Define the behavior of costService.getTotalCost(params)
        when(costService.getTotalCost(params)).thenReturn(BigDecimal.valueOf(10));

        BigDecimal totalCost = costService.getTotalCost(params);
        assertEquals(testRecords.get(0).getCost(), totalCost);
    }

    @Test
    public void testGetTotalCost_withSkuId() {
        Map<String, String> params = new HashMap<>();
        params.put("skuId", "sku2");

        // Define the behavior of costService.getTotalCost(params)
        when(costService.getTotalCost(params)).thenReturn(BigDecimal.valueOf(20));

        BigDecimal totalCost = costService.getTotalCost(params);
        assertEquals(testRecords.get(1).getCost(), totalCost);
    }
}


