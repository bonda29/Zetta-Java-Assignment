package tech.bonda.zja.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bonda.zja.documentation.CostControllerDocumentation;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cost")
public class CostController implements CostControllerDocumentation {

    private final CostService costService;

    @Override
    public ResponseEntity<BigDecimal> getTotalCost(String startTime, String endTime, String location, String skuId) {
        Map<String, String> filters = new HashMap<>();
        filters.put("startTime", startTime);
        filters.put("endTime", endTime);
        filters.put("location", location);
        filters.put("skuId", skuId);

        return ResponseEntity.ok(costService.getTotalCost(filters));
    }

    @Override
    public ResponseEntity<?> getCostGrouped(List<String> fields, boolean isSorted) {
        return ResponseEntity.ok(costService.getCostGrouped(fields, isSorted));
    }

    @Override
    public ResponseEntity<PagedModel<EntityModel<CostRecord>>> searchByLabelAndCountry(
            String labelKey, String labelValue, String country,
            @Min(1) int page,
            @Min(0) @Max(20) int size,
            PagedResourcesAssembler<CostRecord> assembler) {

        Pageable pageRequest = PageRequest.of(page, size);

        List<CostRecord> allEntities = costService.searchByLabelAndCountry(labelKey, labelValue, country);
        if (allEntities.isEmpty()) {
            return ResponseEntity.ok(assembler.toModel(new PageImpl<>(List.of(), pageRequest, 0)));
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allEntities.size());

        List<CostRecord> pageContent = allEntities.subList(start, end);
        Page<CostRecord> costRecordPage = new PageImpl<>(pageContent, pageRequest, allEntities.size());

        return ResponseEntity.ok(assembler.toModel(costRecordPage));
    }
}