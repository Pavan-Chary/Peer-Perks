package in.pavan.peer_perk_ledger.dto;

import in.pavan.peer_perk_ledger.enums.ProductCategory;
import in.pavan.peer_perk_ledger.model.ProductCatalog;

public record ProductResponse(
        Long id,
        String name,
        String description,
        ProductCategory category,
        Integer pointsRequired
) {
    public static ProductResponse fromEntity(ProductCatalog product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPointsRequired()
        );
    }
}