package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class SystemLabels {
    @CsvBindByName(column = "system_labels.key")
    private String key;

    @CsvBindByName(column = "system_labels.value")
    private String value;
}
