package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.enums.ProductCategory;
import in.pavan.peer_perk_ledger.exception.ProductNotExistsEception;
import in.pavan.peer_perk_ledger.model.ProductCatalog;
import in.pavan.peer_perk_ledger.repository.ProductCatalogRepo;
import in.pavan.peer_perk_ledger.repository.ProductCatalogSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService{
    private final ProductCatalogRepo productCatalogRepo;
    @Override
    public Page<ProductCatalog> getAllProducts(int pageNum, int pageSize, ProductCategory category){

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("pointsRequired").ascending());
        Specification<ProductCatalog> specification = ProductCatalogSpecification.getAllProducts(category);

        return productCatalogRepo.findAll(specification, pageable);

    }

    @Override
    public ProductCatalog getProductById(Long productId) {
        ProductCatalog product = productCatalogRepo.findById(productId)
                .orElseThrow(()->new ProductNotExistsEception("No product exists with this Id "+productId));
        return product;
    }
}
