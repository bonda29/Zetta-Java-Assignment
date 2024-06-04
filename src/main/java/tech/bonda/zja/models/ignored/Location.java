package tech.bonda.zja.models.ignored;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Location {
    @CsvBindByName(column = "location.country")
    private String country;

    @CsvBindByName(column = "location.location")
    private String location;

    @CsvBindByName(column = "location.region")
    private String region;

    @CsvBindByName(column = "location.zone")
    private String zone;
}
