package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Credits {
    @CsvBindByName(column = "credits.amount")
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
