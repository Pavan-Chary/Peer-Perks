package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.enums.ProductCategory;
import in.pavan.peer_perk_ledger.model.ProductCatalog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogSpecification {
    public static Specification<ProductCatalog> getAllProducts(ProductCategory category){
        return (root, query, criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();
            if(category!=null){
                predicates.add(criteriaBuilder.equal(root.get("category"),category));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
