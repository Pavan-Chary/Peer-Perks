package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.dto.transaction_dto.TransactionFilter;
import in.pavan.peer_perk_ledger.model.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionSpecification {

    public static Specification<Transaction> getByUserIdWithFilter(UUID userId, TransactionFilter filter){
        return (root, query, criteriaBuilder)->{

            List<Predicate> predicates = new ArrayList<>();

            Predicate isSender = criteriaBuilder.equal(root.get("senderId"), userId);
            Predicate isReceiver = criteriaBuilder.equal(root.get("receiverId"), userId);

            predicates.add(criteriaBuilder.or(isSender, isReceiver));

            if(filter.minPoints()!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("points"), filter.minPoints()));
            }

            if(filter.maxPoints()!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("points"), filter.maxPoints()));
            }

            if(filter.startDate()!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), filter.startDate()));
            }

            if(filter.endDate()!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timestamp"), filter.endDate()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
