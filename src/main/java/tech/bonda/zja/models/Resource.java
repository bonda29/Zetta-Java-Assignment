package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Resource {
    @CsvBindByName(column = "resource.global_name")
    private String globalName;

    @CsvBindByName(column = "resource.name")
    private String name;
}
