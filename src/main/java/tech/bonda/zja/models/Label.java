package tech.bonda.zja.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Label {
    @JsonProperty("value.stem")
    private String valueStem;

    @JsonProperty("key.prefix")
    private String keyPrefix;

    @JsonProperty("key.stem")
    private String keyStem;

    @JsonProperty("key.enum")
    private String keyEnum;

    @JsonProperty("key.joined")
    private String keyJoined;

    @JsonProperty("value.joined")
    private String valueJoined;

    @JsonProperty("value.prefix")
    private String valuePrefix;

    @JsonProperty("value.delimiter")
    private String valueDelimiter;

    @JsonProperty("value")
    private String value;

    @JsonProperty("key.delimiter")
    private String keyDelimiter;

    @JsonProperty("key")
    private String key;

    @JsonProperty("value.enum")
    private String valueEnum;

}
