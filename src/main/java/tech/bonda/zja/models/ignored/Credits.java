package tech.bonda.zja.models.ignored;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import tech.bonda.zja.converter.BigDecimalConverter;

import java.math.BigDecimal;

@Data
public class Credits {
    @CsvCustomBindByName(column = "credits.amount", converter = BigDecimalConverter.class)
    private BigDecimal amount;

    @CsvBindByName(column = "credits.full_name")
    private String fullName;

    @CsvBindByName(column = "credits.id")
    private String id;

    @CsvBindByName(column = "credits.name")
    private String name;

    @CsvBindByName(column = "credits.type")
    private String type;
}
