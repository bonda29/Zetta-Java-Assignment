package tech.bonda.zja.models.ignored;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import tech.bonda.zja.converter.BigDecimalConverter;

import java.math.BigDecimal;

@Data
public class Price {
    @CsvCustomBindByName(column = "price.effective_price", converter = BigDecimalConverter.class)
    private BigDecimal effectivePrice;

    @CsvCustomBindByName(column = "price.pricing_unit_quantity", converter = BigDecimalConverter.class)
    private BigDecimal pricingUnitQuantity;

    @CsvCustomBindByName(column = "price.tier_start_amount", converter = BigDecimalConverter.class)
    private BigDecimal tierStartAmount;

    @CsvBindByName(column = "price.unit")
    private String unit;
}
