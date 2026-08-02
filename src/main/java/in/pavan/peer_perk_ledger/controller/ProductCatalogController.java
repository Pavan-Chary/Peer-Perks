package in.pavan.peer_perk_ledger.controller;

import in.pavan.peer_perk_ledger.dto.ProductResponse;
import in.pavan.peer_perk_ledger.enums.ProductCategory;
import in.pavan.peer_perk_ledger.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ProductCategory category
    ) {
        var productPage = productCatalogService.getAllProducts(page, size, category);


        Page<ProductResponse> responsePage = productPage.map(ProductResponse::fromEntity);

        return ResponseEntity.ok(responsePage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        var product = productCatalogService.getProductById(id);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }
}