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
import java.util.HashSet;
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
        if (record.getCost().compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Invalid record: {}", record);
            return false;
        }
        if (record.getLocationLocation().equals("-") || record.getLocationCountry().equals("-")) {
            log.warn("Invalid record: {}", record);
            return false;
        }
        if (record.getSkuId() == null || record.getSkuId().isEmpty() || record.getSkuId().equals("-")) {
            log.warn("Invalid record: {}", record);
            return false;
        }
        return true;
    }

    @SuppressWarnings("DataFlowIssue")
    private static boolean containsRequiredColumns(String filePath) {
        try (Reader reader = new BufferedReader(new InputStreamReader(CSVParser.class.getClassLoader().getResourceAsStream(filePath)))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] headers = csvReader.readNext();
            List<String> requiredColumns = Arrays.asList("usage_start_time", "usage_end_time", "location.location", "sku.id", "cost", "location.country", "service.id", "service.description", "labels");
            boolean containsAll = new HashSet<>(Arrays.asList(headers)).containsAll(requiredColumns);
            if (!containsAll) {
                log.error("The CSV file does not contain the required columns: {}", requiredColumns);
            }
            return containsAll;
        } catch (Exception ex) {
            throw new RuntimeException("Error while parsing CSV file: " + ex.getMessage());
        }
    }
}