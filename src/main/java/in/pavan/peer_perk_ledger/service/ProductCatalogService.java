package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.enums.ProductCategory;
import in.pavan.peer_perk_ledger.model.ProductCatalog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductCatalogService {
    Page<ProductCatalog> getAllProducts(int pageNum, int pageSize, ProductCategory category);
    ProductCatalog getProductById(Long productId);
}
