package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Price {
    @CsvBindByName(column = "price.effective_price")
    private BigDecimal effectivePrice;

    @CsvBindByName(column = "price.pricing_unit_quantity")
    private BigDecimal pricingUnitQuantity;

    @CsvBindByName(column = "price.tier_start_amount")
    private BigDecimal tierStartAmount;

    @CsvBindByName(column = "price.unit")
    private String unit;
}
