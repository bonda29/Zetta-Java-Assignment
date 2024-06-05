package tech.bonda.zja.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.extern.slf4j.Slf4j;
import tech.bonda.zja.models.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class LabelConverter extends AbstractBeanField<List<Label>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //converts a string to a list of labels and validates the JSON
    @Override
    protected List<Label> convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null || value.isEmpty()) return new ArrayList<>();
        try {
            // Try to parse as a single object
            Label singleObject = objectMapper.readValue(value, Label.class);
            return Collections.singletonList(singleObject);
        } catch (JsonMappingException e) {
            try {
                // Try to parse as a list of objects
                return objectMapper.readValue("[" + value + "]", new TypeReference<List<Label>>() {
                });
            } catch (JsonProcessingException ex) {
                // Return an empty list if JSON is malformed
                return new ArrayList<>();
            }
        } catch (JsonProcessingException e) {
            // Return an empty list if JSON is malformed
            return new ArrayList<>();
        }
    }
}

