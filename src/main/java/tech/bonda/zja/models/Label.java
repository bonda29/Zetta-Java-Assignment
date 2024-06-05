package tech.bonda.zja.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Label {
    @JsonProperty("value.stem")
    private List<String> valueStem;

    @JsonProperty("key.prefix")
    private List<String> keyPrefix;

    @JsonProperty("key.stem")
    private List<String> keyStem;

    @JsonProperty("key.enum")
    private List<String> keyEnum;

    @JsonProperty("key.joined")
    private List<String> keyJoined;

    @JsonProperty("value.joined")
    private List<String> valueJoined;

    @JsonProperty("value.prefix")
    private List<String> valuePrefix;

    @JsonProperty("value.delimiter")
    private List<String> valueDelimiter;

    @JsonProperty("value")
    private List<String> value;

    @JsonProperty("key.delimiter")
    private List<String> keyDelimiter;

    @JsonProperty("key")
    private List<String> key;

    @JsonProperty("value.enum")
    private List<String> valueEnum;

}
