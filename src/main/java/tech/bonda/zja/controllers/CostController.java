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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.bonda.zja.models.CostRecord;
import tech.bonda.zja.service.CostService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cost")
public class CostController {

    private final CostService costService;

    @GetMapping("/total-cost")
    public ResponseEntity<BigDecimal> getTotalCost(@RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime,
                                                   @RequestParam(required = false) String location,
                                                   @RequestParam(required = false) String skuId) {

        Map<String, String> filters = new HashMap<>();
        filters.put("startTime", startTime);
        filters.put("endTime", endTime);
        filters.put("location", location);
        filters.put("skuId", skuId);

        return ResponseEntity.ok(costService.getTotalCost(filters));
    }

    @GetMapping("/cost-grouped")
    public ResponseEntity<?> getCostGrouped(@RequestParam(required = false) List<String> fields,
                                            @RequestParam(defaultValue = "false") boolean isSorted) {//todo: return type
        return ResponseEntity.ok(costService.getCostGrouped(fields, isSorted));
    }


    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<CostRecord>>> searchByLabelAndCountry(@RequestParam(required = false) String labelKey,
                                                                                       @RequestParam(required = false) String labelValue,
                                                                                       @RequestParam(required = false) String country,
                                                                                       @RequestParam @Min(1) int page,
                                                                                       @RequestParam @Min(0) @Max(20) int size,
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