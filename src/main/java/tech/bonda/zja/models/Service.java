package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Service {
    @CsvBindByName(column = "service.description")
    private String description;

    @CsvBindByName(column = "service.id")
    private String id;
}
