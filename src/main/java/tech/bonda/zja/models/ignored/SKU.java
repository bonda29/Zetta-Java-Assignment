package tech.bonda.zja.models.ignored;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class SKU {
    @CsvBindByName(column = "sku.description")
    private String description;

    @CsvBindByName(column = "sku.id")
    private String id;
}
