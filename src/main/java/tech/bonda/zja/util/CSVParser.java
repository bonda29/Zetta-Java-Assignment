package tech.bonda.zja.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import tech.bonda.zja.models.CostRecord;

import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {

    public static List<CostRecord> parseCostRecords(String filePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {

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
            return false;
        } else if (record.getLocationLocation().equals("-") || record.getLocationCountry().equals("-")) {
            return false;
        } else return record.getSkuId() != null && !record.getSkuId().isEmpty() && !record.getSkuId().equals("-");
    }
}