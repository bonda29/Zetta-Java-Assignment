package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostRecord {
    @CsvBindByName(column = "_id")
    private String id;

    @CsvBindByName(column = "billing_account_id")
    private String billingAccountId;

    @CsvBindByName(column = "cost")
    private BigDecimal cost;

    @CsvBindByName(column = "cost_at_list")
    private BigDecimal costAtList;

    @CsvBindByName(column = "cost_type")
    private String costType;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "currency_conversion_rate")
    private BigDecimal currencyConversionRate;

    @CsvBindByName(column = "invoice_month")
    private String invoiceMonth;

    @CsvBindByName(column = "labels")
    private String labels;

    @CsvBindByName(column = "project_id")
    private String projectId;

    @CsvBindByName(column = "transaction_type")
    private String transactionType;


    private Credits credits;
    private Location location;
    private Price price;
    private Resource resource;
    private Service service;
    private SKU sku;
    private SystemLabels systemLabels;
    private Usage usage;
}
