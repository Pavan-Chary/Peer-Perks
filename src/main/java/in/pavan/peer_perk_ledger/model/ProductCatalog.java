package in.pavan.peer_perk_ledger.model;

import in.pavan.peer_perk_ledger.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private Integer pointsRequired;

}
