package tech.bonda.zja.models.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostGroup {
    private final String group;
    private final BigDecimal cost;
}
