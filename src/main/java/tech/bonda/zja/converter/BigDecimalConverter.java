package tech.bonda.zja.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractBeanField<BigDecimal, String> {


    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ("-".equals(value)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value);
    }
}