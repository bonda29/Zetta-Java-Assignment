package tech.bonda.zja.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import tech.bonda.zja.converter.BigDecimalConverter;
import tech.bonda.zja.converter.DateConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CostRecord {
    @CsvBindByName(column = "_id")
    private String id;

    @CsvBindByName(column = "billing_account_id")
    private String billingAccountId;

    @CsvCustomBindByName(column = "cost", converter = BigDecimalConverter.class)
    private BigDecimal cost;

    @CsvCustomBindByName(column = "cost_at_list", converter = BigDecimalConverter.class)
    private BigDecimal costAtList;

    @CsvBindByName(column = "cost_type")
    private String costType;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "invoice.month")
    private String invoiceMonth;

    @CsvBindByName(column = "labels")
    private String labels;

    @CsvBindByName(column = "location.country")
    private String locationCountry;

    @CsvBindByName(column = "location.location")
    private String locationLocation;

    @CsvBindByName(column = "location.region")
    private String locationRegion;

    @CsvBindByName(column = "location.zone")
    private String locationZone;

    @CsvCustomBindByName(column = "price.effective_price", converter = BigDecimalConverter.class)
    private BigDecimal effectivePrice;

    @CsvCustomBindByName(column = "price.pricing_unit_quantity", converter = BigDecimalConverter.class)
    private BigDecimal pricingUnitQuantity;

    @CsvCustomBindByName(column = "price.tier_start_amount", converter = BigDecimalConverter.class)
    private BigDecimal tierStartAmount;

    @CsvBindByName(column = "price.unit")
    private String priceUnit;

    @CsvBindByName(column = "project.id")
    private String projectId;

    @CsvBindByName(column = "service.description")
    private String serviceDescription;

    @CsvBindByName(column = "service.id")
    private String serviceId;

    @CsvBindByName(column = "sku.description")
    private String skuDescription;

    @CsvBindByName(column = "sku.id")
    private String skuId;

    @CsvBindByName(column = "transaction_type")
    private String transactionType;

    @CsvCustomBindByName(column = "usage.amount", converter = BigDecimalConverter.class)
    private BigDecimal usageAmount;

    @CsvCustomBindByName(column = "usage.amount_in_pricing_units", converter = BigDecimalConverter.class)
    private BigDecimal amountInPricingUnits;

    @CsvBindByName(column = "usage.pricing_unit")
    private String usagePricingUnit;

    @CsvBindByName(column = "usage.unit")
    private String usageUnit;

    @CsvCustomBindByName(column = "usage_end_time", converter = DateConverter.class)
    private LocalDateTime usageEndTime;

    @CsvCustomBindByName(column = "usage_start_time", converter = DateConverter.class)
    private LocalDateTime usageStartTime;
}