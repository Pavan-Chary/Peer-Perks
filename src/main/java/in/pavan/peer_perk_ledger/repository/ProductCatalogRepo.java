package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.model.ProductCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductCatalogRepo extends JpaRepository<ProductCatalog, Long>, JpaSpecificationExecutor<ProductCatalog> {

}
