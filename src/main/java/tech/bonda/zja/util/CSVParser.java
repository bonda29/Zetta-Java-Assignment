package tech.bonda.zja.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import tech.bonda.zja.models.CostRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CSVParser {

    @SuppressWarnings("DataFlowIssue")
    public static List<CostRecord> parseCostRecords(String filePath) {
        try (Reader reader = new BufferedReader(new InputStreamReader(CSVParser.class.getClassLoader().getResourceAsStream(filePath)))) {
            if (!containsRequiredColumns(filePath)) {
                throw new IllegalArgumentException("The CSV file does not contain the required columns.");
            }
            CsvToBean<CostRecord> csvToBean = new CsvToBeanBuilder<CostRecord>(reader)
                    .withType(CostRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.stream()
                    .filter(CSVParser::isValidRecord)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new RuntimeException("Error while parsing CSV file: " + ex.getMessage());
        }
    }

    private static boolean isValidRecord(CostRecord record) {
        boolean response = true;
        if (record.getCost().compareTo(BigDecimal.ZERO) < 0) {
            response = false;
        } else if (record.getLocationLocation().equals("-") || record.getLocationCountry().equals("-")) {
            response = false;
        } else if (record.getSkuId() == null || record.getSkuId().isEmpty() || record.getSkuId().equals("-"))
            response = false;

        if (!response) {
            log.warn("Invalid record: {}", record);
        }

        return response;
    }

    @SuppressWarnings("DataFlowIssue")
    private static boolean containsRequiredColumns(String filePath) {
        try (Reader reader = new BufferedReader(new InputStreamReader(CSVParser.class.getClassLoader().getResourceAsStream(filePath)))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] headers = csvReader.readNext();
            List<String> requiredColumns = Arrays.asList("usage_start_time", "usage_end_time", "location.location", "sku.id", "cost", "location.country", "service.id", "service.description", "labels");
            for (String column : requiredColumns) {
                if (!Arrays.asList(headers).contains(column)) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Error while parsing CSV file: " + ex.getMessage());
        }
    }
}