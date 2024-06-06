package tech.bonda.zja.models.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum RequiredFields {
    USAGE_START_TIME("usage_start_time", ZonedDateTime.class),
    USAGE_END_TIME("usage_end_time", ZonedDateTime.class),
    LOCATION("location.location", String.class),
    SKU_ID("sku.id", String.class),
    COST("cost", BigDecimal.class);


    private final String fieldName;
    private final Class<?> fieldType;

    RequiredFields(String fieldName, Class<?> fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public static Map<String, Class<?>> getRequiredFields() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(RequiredFields::getFieldName, RequiredFields::getFieldType));
    }

    public static boolean isValidFieldName(String fieldName) {
        return Arrays.stream(values())
                .anyMatch(field -> field.getFieldName().equals(fieldName));
    }

    public static boolean isValidFieldValue(String fieldName, String fieldValue) {
        RequiredFields requiredField = Arrays.stream(values())
                .filter(field -> field.getFieldName().equals(fieldName))
                .findFirst()
                .orElse(null);

        return requiredField != null;

        // Additional validation logic can be added here
        // For example, check if fieldValue matches the fieldType
    }

}
