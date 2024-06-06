package tech.bonda.zja.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import tech.bonda.zja.models.CostRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CSVParser {

    @SuppressWarnings("DataFlowIssue")
    public static List<CostRecord> parseCostRecords(String filePath) {
        try (Reader reader = new BufferedReader(new InputStreamReader(CSVParser.class.getClassLoader().getResourceAsStream(filePath)))) {

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
}