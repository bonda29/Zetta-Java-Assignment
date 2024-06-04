package tech.bonda.zja.models.ignored;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import tech.bonda.zja.converter.BigDecimalConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Usage {
    @CsvCustomBindByName(column = "usage.amount", converter = BigDecimalConverter.class)
    private BigDecimal amount;

    @CsvCustomBindByName(column = "usage.amount_in_pricing_units", converter = BigDecimalConverter.class)
    private BigDecimal amountInPricingUnits;

    @CsvBindByName(column = "usage.pricing_unit")
    private String pricingUnit;

    @CsvBindByName(column = "usage.unit")
    private String unit;

}
