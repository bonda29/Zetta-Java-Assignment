package tech.bonda.zja.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.bonda.zja.models.CostRecord;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Cost", description = "Cost related operations")
public interface CostControllerDocumentation {

    @Operation(summary = "Get total cost", description = "Get the total cost based on optional filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Total cost",
                            content = @Content(schema = @Schema(implementation = BigDecimal.class)))
            })
    @GetMapping("/total-cost")
    ResponseEntity<BigDecimal> getTotalCost(
            @Parameter(description = "Start time filter (yyyy-MM-dd or yyyy-MM-dd HH:mm:ss)") @RequestParam(required = false) String startTime,
            @Parameter(description = "End time filter (yyyy-MM-dd or yyyy-MM-dd HH:mm:ss)") @RequestParam(required = false) String endTime,
            @Parameter(description = "Location filter") @RequestParam(required = false) String location,
            @Parameter(description = "SKU ID filter") @RequestParam(required = false) String skuId);

    @Operation(summary = "Get cost grouped", description = "Get the cost grouped by specified fields",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grouped cost data")
            })
    @GetMapping("/cost-grouped")
    ResponseEntity<?> getCostGrouped(
            @Parameter(description = "Fields to group by") @RequestParam(required = false) List<String> fields,
            @Parameter(description = "Whether to sort the grouped results") @RequestParam(defaultValue = "false") boolean isSorted);

    @Operation(summary = "Search by label and country", description = "Search cost records by label key, label value, and country with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paged list of cost records")
            })
    @GetMapping("/search")
    ResponseEntity<PagedModel<EntityModel<CostRecord>>> searchByLabelAndCountry(
            @Parameter(description = "Label key filter") @RequestParam(required = false) String labelKey,
            @Parameter(description = "Label value filter") @RequestParam(required = false) String labelValue,
            @Parameter(description = "Country filter") @RequestParam(required = false) String country,
            @Parameter(description = "Page number") @RequestParam @Min(1) int page,
            @Parameter(description = "Page size (max 20)") @RequestParam @Min(0) @Max(20) int size,
            PagedResourcesAssembler<CostRecord> assembler);
}
