package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Usage {
    @CsvBindByName(column = "usage.amount")
    private BigDecimal amount;

    @CsvBindByName(column = "usage.amount_in_pricing_units")
    private BigDecimal amountInPricingUnits;

    @CsvBindByName(column = "usage.pricing_unit")
    private String pricingUnit;

    @CsvBindByName(column = "usage.unit")
    private String unit;

    @CsvBindByName(column = "usage_end_time")
    private LocalDateTime usageEndTime;

    @CsvBindByName(column = "usage_start_time")
    private LocalDateTime usageStartTime;
}
